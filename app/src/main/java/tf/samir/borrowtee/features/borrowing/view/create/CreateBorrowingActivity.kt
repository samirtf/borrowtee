package tf.samir.borrowtee.features.borrowing.view.create

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import tf.samir.borrowtee.R
import tf.samir.borrowtee.databinding.ActivityCreateBorrowingBinding
import tf.samir.borrowtee.features.borrowing.presentation.presenter.create.*
import tf.samir.borrowtee.viewbase.alert
import tf.samir.core.base.HyperActivity
import tf.samir.infrastructure.datasource.failures.UniqueConstraintException
import timber.log.Timber

@AndroidEntryPoint
class CreateBorrowingActivity :
    HyperActivity<CreateBorrowingViewState, CreateBorrowingViewEffect, CreateBorrowingViewEvent, CreateBorrowingViewModel>() {

    companion object {
        const val TAG = "CreateBorrowingA"
    }

    override val viewModel by viewModels<CreateBorrowingViewModel>()
    private var dialog: AlertDialog? = null
    private var binding: ActivityCreateBorrowingBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // The layout for this activity is a Data Binding layout so it needs to be inflated using
        // DataBindingUtil.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_borrowing)
        binding?.let { binding ->
            binding.viewModel = viewModel
            binding.thing = ThingData("", "")
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
        }
    }

    private fun navigateBack() {
        Timber.tag(TAG).i("NavigateBack")
        onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        // releasing resources
        binding = null
        dialog?.dismiss()
        dialog = null
    }

}