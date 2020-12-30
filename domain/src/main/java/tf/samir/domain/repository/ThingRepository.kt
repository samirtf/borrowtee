package tf.samir.domain.repository

import kotlinx.coroutines.flow.Flow
import tf.samir.domain.entities.ThingEntity
import tf.samir.domain.entities.ThingState

interface ThingRepository {
//    val allThings: Flow<List<ThingEntity>>
//    val thingsAtHome: Flow<List<ThingEntity>>
    fun fetchThings(@ThingState state: Int? = null): Flow<List<ThingEntity>>
    suspend fun insertThing(thingEntity: ThingEntity)
    suspend fun updateThing(thingEntity: ThingEntity)
    suspend fun deleteAll()
    fun deleteThing(thingId: String)
}