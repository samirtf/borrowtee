package tf.samir.infrastructure.datasource

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tf.samir.data.datasource.ThingLocalDataSource
import tf.samir.domain.entities.ThingEntity
import tf.samir.domain.repository.ThingRepository
import tf.samir.infrastructure.mapper.DbMapper


class ThingLocalDataSourceImpl(
    private val dbMapper: DbMapper,
    private val thingModelDao: tf.samir.infrastructure.dao.ThingModelDao): ThingLocalDataSource {


    @ExperimentalCoroutinesApi
    override fun getAllThings(): Flow<List<ThingEntity>> = thingModelDao
        .getAllThingsDistinctUntilChanged().map { dbMapper.mapThingModelsToDomain(it) }

    @ExperimentalCoroutinesApi
    override fun getAllThingsBy(state: Int): Flow<List<ThingEntity>> = thingModelDao
        .getAllThingsByBorrowStateDistinctUntilChanged(state).map { dbMapper.mapThingModelsToDomain(it) }

    override suspend fun insert(thing: ThingEntity) = thingModelDao.insert(dbMapper.mapDomainThingToDb(thing))

    override suspend fun update(thing: ThingEntity) = thingModelDao.update(dbMapper.mapDomainThingToDb(thing))

    override suspend fun deleteAll() = thingModelDao.deleteAll()

}