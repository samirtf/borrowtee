package tf.samir.borrowtee.features.borrowing.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("setLoading")
fun setLoading(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}