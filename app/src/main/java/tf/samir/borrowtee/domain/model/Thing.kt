package tf.samir.borrowtee.domain.model

interface Thing {
    val id: String
    val name: String
    val description: String
    val state: Int
}