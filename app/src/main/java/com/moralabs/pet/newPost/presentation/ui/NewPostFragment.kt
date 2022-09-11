package com.moralabs.pet.newPost.presentation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.moralabs.pet.R
import com.moralabs.pet.BR
import com.moralabs.pet.core.presentation.BaseViewModel
import com.moralabs.pet.core.presentation.ViewState
import com.moralabs.pet.core.presentation.adapter.BaseListAdapter
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.ui.BaseFragment
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
        }, isSameDto = { oldItem, newItem ->
            true
        })
    }

    override fun addListeners() {
        super.addListeners()

        viewModel.petValue()

        binding.toolbar.publishText.setOnClickListener {
            val pet = petCardAdapter.currentList?.filter { it.selected }?.firstOrNull()
            viewModel.createPost(
                NewPostDto(
                    text = binding.explanationText.text.toString(),
                    type = postType,
                    petId = pet?.id
                )
            )
            startActivity(Intent(context, MainPageActivity::class.java))
        }

        binding.toolbar.imgClose.setOnClickListener {
            startActivity(Intent(context, MainPageActivity::class.java))
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

            binding.postImage.isVisible = it.size > 0

        }

        lifecycleScope.launch {
            viewModel.getUser.collect {
                when(it) {
                    is ViewState.Success -> {
                        binding.userPhoto.loadImage(it.data.media?.url)
                        binding.userName.text = it.data.userName.toString()
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.petCardList.adapter = petCardAdapter
        viewModel.userInfo()

        if (postType == TabTextType.POST_TYPE.type) {
            binding.petChooseLinear.visibility = View.GONE
        }
        if (postType == TabTextType.QAN_TYPE.type) {
            binding.petChooseLinear.visibility = View.GONE
        }
        if (postType == TabTextType.FIND_PARTNER_TYPE.type) {
            binding.keyboardConstraint.visibility = View.GONE
            binding.petChooseLinear.visibility = View.VISIBLE
        }
        if (postType == TabTextType.ADOPTION_TYPE.type) {
            binding.keyboardConstraint.visibility = View.GONE
            binding.petChooseLinear.visibility = View.VISIBLE
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
            permissions.entries.forEach {
                if (it.value.not()) {
                    return@forEach
                }

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
                getImagePath(it)?.let { filePath ->
                    (viewModel as? NewPostViewModel)?.addFile(File(filePath))
                }
            }
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

    private fun getImagePath(uri: Uri): String? {
        var imagePath: String? = null

        //DocumentsContract defines the contract between a documents provider and the platform.
        if (DocumentsContract.isDocumentUri(context, uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri.authority) {
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePathFromSelection(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    selsetion
                )
            } else if ("com.android.providers.downloads.documents" == uri.authority) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse(
                        "content://downloads/public_downloads"
                    ), java.lang.Long.valueOf(docId)
                )
                imagePath = getImagePathFromSelection(contentUri, null)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            imagePath = getImagePathFromSelection(uri, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            imagePath = uri.path
        }

        return imagePath
    }

    @SuppressLint("Range")
    private fun getImagePathFromSelection(uri: Uri, selection: String?): String? {
        var path: String? = null
        val cursor = currentActivity?.contentResolver?.query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }

    override fun onFileDelete(file: File) {
        (viewModel as? NewPostViewModel)?.deleteFile(file)
    }
}

interface NewPostImageListener {
    fun onFileDelete(file: File)
}