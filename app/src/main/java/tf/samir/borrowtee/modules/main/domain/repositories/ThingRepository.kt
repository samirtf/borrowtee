package tf.samir.borrowtee.modules.main.domain.repositories

import kotlinx.coroutines.flow.Flow
import tf.samir.borrowtee.modules.main.domain.entities.Thing

interface ThingRepository {
    val allThings: Flow<List<Thing>>
    val thingsAtHome: Flow<List<Thing>>
}