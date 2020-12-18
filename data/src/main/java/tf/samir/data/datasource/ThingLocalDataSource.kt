package tf.samir.data.datasource

import kotlinx.coroutines.flow.Flow
import tf.samir.domain.entities.ThingEntity
import tf.samir.domain.entities.ThingState


interface ThingLocalDataSource {

    fun getAllThings(): Flow<List<ThingEntity>>
    fun getAllThingsBy(@ThingState state: Int): Flow<List<ThingEntity>>
    suspend fun insert(thing: ThingEntity)
    suspend fun update(thing: ThingEntity)
    suspend fun deleteAll()
    fun delete(thingId: String)
}