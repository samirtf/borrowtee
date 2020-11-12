package tf.samir.borrowtee.modules.main.domain.usecases

import kotlinx.coroutines.flow.Flow
import tf.samir.borrowtee.modules.main.domain.entities.Thing
import tf.samir.borrowtee.modules.main.domain.repositories.ThingRepository

class GetThingsAtHomeUseCase(private val thingRepository: ThingRepository) {
    operator fun invoke(): Flow<List<Thing>> = thingRepository.thingsAtHome
}