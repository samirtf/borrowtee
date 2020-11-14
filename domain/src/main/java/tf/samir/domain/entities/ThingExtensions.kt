package tf.samir.domain.entities

fun Thing.isAtHome(): Boolean {
    return this.state == AT_HOME
}