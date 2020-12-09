package tf.samir.infrastructure.datasource

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tf.samir.data.datasource.ThingLocalDataSource
import tf.samir.domain.entities.ThingEntity
import tf.samir.infrastructure.dao.ThingModelDao
import tf.samir.infrastructure.mapper.DbMapper
import javax.inject.Inject


class ThingLocalDataSourceImpl @Inject constructor(
    private val dbMapper: DbMapper,
    private val thingModelDao: ThingModelDao): ThingLocalDataSource {


    @ExperimentalCoroutinesApi
    override fun getAllThings(): Flow<List<ThingEntity>> = thingModelDao
        .getAllThingsDistinctUntilChanged().map {
            val mapThingModelsToDomain = dbMapper.mapThingModelsToDomain(it)
            Log.e("TLDSI", "$mapThingModelsToDomain")
            mapThingModelsToDomain
        }

    @ExperimentalCoroutinesApi
    override fun getAllThingsBy(state: Int): Flow<List<ThingEntity>> = thingModelDao
        .getAllThingsByBorrowStateDistinctUntilChanged(state).map { dbMapper.mapThingModelsToDomain(it) }

    override suspend fun insert(thing: ThingEntity) = thingModelDao.insert(dbMapper.mapDomainThingToDb(thing))

    override suspend fun update(thing: ThingEntity) = thingModelDao.update(dbMapper.mapDomainThingToDb(thing))

    override suspend fun deleteAll() = thingModelDao.deleteAll()

}