package tf.samir.borrowtee.modules.main.utils

import tf.samir.borrowtee.BR
import tf.samir.borrowtee.R
import tf.samir.borrowtee.modules.main.domain.entities.Thing
import tf.samir.borrowtee.viewbase.RecyclerItem

fun Thing.toRecyclerItem() = RecyclerItem(
    data = this,
    variableId = BR.thing,
    layoutId = R.layout.thing_at_home_item
)