package com.moralabs.pet.settings.presentation.ui.account

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
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.loadImageWithPlaceholder
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.core.presentation.viewmodel.ViewState
import com.moralabs.pet.databinding.FragmentEditPersonalInformationBinding
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class EditPersonalInformationFragment :
    BaseFragment<FragmentEditPersonalInformationBinding, UserDto, SettingsViewModel>() {

    private var newProfilePictureUrl: String? = null
    private val digitType = "^\\(\\d{3}\\)\\s\\d{3}\\s\\d{2}\\s\\d{2}".toRegex()

    private var phoneList = mutableListOf<Any>("(", "_", "_", "_", ")", " ", "_", "_", "_", " ", "_", "_", " ", "_", "_")
    var defaultList = mutableListOf<Any>("(", "_", "_", "_", ")", " ", "_", "_", "_", " ", "_", "_", " ", "_", "_")
    var indices = mutableListOf<Int>()
    var index = 0
    var oldIndex = -1

    override fun getLayoutId() = R.layout.fragment_edit_personal_information
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    override fun fragmentViewModel(): BaseViewModel<UserDto> {
        val viewModel: SettingsViewModel by viewModels()
        return viewModel
    }

    override fun setToolbar() {
        super.setToolbar()
        toolbarListener?.showTitleText(getString(R.string.settings_personal_information))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userInfo()
    }

    override fun addListeners() {
        super.addListeners()

        binding.editProfile.setOnClickListener {
            viewModel.editUser(
                binding.fullNameEdit.text.toString(),
                if (digitType.matches(binding.phoneNumberEdit.text.toString()))
                    binding.phoneNumberEdit.text.toString()
                else null,
                newProfilePictureUrl?.let {
                    File(it)
                } ?: null
            )
        }

        binding.editImage.setOnClickListener {
            permissionResultLauncher.launch(permissions)
        }

        index = 0
        indices.clear()
        defaultList = mutableListOf<Any>("(", "_", "_", "_", ")", " ", "_", "_", "_", " ", "_", "_", " ", "_", "_")
        phoneList = mutableListOf<Any>("(", "_", "_", "_", ")", " ", "_", "_", "_", " ", "_", "_", " ", "_", "_")

        if (index == 0)
            phoneList.forEachIndexed { i, e -> if (e is Int || e == "_") indices.add(i) }

        var span = SpannableString(phoneList.joinToString(""))
        span.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.mainColor)), 0,
            indices[index] + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.phoneNumberEdit.setText(span)
        index = -1
        oldIndex = index

        binding.phoneNumberEdit.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action != KeyEvent.ACTION_DOWN)
                return@OnKeyListener true
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (index == -1) {
                    var span = SpannableString(phoneList.joinToString(""))
                    span.setSpan(
                        ForegroundColorSpan(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.mainColor
                            )
                        ), 0,
                        0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    binding.phoneNumberEdit.setText(span)
                    binding.phoneNumberEdit.setSelection(indices[0])
                    return@OnKeyListener true
                } else {
                    phoneList[indices[index]] = defaultList[indices[index]]
                    index--
                    var span = SpannableString(phoneList.joinToString(""))
                    if (index > -1) {
                        span.setSpan(
                            ForegroundColorSpan(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.mainColor
                                )
                            ), 0,
                            indices[index] + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        binding.phoneNumberEdit.setText(span)
                        binding.phoneNumberEdit.setSelection(indices[index] + 1)
                    } else {
                        span.setSpan(
                            ForegroundColorSpan(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.mainColor
                                )
                            ), 0,
                            0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        binding.phoneNumberEdit.setText(span)
                        binding.phoneNumberEdit.setSelection(indices[0])
                    }
                    oldIndex = index
                }

                return@OnKeyListener true
            } else if (keyCode in 7..16) {
                if (index == indices.size - 1) return@OnKeyListener true
                index++
                phoneList[indices[index]] = keyCode - 7
                var span = SpannableString(phoneList.joinToString(""))
                span.setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.mainColor
                        )
                    ), 0,
                    indices[index] + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.phoneNumberEdit.setText(span)

                if (index + 1 < indices.size)
                    binding.phoneNumberEdit.setSelection(indices[index + 1])
                else
                    binding.phoneNumberEdit.setSelection(indices[index] + 1)

                return@OnKeyListener true
            }
            false
        })
    }

    override fun addObservers() {
        super.addObservers()

        lifecycleScope.launch {
            viewModel.editProfile.collect {
                when (it) {
                    is ViewState.Loading -> {
                        startLoading()
                    }
                    is ViewState.Success<UserDto> -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.success_profile_change),
                            Toast.LENGTH_LONG
                        ).show()
                        activity?.onBackPressed()
                    }
                    is ViewState.Error<*> -> {
                        stateError(it.message)
                        stopLoading()
                    }
                }
            }
        }
    }

    override fun stateSuccess(data: UserDto) {
        super.stateSuccess(data)
        binding.fullNameEdit.setText(data.fullName.toString())
        data.phoneNumber?.let {
            binding.phoneNumberEdit.setText(it)
        }
        binding.userImage.loadImageWithPlaceholder(data.media?.url)
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
                    newProfilePictureUrl = file.path
                    binding.userImage.loadImageWithPlaceholder(newProfilePictureUrl)
                }
                currentPhotoFile = null
            }
        }

    private val galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                getRealPathFromURI(requireContext(), it)?.let { filePath ->
                    newProfilePictureUrl = filePath
                    binding.userImage.loadImageWithPlaceholder(newProfilePictureUrl)
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
                        return Environment.getExternalStorageDirectory()
                            .toString() + "/Download/" + fileName
                    }
                    var id = DocumentsContract.getDocumentId(uri)
                    if (id.startsWith("raw:")) {
                        id = id.replaceFirst("raw:".toRegex(), "")
                        val file = File(id)
                        if (file.exists()) return id
                    }
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(id)
                    )
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
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                context,
                uri,
                null,
                null
            )
        }
        "file".equals(uri.scheme, ignoreCase = true) -> {
            return uri.path
        }
    }
    return null
}

fun getDataColumn(
    context: Context, uri: Uri?, selection: String?,
    selectionArgs: Array<String>?
): String? {
    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(
        column
    )
    try {
        if (uri == null) return null
        cursor = context.contentResolver.query(
            uri, projection, selection, selectionArgs,
            null
        )
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
        cursor = context.contentResolver.query(
            uri, projection, null, null,
            null
        )
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