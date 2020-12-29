package tf.samir.infrastructure.datasource

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tf.samir.data.datasource.ThingLocalDataSource
import tf.samir.domain.entities.ThingEntity
import tf.samir.infrastructure.dao.ThingModelDao
import tf.samir.infrastructure.datasource.failures.GenericConstraintException
import tf.samir.infrastructure.datasource.failures.LocalDataSourceException
import tf.samir.infrastructure.datasource.failures.UniqueConstraintException
import tf.samir.infrastructure.mapper.DbMapper
import javax.inject.Inject


class ThingLocalDataSourceImpl @Inject constructor(
    private val dbMapper: DbMapper,
    private val thingModelDao: ThingModelDao): ThingLocalDataSource {


    @ExperimentalCoroutinesApi
    override fun getAllThings(): Flow<List<ThingEntity>> = thingModelDao
        .getAllThingsDistinctUntilChanged().map { dbMapper.mapThingModelsToDomain(it) }

    @ExperimentalCoroutinesApi
    override fun getAllThingsBy(state: Int): Flow<List<ThingEntity>> = thingModelDao
        .getAllThingsByBorrowStateDistinctUntilChanged(state).map { dbMapper.mapThingModelsToDomain(it) }

    override suspend fun insert(thing: ThingEntity) {
        kotlin.runCatching {
            thingModelDao.insert(dbMapper.mapDomainThingToDb(thing))
        }.onFailure { handleException(it) }
    }

    private fun handleException(it: Throwable) {
        when (it) { is SQLiteConstraintException -> { it.reThrow() } }
        throw LocalDataSourceException()
    }

    private fun SQLiteConstraintException.reThrow() {
        if (this.message?.contains("UNIQUE") == true) {
            throw UniqueConstraintException()
        }
        throw GenericConstraintException()
    }

    override suspend fun update(thing: ThingEntity) = thingModelDao.update(dbMapper.mapDomainThingToDb(thing))

    override suspend fun deleteAll() = thingModelDao.deleteAll()

    override fun delete(thingId: String) = thingModelDao.delete(thingId)

}