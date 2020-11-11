package tf.samir.borrowtee.modules.main.infrastructure.mapper

import tf.samir.borrowtee.modules.main.domain.entities.Thing
import tf.samir.borrowtee.modules.main.infrastructure.model.ThingModel

class DbMapperImpl: DbMapper {
    override fun mapDomainThingToDb(thing: Thing): ThingModel {
        return with(thing) {
            ThingModel(id, name, description, state)
        }
    }

    override fun mapThingModelToDomain(thingModel: ThingModel): Thing {
        return with(thingModel) {
            Thing(id, name, description, state)
        }
    }

    override fun mapDomainThingsToDb(things: List<Thing>): List<ThingModel> {
        return things.map {
            mapDomainThingToDb(it)
        }
    }

    override fun mapThingModelsToDomain(thingModels: List<ThingModel>): List<Thing> {
        return thingModels.map {
            mapThingModelToDomain(it)
        }
    }
}