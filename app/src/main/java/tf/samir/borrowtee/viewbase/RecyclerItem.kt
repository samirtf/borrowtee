package tf.samir.borrowtee.viewbase

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import tf.samir.domain.entities.ThingEntity

data class RecyclerItem(
    val data: ThingEntity,
    @LayoutRes val layoutId: Int,
    val variableId: Int
) {
    fun bind(binding: ViewDataBinding) {
        binding.setVariable(variableId, data)
    }
}