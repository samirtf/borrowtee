package tf.samir.borrowtee.data.repositories

import tf.samir.borrowtee.model.Thing

interface ThingsRepository {
    fun getThingsAtHome(): List<Thing>
}