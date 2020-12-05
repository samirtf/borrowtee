package tf.samir.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import tf.samir.data.datasource.ThingLocalDataSource
import tf.samir.domain.entities.AT_HOME
import tf.samir.domain.entities.ThingEntity
import tf.samir.domain.repository.ThingRepository

class ThingRepositoryImpl(
    private val localDataSource: ThingLocalDataSource): ThingRepository {

    @ExperimentalCoroutinesApi
    override val allThings: Flow<List<ThingEntity>> = localDataSource.getAllThings()

    @ExperimentalCoroutinesApi
    override val thingsAtHome: Flow<List<ThingEntity>> = localDataSource.getAllThingsBy(AT_HOME)

    override suspend fun insertThing(thingEntity: ThingEntity) = localDataSource.insert(thingEntity)

    override suspend fun updateThing(thingEntity: ThingEntity) = localDataSource.insert(thingEntity)

    override suspend fun deleteAll() = localDataSource.deleteAll()

}