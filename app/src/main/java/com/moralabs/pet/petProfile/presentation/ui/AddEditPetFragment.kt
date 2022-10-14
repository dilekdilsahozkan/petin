package com.moralabs.pet.petProfile.presentation.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
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
                        if(isNewPhoto) petAdapter.currentList.firstOrNull { it.uiType == AttributeUiType.PHOTO }?.choice?.let { File(it) } else  null,
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
        isNewPhoto = true
        return FileProvider.getUriForFile(
            requireContext(),
            context?.packageName + ".fileprovider",
            file
        )
    }
}