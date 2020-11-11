package tf.samir.borrowtee.modules.main.infrastructure.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tf.samir.borrowtee.modules.main.domain.entities.Thing
import tf.samir.borrowtee.modules.main.domain.repositories.ThingRepository
import tf.samir.borrowtee.modules.main.infrastructure.dao.ThingModelDao
import tf.samir.borrowtee.modules.main.infrastructure.mapper.DbMapper

class ThingRepositoryImpl(private val dbMapper: DbMapper, private val thingModelDao: ThingModelDao): ThingRepository {

    override val allThings: Flow<List<Thing>> = thingModelDao.getAllThings().map { dbMapper.mapThingModelsToDomain(it) }

    override val thingsAtHome: Flow<List<Thing>>
        get() = TODO("Not yet implemented")

}