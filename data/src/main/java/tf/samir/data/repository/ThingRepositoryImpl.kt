package tf.samir.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import tf.samir.domain.entities.ThingEntity
import tf.samir.domain.repository.ThingRepository
import tf.samir.infrastructure.mapper.DbMapper

class ThingRepositoryImpl(
    private val dbMapper: DbMapper,
    private val thingModelDao: tf.samir.infrastructure.dao.ThingModelDao): ThingRepository {

    @ExperimentalCoroutinesApi
    override val allThings: Flow<List<ThingEntity>> = thingModelDao
        .getAllThingsDistinctUntilChanged().map { dbMapper.mapThingModelsToDomain(it) }

    @ExperimentalCoroutinesApi
    override val thingsAtHome: Flow<List<ThingEntity>> = thingModelDao
        .getAllThingsAtHomeDistinctUntilChanged().map { dbMapper.mapThingModelsToDomain(it) }

}