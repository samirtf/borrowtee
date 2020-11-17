package tf.samir.borrowtee.features.borrowing.utils

import android.view.View
import androidx.databinding.BindingAdapter
import tf.samir.borrowtee.features.borrowing.presentation.presenter.create.CreateBorrowingViewModel

@BindingAdapter("setLoading")
fun setLoading(view: View, value: CreateBorrowingViewModel.State) {
    view.visibility = if (value != CreateBorrowingViewModel.State.Idle) View.VISIBLE else View.GONE
}

@BindingAdapter("setEnable")
fun setEnable(view: View, value: CreateBorrowingViewModel.State) {
    view.isEnabled = value == CreateBorrowingViewModel.State.Idle
}
