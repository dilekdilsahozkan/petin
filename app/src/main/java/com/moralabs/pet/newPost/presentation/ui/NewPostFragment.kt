package com.moralabs.pet.newPost.presentation.ui

import android.Manifest
import android.app.AlertDialog
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.moralabs.pet.BR
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.adapter.PetListAdapter
import com.moralabs.pet.core.presentation.adapter.loadImage
import com.moralabs.pet.core.presentation.adapter.loadImageWithPlaceholder
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.ui.PetWarningDialog
import com.moralabs.pet.core.presentation.ui.PetWarningDialogResult
import com.moralabs.pet.core.presentation.ui.PetWarningDialogType
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.databinding.FragmentNewPostBinding
import com.moralabs.pet.databinding.ItemPetCardBinding
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
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        if (postType == TabTextType.POST_TYPE.type) {
            binding.keyboardToolbar.visibility = View.VISIBLE
            binding.line.visibility = View.VISIBLE
            binding.petCardList.visibility = View.GONE
            binding.selectPetText.visibility = View.GONE
            binding.cardPostImage.visibility = View.VISIBLE
        }
        if (postType == TabTextType.QAN_TYPE.type) {
            binding.keyboardToolbar.visibility = View.VISIBLE
            binding.line.visibility = View.VISIBLE
            binding.petCardList.visibility = View.GONE
            binding.selectPetText.visibility = View.GONE
            binding.cardPostImage.visibility = View.VISIBLE
        }
        if (postType == TabTextType.FIND_PARTNER_TYPE.type) {
            binding.keyboardToolbar.visibility = View.GONE
            binding.line.visibility = View.GONE
            binding.petCardList.visibility = View.VISIBLE
            binding.selectPetText.visibility = View.VISIBLE
            binding.cardPostImage.visibility = View.GONE
        }
        if (postType == TabTextType.ADOPTION_TYPE.type) {
            binding.keyboardToolbar.visibility = View.GONE
            binding.line.visibility = View.GONE
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
                discard = getString(R.string.no),
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
                binding.inputViewCounter.setTextColor(ContextCompat.getColor(requireContext(), R.color.darkGray))
            }
        }
        binding.explanationText.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text: String = binding.explanationText.text.toString()
                if (text.startsWith(" ")) {
                    binding.explanationText.setText(text.trim { it <= ' ' })
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
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
                Glide.with(requireContext()).load(it[0]).into(binding.postImage)
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

fun getRealPathFromURI(context: Context, uri: Uri): String? {
    when {
        // DocumentProvider
        DocumentsContract.isDocumentUri(context, uri) -> {
            when {
                // ExternalStorageProvider
                isExternalStorageDocument(uri) -> {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":").toTypedArray()
                    val type = split[0]
                    // This is for checking Main Memory
                    return if ("primary".equals(type, ignoreCase = true)) {
                        if (split.size > 1) {
                            Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                        } else {
                            Environment.getExternalStorageDirectory().toString() + "/"
                        }
                        // This is for checking SD Card
                    } else {
                        "storage" + "/" + docId.replace(":", "/")
                    }
                }
                isDownloadsDocument(uri) -> {
                    val fileName = getFilePath(context, uri)
                    if (fileName != null) {
                        return Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName
                    }
                    var id = DocumentsContract.getDocumentId(uri)
                    if (id.startsWith("raw:")) {
                        id = id.replaceFirst("raw:".toRegex(), "")
                        val file = File(id)
                        if (file.exists()) return id
                    }
                    val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
                    return getDataColumn(context, contentUri, null, null)
                }
                isMediaDocument(uri) -> {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":").toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    when (type) {
                        "image" -> {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        }
                        "video" -> {
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        }
                        "audio" -> {
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        }
                    }
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])
                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }
            }
        }
        "content".equals(uri.scheme, ignoreCase = true) -> {
            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)
        }
        "file".equals(uri.scheme, ignoreCase = true) -> {
            return uri.path
        }
    }
    return null
}

fun getDataColumn(context: Context, uri: Uri?, selection: String?,
                  selectionArgs: Array<String>?): String? {
    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(
        column
    )
    try {
        if (uri == null) return null
        cursor = context.contentResolver.query(uri, projection, selection, selectionArgs,
            null)
        if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(index)
        }
    } finally {
        cursor?.close()
    }
    return null
}


fun getFilePath(context: Context, uri: Uri?): String? {
    var cursor: Cursor? = null
    val projection = arrayOf(
        MediaStore.MediaColumns.DISPLAY_NAME
    )
    try {
        if (uri == null) return null
        cursor = context.contentResolver.query(uri, projection, null, null,
            null)
        if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
            return cursor.getString(index)
        }
    } finally {
        cursor?.close()
    }
    return null
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is ExternalStorageProvider.
 */
fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is DownloadsProvider.
 */
fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is MediaProvider.
 */
fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is Google Photos.
 */
fun isGooglePhotosUri(uri: Uri): Boolean {
    return "com.google.android.apps.photos.content" == uri.authority
}