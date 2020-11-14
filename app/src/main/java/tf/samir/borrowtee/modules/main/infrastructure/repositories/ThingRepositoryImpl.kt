package tf.samir.borrowtee.modules.main.infrastructure.repositories

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import tf.samir.borrowtee.modules.main.domain.entities.Thing
import tf.samir.domain.repositories.ThingRepository
import tf.samir.infrastructure.dao.ThingModelDao
import tf.samir.infrastructure.mapper.DbMapper

class ThingRepositoryImpl(private val dbMapper: tf.samir.infrastructure.mapper.DbMapper, thingModelDao: tf.samir.infrastructure.dao.ThingModelDao):
    tf.samir.domain.repositories.ThingRepository {

    @ExperimentalCoroutinesApi
    override val allThings: Flow<List<Thing>> = thingModelDao
        .getAllThingsDistinctUntilChanged().map { dbMapper.mapThingModelsToDomain(it) }

    override val thingsAtHome: Flow<List<Thing>> = thingModelDao
        .getAllThingsAtHomeDistinctUntilChanged().map { dbMapper.mapThingModelsToDomain(it) }

}