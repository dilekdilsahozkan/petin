package com.moralabs.pet.newPost.presentation.ui

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.R
import com.moralabs.pet.BR
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.ui.PetWarningDialog
import com.moralabs.pet.core.presentation.ui.PetWarningDialogResult
import com.moralabs.pet.core.presentation.ui.PetWarningDialogType
import com.moralabs.pet.databinding.FragmentNewPostBinding
import com.moralabs.pet.databinding.ItemPetCardBinding
import com.moralabs.pet.mainPage.presentation.ui.MainPageActivity
import com.moralabs.pet.newPost.data.remote.dto.NewPostDto
import com.moralabs.pet.newPost.presentation.viewmodel.NewPostViewModel
import com.moralabs.pet.petProfile.data.remote.dto.CreatePostDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class NewPostFragment : BaseFragment<FragmentNewPostBinding, CreatePostDto, NewPostViewModel>(),
    NewPostImageListener {

    private val postType: Int? by lazy {
        activity?.intent?.getIntExtra(NewPostActivity.BUNDLE_CHOOSE_TYPE, 0)
    }

    private val location: String? by lazy {
        activity?.intent?.getStringExtra(NewPostActivity.LOCATION)
    }

    private val locationId: String? by lazy {
        activity?.intent?.getStringExtra(NewPostActivity.LOCATION_ID)
    }

    private var currentPhotoFile: File? = null

    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun getLayoutId() = R.layout.fragment_new_post
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<CreatePostDto> {
        val viewModel: NewPostViewModel by viewModels()
        return viewModel
    }

    private val petCardAdapter: BaseListAdapter<PetDto, ItemPetCardBinding> by lazy {
        BaseListAdapter(R.layout.item_pet_card, BR.item, onRowClick = { selected ->
            petCardAdapter.currentList.forEach { pet ->
                pet.selected = pet == selected
            }
            petCardAdapter.notifyDataSetChanged()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(location.isNullOrEmpty().not()){
            binding.location.visibility = View.VISIBLE
            binding.location.text = location.toString()
        } else {
            binding.location.visibility = View.GONE
        }

        binding.petCardList.adapter = petCardAdapter
        viewModel.userInfo()

        if (postType == TabTextType.POST_TYPE.type) {
            binding.petChooseLinear.visibility = View.GONE
            binding.cardPostImage.visibility = View.VISIBLE
            binding.deleteImage.visibility = View.VISIBLE
        }
        if (postType == TabTextType.QAN_TYPE.type) {
            binding.petChooseLinear.visibility = View.GONE
            binding.cardPostImage.visibility = View.VISIBLE
            binding.deleteImage.visibility = View.VISIBLE
        }
        if (postType == TabTextType.FIND_PARTNER_TYPE.type) {
            binding.keyboardConstraint.visibility = View.GONE
            binding.petChooseLinear.visibility = View.VISIBLE
            binding.cardPostImage.visibility = View.GONE
            binding.deleteImage.visibility = View.GONE
        }
        if (postType == TabTextType.ADOPTION_TYPE.type) {
            binding.keyboardConstraint.visibility = View.GONE
            binding.petChooseLinear.visibility = View.VISIBLE
            binding.cardPostImage.visibility = View.GONE
            binding.deleteImage.visibility = View.GONE
        }
    }

    override fun addListeners() {
        super.addListeners()

        viewModel.petValue()

        binding.toolbar.publishText.setOnClickListener {
            if(binding.explanationText.text.isNullOrEmpty()){
                Toast.makeText(requireContext(), getString(R.string.add_text), Toast.LENGTH_LONG).show()
            }else{
                val pet = petCardAdapter.currentList.filter { it.selected }.firstOrNull()
                viewModel.createPost(
                    NewPostDto(
                        text = binding.explanationText.text.toString(),
                        type = postType,
                        locationId = locationId,
                        petId = pet?.id,
                        files = viewModel.files.value
                    )
                )
                startActivity(Intent(context, MainPageActivity::class.java))
            }
        }

        binding.toolbar.imgClose.setOnClickListener {
            PetWarningDialog(
                requireContext(),
                PetWarningDialogType.CONFIRMATION,
                resources.getString(R.string.ask_sure),
                okey = getString(R.string.yes),
                description = resources.getString(R.string.postSure),
                negativeButton = resources.getString(R.string.no),
                onResult = {
                    if (PetWarningDialogResult.OK == it) {
                        startActivity(Intent(context, MainPageActivity::class.java))
                    }
                }
            ).show()
        }

        binding.locationIcon.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_post_to_addLocationFragment)
        }

        binding.galleryIcon.setOnClickListener {
            permissionResultLauncher.launch(permissions)
        }
    }

    override fun addObservers() {
        super.addObservers()

        (viewModel as? NewPostViewModel)?.files?.observe(viewLifecycleOwner) {

//            binding.postImage.isVisible = it.size > 0
            if (it.size > 0) {
                Glide.with(requireContext()).load(it.get(0)).into(binding.postImage)
            }

            lifecycleScope.launch {
                viewModel.getUser.collect {
                    when (it) {
                        is ViewState.Success -> {
                            binding.userPhoto.loadImage(it.data.media?.url)
                            binding.userName.text = it.data.userName.toString()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    override fun stateSuccess(data: CreatePostDto) {
        super.stateSuccess(data)

        petCardAdapter.submitList(data.getValue)
    }

    private val permissionResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            // Handle Permission granted/rejected
            var canDialogBeOpened = true
            permissions.entries.forEach {
                if (it.value.not()) {
                    // TODO : Error
                    canDialogBeOpened = false
                    return@forEach
                }
            }
            if (canDialogBeOpened) {
                openDialog()
            }
        }

    private val cameraResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) {
            if (it) {
                currentPhotoFile?.let { file ->
                    (viewModel as? NewPostViewModel)?.addFile(file)

                }
                currentPhotoFile = null
            }
        }

    private val galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                getRealPathFromURI(requireContext(), it)?.let { filePath ->
                    (viewModel as? NewPostViewModel)?.addFile(File(filePath))
                }
            }
        }

    private fun getRealPathFromURI(context: Context, uri: Uri?): String? {
        var filePath = ""
        val wholeID = DocumentsContract.getDocumentId(uri)

        // Split at colon, use second item in the array
        val id = wholeID.split(":").toTypedArray()[1]
        val column = arrayOf(MediaStore.Images.Media.DATA)

        // where id is equal to
        val sel = MediaStore.Images.Media._ID + "=?"
        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            column, sel, arrayOf(id), null
        )
        val columnIndex = cursor?.getColumnIndex(column[0])
        if (cursor?.moveToFirst() == true) {
            filePath = columnIndex?.let { cursor?.getString(it) }.toString()
        }
        cursor?.close()
        return filePath
    }

    private fun openDialog() {
        val builder = AlertDialog.Builder(context)

        builder.setItems(
            arrayOf(
                getString(R.string.takePhoto),
                getString(R.string.chooseGallery),
                getString(R.string.cancel)
            )
        ) { _, index ->
            when (index) {
                0 -> {
                    cameraResultLauncher.launch(createPhotoUri())
                }
                1 -> {
                    galleryResultLauncher.launch("image/*")
                }
            }
        }
        builder.show()
    }

    private fun createPhotoUri(): Uri {
        val file = File(context?.cacheDir, "${System.currentTimeMillis()}.jpg")

        if (file.parentFile.exists().not())
            file.parentFile.mkdirs()

        currentPhotoFile = file

        return FileProvider.getUriForFile(
            requireContext(),
            context?.packageName + ".fileprovider",
            file
        )
    }

    override fun onFileDelete(file: File) {
        (viewModel as? NewPostViewModel)?.deleteFile(file)
    }
}

interface NewPostImageListener {
    fun onFileDelete(file: File)
}
