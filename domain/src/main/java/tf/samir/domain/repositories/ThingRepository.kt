package tf.samir.domain.repositories

import kotlinx.coroutines.flow.Flow
import tf.samir.domain.entities.ThingEntity

interface ThingRepository {
    val allThings: Flow<List<ThingEntity>>
    val thingsAtHome: Flow<List<ThingEntity>>
}