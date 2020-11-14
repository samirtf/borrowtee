package tf.samir.infrastructure.mapper

import tf.samir.data.model.LocalThingModel
import tf.samir.domain.entities.ThingEntity

interface DbMapper {

    fun mapDomainThingToDb(thingEntity: ThingEntity): LocalThingModel

    fun mapThingModelToDomain(thingModel: LocalThingModel): ThingEntity

    fun mapDomainThingsToDb(thingEntities: List<ThingEntity>): List<LocalThingModel>

    fun mapThingModelsToDomain(thingModels: List<LocalThingModel>): List<ThingEntity>
}