package tf.samir.borrowtee.features.borrowing.view.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import tf.samir.borrowtee.R
import tf.samir.borrowtee.databinding.ActivityCreateBorrowingBinding
import tf.samir.borrowtee.features.borrowing.presentation.presenter.create.CreateBorrowingViewModel
import tf.samir.borrowtee.features.main.view.all_things.AllThingsFragment
import timber.log.Timber

class CreateBorrowingActivity : AppCompatActivity() {

    companion object {
        const val TAG = "CreateBorrowingA"
    }

    private val viewModel by viewModels<CreateBorrowingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // The layout for this activity is a Data Binding layout so it needs to be inflated using
        // DataBindingUtil.
        val binding: ActivityCreateBorrowingBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_borrowing)
        binding.viewModel = viewModel
        // Specify the current activity as the lifecycle owner.
        binding.lifecycleOwner = this

        viewModel.event.observe(this, Observer {
            when (it) {
                CreateBorrowingViewModel.State.Idle -> Timber.tag(AllThingsFragment.TAG).i("Idle")
                CreateBorrowingViewModel.State.CreateBorrowing -> Timber.tag(AllThingsFragment.TAG).i("Loading")
                CreateBorrowingViewModel.State.Success -> {
                    Timber.tag(AllThingsFragment.TAG).i("Success")
                    viewModel.onActionComplete()
                }
                CreateBorrowingViewModel.State.Error -> {
                    Timber.tag(AllThingsFragment.TAG).i("Error")
                    viewModel.onActionComplete()
                }
                else -> {}
            }
        })
    }



}