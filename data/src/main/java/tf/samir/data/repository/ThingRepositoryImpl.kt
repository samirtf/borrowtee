package tf.samir.data.repository

import kotlinx.coroutines.flow.Flow
import tf.samir.data.datasource.ThingLocalDataSource
import tf.samir.domain.entities.AT_HOME
import tf.samir.domain.entities.ThingEntity
import tf.samir.domain.entities.ThingState
import tf.samir.domain.repository.ThingRepository
import javax.inject.Inject

class ThingRepositoryImpl @Inject constructor(
    private val localDataSource: ThingLocalDataSource
) : ThingRepository {

    override fun fetchThings(@ThingState state: Int?): Flow<List<ThingEntity>> {
        return if (state != null) {
            localDataSource.getAllThingsBy(state)
        } else {
            localDataSource.getAllThings()
        }
    }

    override suspend fun insertThing(thingEntity: ThingEntity) = localDataSource.insert(thingEntity)

    override suspend fun updateThing(thingEntity: ThingEntity) = localDataSource.insert(thingEntity)

    override suspend fun deleteAll() = localDataSource.deleteAll()

    override fun deleteThing(thingId: String) = localDataSource.delete(thingId)

}