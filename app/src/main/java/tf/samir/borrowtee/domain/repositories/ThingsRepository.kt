package tf.samir.borrowtee.domain.repositories

import tf.samir.borrowtee.domain.entities.Thing

interface ThingsRepository {
    fun getThingsAtHome(): List<Thing>
}