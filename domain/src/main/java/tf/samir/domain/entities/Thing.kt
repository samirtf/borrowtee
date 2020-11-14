package tf.samir.domain.entities

data class Thing (
    val id: String,
    val name: String,
    val description: String,
    @ThingState
    val state: Int
)