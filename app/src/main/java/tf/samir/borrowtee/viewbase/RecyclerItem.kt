package tf.samir.borrowtee.viewbase

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import tf.samir.borrowtee.modules.main.domain.entities.Thing

data class RecyclerItem(
    val data: Thing,
    @LayoutRes val layoutId: Int,
    val variableId: Int
) {
    fun bind(binding: ViewDataBinding) {
        binding.setVariable(variableId, data)
    }
}