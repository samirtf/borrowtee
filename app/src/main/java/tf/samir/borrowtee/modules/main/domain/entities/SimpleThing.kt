package tf.samir.borrowtee.modules.main.domain.entities

class SimpleThing(
    override val id: String,
    override val name: String,
    override val description: String,
    override val state: Int
) : Thing