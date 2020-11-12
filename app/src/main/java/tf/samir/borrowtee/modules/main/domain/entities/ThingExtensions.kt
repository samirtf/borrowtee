package tf.samir.borrowtee.modules.main.domain.entities

fun Thing.isAtHome(): Boolean {
    return this.state == AT_HOME
}