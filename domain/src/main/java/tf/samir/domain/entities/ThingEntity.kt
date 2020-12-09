package tf.samir.domain.entities

import java.util.*

data class ThingEntity (
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String = "",
    @ThingState
    val state: Int = AT_HOME
)