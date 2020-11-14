package tf.samir.domain.entities

fun ThingEntity.isAtHome(): Boolean {
    return this.state == AT_HOME
}