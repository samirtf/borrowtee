package tf.samir.borrowtee.features.borrowing.view.create

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_create_borrowing.*
import tf.samir.borrowtee.R
import tf.samir.borrowtee.databinding.ActivityCreateBorrowingBinding
import tf.samir.borrowtee.features.borrowing.presentation.presenter.create.*
import tf.samir.borrowtee.features.main.view.all_things.AllThingsFragment
import tf.samir.borrowtee.features.main.view.all_things.AllThingsFragmentDirections
import tf.samir.borrowtee.viewbase.alert
import tf.samir.core.base.HyperActivity
import timber.log.Timber

@AndroidEntryPoint
class CreateBorrowingActivity :
    HyperActivity<CreateBorrowingViewState, CreateBorrowingViewEffect, CreateBorrowingViewEvent, CreateBorrowingViewModel>() {

    companion object {
        const val TAG = "CreateBorrowingA"
    }

    override val viewModel by viewModels<CreateBorrowingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // The layout for this activity is a Data Binding layout so it needs to be inflated using
        // DataBindingUtil.
        val binding: ActivityCreateBorrowingBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_create_borrowing)
        binding.viewModel = viewModel
        binding.thing = ThingData("trajano")
        binding.button.setOnClickListener {
            binding.thing.let { viewModel.handle(CreateBorrowingViewEvent.CreateClicked(it)) }
        }
        // Specify the current activity as the lifecycle owner.
        binding.lifecycleOwner = this
    }

    override fun renderViewState(viewState: CreateBorrowingViewState) {
        when (viewState.createStatus) {
            CreateStatus.Creating -> updateCreatingUiState()
            CreateStatus.Created -> updateCreatedUiState()
            CreateStatus.NotCreated -> updateNotCreatedUiState()
        }
    }

    private fun updateCreatingUiState() {
//        progressBar.visibility = View.VISIBLE
//        layoutName.isEnabled = false
//        layoutDescription.isEnabled = false
    }

    private fun updateCreatedUiState() {
//        progressBar.visibility = View.INVISIBLE
//        layoutName.isEnabled = true
//        layoutDescription.isEnabled = false
    }

    private fun updateNotCreatedUiState() {
//        progressBar.visibility = View.INVISIBLE
//        layoutName.isEnabled = true
//        layoutDescription.isEnabled = true
    }


    override fun renderViewEffect(viewEffect: CreateBorrowingViewEffect) {
        when (viewEffect) {
            is CreateBorrowingViewEffect.ShowSuccessDialog -> {
                Timber.tag(TAG).i("ShowSuccessDialog")
                showSuccessDialog()
            }
            CreateBorrowingViewEffect.NavigateBack -> {
                Timber.tag(TAG).i("NavigateBack")
                onBackPressed()
            }
            is CreateBorrowingViewEffect.ShowFailureDialog -> {
                Timber.tag(TAG).i("ShowFailureDialog")
                showFailureDialog()
            }
        }
    }

    private fun showSuccessDialog() {
        alert("Borrowing created", "The borrowing was successfully created!") {
            positiveButton("Ok") {
                Timber.tag(TAG).i("NavigateBack")
                onBackPressed()
            }
            cancelable = false
        }.show()
    }

    private fun showFailureDialog() {
        alert("Failed to create borrowing", "Unable to create borrowing") {
            positiveButton("Retry") {
                Timber.tag(TAG).i("Retried.")
            }
            negativeButton("Cancel"){
                Timber.tag(TAG).i("Cancelled.")
                onBackPressed()
            }
            cancelable = false
        }.show()
    }

}