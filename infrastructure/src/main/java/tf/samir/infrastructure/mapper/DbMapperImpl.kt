package tf.samir.infrastructure.mapper

import tf.samir.data.model.LocalThingModel
import tf.samir.domain.entities.ThingEntity
import javax.inject.Inject

class DbMapperImpl @Inject constructor(): DbMapper {

    override fun mapDomainThingToDb(thingEntity: ThingEntity): LocalThingModel {
        return with(thingEntity) {
            LocalThingModel(id, name, description, state)
        }
    }

    override fun mapThingModelToDomain(thingModel: LocalThingModel): ThingEntity {
        return with(thingModel) {
            ThingEntity(id, name, description, state)
        }
    }

    override fun mapDomainThingsToDb(thingEntities: List<ThingEntity>): List<LocalThingModel> {
        return thingEntities.map {
            mapDomainThingToDb(it)
        }
    }

    override fun mapThingModelsToDomain(thingModels: List<LocalThingModel>): List<ThingEntity> {
        return thingModels.map {
            mapThingModelToDomain(it)
        }
    }
}