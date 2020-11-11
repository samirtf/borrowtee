package tf.samir.borrowtee.modules.main.infrastructure.mapper

import tf.samir.borrowtee.modules.main.domain.entities.Thing
import tf.samir.borrowtee.modules.main.infrastructure.model.ThingModel

interface DbMapper {

    fun mapDomainThingToDb(thing: Thing): ThingModel

    fun mapThingModelToDomain(thingModel: ThingModel): Thing

    fun mapDomainThingsToDb(things: List<Thing>): List<ThingModel>

    fun mapThingModelsToDomain(thingModels: List<ThingModel>): List<Thing>
}