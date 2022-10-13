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
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.R
import com.moralabs.pet.BR
import com.moralabs.pet.core.presentation.adapter.PetListAdapter
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.adapter.loadImageWithPlaceholder
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

    val MAX_CHAR_NUMBER = 255

    private var currentPhotoFile: File? = null

    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    var list = mutableListOf<PetDto>()

    override fun getLayoutId() = R.layout.fragment_new_post
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<CreatePostDto> {
        val viewModel: NewPostViewModel by viewModels()
        return viewModel
    }

    private val petCardAdapter: PetListAdapter<PetDto, ItemPetCardBinding> by lazy {
        PetListAdapter(R.layout.item_pet_card, BR.item, onRowClick = { selected ->

            var a = mutableListOf<PetDto>()
            viewModel.petList.forEach { pet ->
                a.add(pet.copy())
            }
            a.forEach {
                it.selected = it.id == selected.id
            }
            petCardAdapter.submitList(a)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.petCardList.adapter = petCardAdapter
        viewModel.userInfo()

        if (postType == TabTextType.POST_TYPE.type) {
            binding.keyboardConstraint.visibility = View.VISIBLE
            binding.petCardList.visibility = View.GONE
            binding.selectPetText.visibility = View.GONE
            binding.cardPostImage.visibility = View.VISIBLE
        }
        if (postType == TabTextType.QAN_TYPE.type) {
            binding.keyboardConstraint.visibility = View.VISIBLE
            binding.petCardList.visibility = View.GONE
            binding.selectPetText.visibility = View.GONE
            binding.cardPostImage.visibility = View.VISIBLE
        }
        if (postType == TabTextType.FIND_PARTNER_TYPE.type) {
            binding.keyboardConstraint.visibility = View.GONE
            binding.petCardList.visibility = View.VISIBLE
            binding.selectPetText.visibility = View.VISIBLE
            binding.cardPostImage.visibility = View.GONE
        }
        if (postType == TabTextType.ADOPTION_TYPE.type) {
            binding.keyboardConstraint.visibility = View.GONE
            binding.petCardList.visibility = View.VISIBLE
            binding.selectPetText.visibility = View.VISIBLE
            binding.cardPostImage.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(
            "fragment_location"
        ) { _, result ->
            binding.location.text = result.getString("location")
            binding.location.visibility = View.VISIBLE

            viewModel.locationId = result.getString("locationId")
        }
        viewModel.petValue()
    }

    override fun addListeners() {
        super.addListeners()

        binding.toolbar.publishText.setOnClickListener {
            if (binding.explanationText.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.add_text), Toast.LENGTH_LONG)
                    .show()
            } else {
                val pet = petCardAdapter.currentList.filter { it.selected }.firstOrNull()
                if ((postType == TabTextType.FIND_PARTNER_TYPE.type || postType == TabTextType.ADOPTION_TYPE.type) && pet?.selected == true) {
                    viewModel.createPost(
                        NewPostDto(
                            text = binding.explanationText.text.toString(),
                            type = postType,
                            petId = pet.id,
                        )
                    )
                } else if (postType == TabTextType.POST_TYPE.type || postType == TabTextType.QAN_TYPE.type) {
                    viewModel.createPost(
                        NewPostDto(
                            text = binding.explanationText.text.toString(),
                            type = postType,
                            locationId = viewModel.locationId,
                            files = viewModel.files.value
                        )
                    )
                } else {
                    Toast.makeText(requireContext(), R.string.have_to_add_pet, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        binding.toolbar.imgClose.setOnClickListener {
            PetWarningDialog(
                requireContext(),
                PetWarningDialogType.CONFIRMATION,
                resources.getString(R.string.ask_sure),
                okay = getString(R.string.yes),
                description = resources.getString(R.string.postSure),
                negativeButton = resources.getString(R.string.no),
                onResult = {
                    if (PetWarningDialogResult.OK == it) {
                        activity?.finish()
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

        binding.explanationText.doAfterTextChanged {
            binding.inputViewCounter.text = "${it?.length}/${MAX_CHAR_NUMBER}"

            if(it?.length == MAX_CHAR_NUMBER){
                binding.inputViewCounter.setTextColor(ContextCompat.getColor(requireContext(), R.color.mainColor))
            }else{
                binding.inputViewCounter.setTextColor(R.color.darkGray)
            }
        }
    }

    override fun addObservers() {
        super.addObservers()

        viewModel.selectedPet.observe(viewLifecycleOwner) {
            it?.let { list ->
                (viewModel as? NewPostViewModel)?.petList = list.toMutableList()
            }
            petCardAdapter.submitList(viewModel.petList)
        }

        (viewModel as? NewPostViewModel)?.files?.observe(viewLifecycleOwner) {

            if (it.size > 0) {
                Glide.with(requireContext()).load(it.get(0)).into(binding.postImage)
            }

            lifecycleScope.launch {
                viewModel.getUser.collect {
                    when (it) {
                        is ViewState.Success -> {
                            binding.userPhoto.loadImage(it.data.media?.url)
                            binding.userName.text = it.data.userName.toString()
                            binding.userPhoto.loadImageWithPlaceholder(it.data.media?.url)
                        }
                        else -> {}
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.createPost.collect {
                when (it) {
                    is ViewState.Success -> {
                        activity?.finish()
                    }
                    else -> {}
                }
            }
        }
    }

    override fun stateSuccess(data: CreatePostDto) {
        super.stateSuccess(data)

        if ((postType == TabTextType.FIND_PARTNER_TYPE.type || postType == TabTextType.ADOPTION_TYPE.type) && data.getValue?.size == 0) {
            binding.addPetText.visibility = View.VISIBLE
        } else {
            binding.addPetText.visibility = View.GONE
        }

        data.getValue?.let { list ->
            (viewModel as? NewPostViewModel)?.petList = list.toMutableList()
        }

        petCardAdapter.submitList(viewModel.petList)
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
