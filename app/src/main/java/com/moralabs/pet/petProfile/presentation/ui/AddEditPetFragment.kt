package com.moralabs.pet.petProfile.presentation.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentAddPetBinding
import com.moralabs.pet.petProfile.data.remote.dto.AttributeDto
import com.moralabs.pet.petProfile.data.remote.dto.PetAttributeDto
import com.moralabs.pet.petProfile.data.remote.dto.PetDto
import com.moralabs.pet.petProfile.presentation.adapter.PetAdapter
import com.moralabs.pet.petProfile.presentation.model.AttributeUiDto
import com.moralabs.pet.petProfile.presentation.model.AttributeUiType
import com.moralabs.pet.petProfile.presentation.viewmodel.AddEditPetViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class AddEditPetFragment : BaseFragment<FragmentAddPetBinding, List<AttributeDto>, AddEditPetViewModel>() {

    private val pet by lazy {
        activity?.intent?.getParcelableExtra<PetDto>(AddEditPetActivity.BUNDLE_PET)
    }

    private var isNewPhoto : Boolean = false

    override fun getLayoutId() = R.layout.fragment_add_pet
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<List<AttributeDto>> {
        val viewModel: AddEditPetViewModel by viewModels()
        return viewModel
    }

    private val petAdapter by lazy {
        PetAdapter(
            requireContext(),
            onPhotoClicked = {
                permissionResultLauncher.launch(permissions)
            },
            onChoiceChanged = { list ->
                viewModel.visibleData.postValue(viewModel.allData?.filter {
                    it.attributeDto.parentAttributeChoiceId == null || (list.filter { it.choiceId != null }
                        .map { it.choiceId }
                        .contains(it.attributeDto.parentAttributeChoiceId))
                }
                )
            }
        )
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.addPet))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = petAdapter

        viewModel.petAttributes()
    }

    override fun addListeners() {
        super.addListeners()

        binding.saveButton.setOnClickListener {
            if (petAdapter.currentList.any { it.attributeDto.isRequired && it.choice.isNullOrEmpty() }) {
                Toast.makeText(requireContext(), getString(R.string.add_pet_required), Toast.LENGTH_LONG).show()
            } else {
                pet?.let { petDto ->
                    viewModel.updatePet(
                        petDto,
                        if(isNewPhoto) petAdapter.currentList.firstOrNull { it.uiType == AttributeUiType.PHOTO }?.choice?.let { File(it) } else  null,                        petAdapter.currentList.firstOrNull { it.uiType == AttributeUiType.NAME }?.choice,
                        petAdapter.currentList.filter { it.uiType == AttributeUiType.ATTRIBUTE || it.uiType == AttributeUiType.ATTRIBUTE_LIST }
                            .map {
                                PetAttributeDto(
                                    attributeId = it.attributeDto.id,
                                    attributeChoiceId = it.choiceId,
                                    choice = it.choice,
                                )
                            },
                        onSuccess = {
                            Toast.makeText(requireContext(), getString(R.string.edit_pet), Toast.LENGTH_LONG).show()
                            activity?.setResult(Activity.RESULT_OK)
                            activity?.onBackPressed()
                        }
                    )
                } ?: run {
                    viewModel.savePet(
                        petAdapter.currentList.firstOrNull { it.uiType == AttributeUiType.PHOTO }?.choice?.let { File(it) },
                        petAdapter.currentList.firstOrNull { it.uiType == AttributeUiType.NAME }?.choice,
                        petAdapter.currentList.filter { it.uiType == AttributeUiType.ATTRIBUTE || it.uiType == AttributeUiType.ATTRIBUTE_LIST }
                            .map {
                                PetAttributeDto(
                                    attributeId = it.attributeDto.id,
                                    attributeChoiceId = it.choiceId,
                                    choice = it.choice,
                                )
                            },
                        onSuccess = {
                            Toast.makeText(requireContext(), getString(R.string.add_pet), Toast.LENGTH_LONG).show()
                            activity?.setResult(Activity.RESULT_OK)
                            activity?.onBackPressed()
                        }
                    )
                }
            }
        }
    }

    override fun addObservers() {
        super.addObservers()

        viewModel.visibleData.observe(viewLifecycleOwner) {
            petAdapter.submitList(it)
        }
    }

    override fun stateSuccess(data: List<AttributeDto>) {
        super.stateSuccess(data)

        val list = mutableListOf(
            AttributeUiDto(
                uiType = AttributeUiType.PHOTO,
                attributeDto = AttributeDto(
                    isRequired = true
                ),
                choice = pet?.media?.url
            ),
            AttributeUiDto(
                uiType = AttributeUiType.NAME,
                attributeDto = AttributeDto(
                    name = getString(R.string.name),
                    isRequired = true,
                ),
                choice = pet?.name
            )
        )

        data.forEach { attributeDto ->
            if (attributeDto.choices.isNullOrEmpty()) {
                list.add(
                    AttributeUiDto(
                        uiType = AttributeUiType.ATTRIBUTE,
                        attributeDto = attributeDto,
                        choiceId = pet?.petAttributes?.firstOrNull { it.attributeId == attributeDto.id }?.attributeChoiceId,
                        choice = pet?.petAttributes?.firstOrNull { it.attributeId == attributeDto.id }?.choice
                    )
                )
            } else {
                list.add(
                    AttributeUiDto(
                        uiType = AttributeUiType.ATTRIBUTE_LIST,
                        attributeDto = attributeDto,
                        choiceId = pet?.petAttributes?.firstOrNull { it.attributeId == attributeDto.id }?.attributeChoiceId,
                        choice = pet?.petAttributes?.firstOrNull { it.attributeId == attributeDto.id }?.choice
                    )
                )
            }
        }

        viewModel.allData = list
        viewModel.visibleData.postValue(viewModel.allData?.filter {
            it.attributeDto.parentAttributeChoiceId == null || (list.filter { it.choiceId != null }
                .map { it.choiceId }
                .contains(it.attributeDto.parentAttributeChoiceId))
        })
    }

    // PHOTO AREA

    private var currentPhotoFile: File? = null

    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

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
                    petAdapter.currentList.getOrNull(0)?.choice = file.path
                    petAdapter.notifyDataSetChanged()
                }
                currentPhotoFile = null
            }
        }

    private val galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                getRealPathFromURI(requireContext(), it)?.let { filePath ->
                    petAdapter.currentList.getOrNull(0)?.choice = filePath
                    petAdapter.notifyDataSetChanged()
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
        isNewPhoto = true
        return FileProvider.getUriForFile(
            requireContext(),
            context?.packageName + ".fileprovider",
            file
        )
    }
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

fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

fun isGooglePhotosUri(uri: Uri): Boolean {
    return "com.google.android.apps.photos.content" == uri.authority
}