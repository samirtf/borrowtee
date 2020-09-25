package tf.samir.borrowtee.model

interface Thing {
    val id: String
    val name: String
    val description: String
    @ThingState val state: Int
}