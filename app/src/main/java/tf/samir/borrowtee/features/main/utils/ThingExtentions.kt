package tf.samir.borrowtee.features.main.utils

import tf.samir.borrowtee.BR
import tf.samir.borrowtee.R
import tf.samir.borrowtee.viewbase.RecyclerItem
import tf.samir.domain.entities.ThingEntity


fun ThingEntity.toRecyclerItem() = RecyclerItem(
    data = this,
    variableId = BR.thing,
    layoutId = R.layout.thing_at_home_item
)