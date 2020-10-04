package tf.samir.borrowtee.modules.main.domain.repositories

import tf.samir.borrowtee.modules.main.domain.entities.Thing

interface ThingsRepository {
    fun getThingsAtHome(): List<Thing>
}