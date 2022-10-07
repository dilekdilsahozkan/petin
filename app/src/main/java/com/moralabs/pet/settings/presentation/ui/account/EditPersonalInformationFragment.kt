package com.moralabs.pet.settings.presentation.ui.account

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.KeyEvent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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

    var phoneList = mutableListOf<Any>("(", "_", "_", "_", ")", " ", "_", "_", "_", " ", "_", "_", " ", "_", "_")
    var defaultList = mutableListOf<Any>("(", "_", "_", "_", ")", " ", "_", "_", "_", " ", "_", "_", " ", "_", "_")
    var indices = mutableListOf<Int>()
    var index = 0
    var oldIndex = -1



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
        data.phoneNumber?.let {
            binding.phoneNumberEdit.setText(it)
        }
        binding.userImage.loadImageWithPlaceholder(data.media?.url)
    }

    override fun addListeners() {
        super.addListeners()
        binding.editProfile.setOnClickListener {
            if(newProfilePictureUrl.isNullOrEmpty()) {
                viewModel.editUser(
                    binding.fullNameEdit.text.toString(),
                    binding.phoneNumberEdit.text.toString(),
                    null
                )
            }
            else {
                viewModel.editUser(
                    binding.fullNameEdit.text.toString(),
                    binding.phoneNumberEdit.text.toString(),
                    File(newProfilePictureUrl)
                )
            }

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
                if (index == -1){
                    var span = SpannableString(phoneList.joinToString(""))
                    span.setSpan(
                        ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.mainColor)), 0,
                        0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    binding.phoneNumberEdit.setText(span)
                    binding.phoneNumberEdit.setSelection(indices[0])
                    return@OnKeyListener true
                }else{
                    phoneList[indices[index]] = defaultList[indices[index]]
                    index--
                    var span = SpannableString(phoneList.joinToString(""))
                    if(index>-1){
                        span.setSpan(
                            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.mainColor)), 0,
                            indices[index] + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        binding.phoneNumberEdit.setText(span)
                        binding.phoneNumberEdit.setSelection(indices[index]+1)
                    }else{
                        span.setSpan(
                            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.mainColor)), 0,
                            0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        binding.phoneNumberEdit.setText(span)
                        binding.phoneNumberEdit.setSelection(indices[0])
                    }

                    oldIndex = index
                }

                return@OnKeyListener true
            }
            else if (keyCode in 7..16) {
                if (index == indices.size - 1) return@OnKeyListener true
                index++
                phoneList[indices[index]] = keyCode - 7
                var span = SpannableString(phoneList.joinToString(""))
                span.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.mainColor)), 0,
                    indices[index] + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.phoneNumberEdit.setText(span)

                if(index+1<indices.size)
                    binding.phoneNumberEdit.setSelection(indices[index+1])
                else
                    binding.phoneNumberEdit.setSelection(indices[index]+1)

                return@OnKeyListener true
            }
            false
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