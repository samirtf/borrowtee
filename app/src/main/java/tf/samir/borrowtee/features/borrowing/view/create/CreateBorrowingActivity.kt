package tf.samir.borrowtee.features.borrowing.view.create

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import tf.samir.borrowtee.R
import tf.samir.borrowtee.databinding.ActivityCreateBorrowingBinding
import tf.samir.borrowtee.features.borrowing.presentation.presenter.create.*
import tf.samir.borrowtee.features.borrowing.utils.createBitmap
import tf.samir.borrowtee.features.borrowing.utils.createImageFile
import tf.samir.borrowtee.viewbase.alert
import tf.samir.core.base.HyperActivity
import tf.samir.infrastructure.datasource.failures.UniqueConstraintException
import timber.log.Timber
import java.io.File
import java.io.IOException


@AndroidEntryPoint
class CreateBorrowingActivity :
    HyperActivity<CreateBorrowingViewState, CreateBorrowingViewEffect, CreateBorrowingViewEvent, CreateBorrowingViewModel>() {

    companion object {
        const val TAG = "CreateBorrowingA"
    }

    override val viewModel by viewModels<CreateBorrowingViewModel>()
    private var dialog: AlertDialog? = null
    private var binding: ActivityCreateBorrowingBinding? = null

    private var resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            // There are no request codes
            Timber.tag(TAG).i("registerForActivityResult:[${pictureFile?.absolutePath}]")
            updatePictureView(pictureFile.createBitmap())
        } else {
            Timber.tag(TAG).i("registerForActivityResult:fail")
        }
    }

    private var pictureFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // The layout for this activity is a Data Binding layout so it needs to be inflated using
        // DataBindingUtil.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_borrowing)
        binding?.let { binding ->
            binding.viewModel = viewModel
            binding.thing = ThingData("", "")
            binding.imageView.setOnClickListener {
                viewModel.handle(CreateBorrowingViewEvent.TakePicture)
            }
            binding.button.setOnClickListener {
                binding.thing.let { viewModel.handle(CreateBorrowingViewEvent.CreateClicked(it)) }
            }
            // Specify the current activity as the lifecycle owner.
            binding.lifecycleOwner = this
        }

    }

    override fun renderViewState(viewState: CreateBorrowingViewState) {
        when (viewState.createStatus) {
            CreateStatus.Creating -> Timber.tag(TAG).d("CreateStatus.Creating")
            CreateStatus.Created -> Timber.tag(TAG).d("CreateStatus.Created")
            CreateStatus.NotCreated -> Timber.tag(TAG).d("CreateStatus.NotCreated")
        }
        when (viewState.dialogState) {
            DialogState.NotShowing -> Timber.tag(TAG).d("Not showing dialog")
            DialogState.ShowingSuccess -> showSuccessDialog()
            is DialogState.ShowingFailure -> showFailureDialog(viewState.dialogState.exception)
        }
    }

    private fun showSuccessDialog() {
        dialog = null
        dialog = alert("Borrowing created", "The borrowing was successfully created!") {
            positiveButton("Ok") { navigateBack() }
            cancelable = false
        }
        dialog?.show()
    }

    private fun showFailureDialog(ex: Throwable) {
        dialog = null
        dialog = alert("Failed to create borrowing", handleThrowableMessage(ex)) {
            positiveButton("Retry") { retryToCreateBorrowing() }
            negativeButton("Cancel") { navigateBack() }
            cancelable = false
        }
        dialog?.show()
    }

    private fun handleThrowableMessage(ex: Throwable): String {
        return when (ex) {
            is UniqueConstraintException -> getString(R.string.error_item_already_registered)
            else -> getString(R.string.error_unknown)
        }
    }

    private fun retryToCreateBorrowing() {
        Timber.tag(TAG).i("Retried.")
        binding?.let { it.viewModel?.handle(CreateBorrowingViewEvent.CreateClicked(it.thing)) }
    }

    override fun renderViewEffect(viewEffect: CreateBorrowingViewEffect) {
        when (viewEffect) {
            is CreateBorrowingViewEffect.ShowSuccessDialog -> Timber.tag(TAG).i("ShowSuccessDialog")
            CreateBorrowingViewEffect.NavigateBack -> navigateBack()
            is CreateBorrowingViewEffect.ShowFailureDialog -> Timber.tag(TAG).i("ShowFailureDialog")
            CreateBorrowingViewEffect.OpenTakePictureDialog -> openTakePictureDialog()
        }
    }

    private fun navigateBack() {
        Timber.tag(TAG).i("NavigateBack")
        onBackPressed()
    }

    private fun openTakePictureDialog() {
        Timber.tag(TAG).i("openTakePictureDialog")
        dialog = null
        dialog = alert(
            getString(R.string.take_picture_dialog_title),
            getString(R.string.take_picture_dialog_message)
        ) {
            positiveButton(getString(R.string.button_camera)) { openCamera() }
            negativeButton(getString(R.string.button_gallery)) { openGallery() }
            cancelable = true
        }
        dialog?.show()
    }

    private fun openCamera() {
        Timber.tag(TAG).i("openCamera")
        dispatchTakePictureIntent()
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                Timber.tag(TAG).i("Error occurred while creating the File")
                null
            }.also { pictureFile = it }

            // Continue only if the File was successfully created
            photoFile?.also {
                val photoUri: Uri = FileProvider.getUriForFile(
                    this,
                    applicationContext.packageName + ".fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                resultLauncher.launch(takePictureIntent)
            }
        }
    }

    private fun openGallery() {
        Timber.tag(TAG).i("openGallery")
    }

    private fun updatePictureView(bitmap: Bitmap?) {
        Timber.tag(TAG).i("doSomeOperations:[$bitmap]")
        binding?.imageView?.setImageBitmap(bitmap)
    }

    override fun onDestroy() {
        super.onDestroy()
        // releasing resources
        binding = null
        dialog?.dismiss()
        dialog = null
    }

}

