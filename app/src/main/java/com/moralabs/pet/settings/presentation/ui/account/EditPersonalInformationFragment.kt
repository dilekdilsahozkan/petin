package com.moralabs.pet.settings.presentation.ui.account

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.moralabs.pet.R
import com.moralabs.pet.core.presentation.viewmodel.BaseViewModel
import com.moralabs.pet.core.presentation.adapter.loadImageWithPlaceholder
import com.moralabs.pet.core.presentation.ui.BaseFragment
import com.moralabs.pet.databinding.FragmentEditPersonalInformationBinding
import com.moralabs.pet.profile.data.remote.dto.UserDto
import com.moralabs.pet.settings.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class EditPersonalInformationFragment : BaseFragment<FragmentEditPersonalInformationBinding, UserDto, SettingsViewModel>() {

    override fun getLayoutId() = R.layout.fragment_edit_personal_information
    override fun fetchStrategy() = UseCaseFetchStrategy.NO_FETCH

    private var newProfilePictureUrl: String? = null


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

    override fun stateSuccess(data: UserDto) {
        super.stateSuccess(data)
        binding.fullNameEdit.setText(data.fullName.toString())
        binding.phoneNumberEdit.setText(data.phoneNumber.toString())
        binding.userImage.loadImageWithPlaceholder(data.media?.url)
    }

    override fun addListeners() {
        super.addListeners()
        binding.editProfile.setOnClickListener {
            viewModel.editUser(
                binding.fullNameEdit.text.toString(),
                binding.phoneNumberEdit.text.toString(),
                File(newProfilePictureUrl)
            )
        }
        binding.editImage.setOnClickListener {
            permissionResultLauncher.launch(permissions)
        }
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
}