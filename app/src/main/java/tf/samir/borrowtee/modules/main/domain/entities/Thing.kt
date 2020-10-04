package tf.samir.borrowtee.modules.main.domain.entities

interface Thing {
    val id: String
    val name: String
    val description: String
    @ThingState
    val state: Int
}