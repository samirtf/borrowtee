package tf.samir.borrowtee.domain.entities

interface Thing {
    val id: String
    val name: String
    val description: String
    @ThingState
    val state: Int
}