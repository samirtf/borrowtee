package tf.samir.borrowtee.modules.main.domain.usecases

import tf.samir.borrowtee.modules.main.domain.entities.Thing
import tf.samir.borrowtee.modules.main.domain.repositories.ThingsRepository

class GetThingsAtHomeUseCase(private val thingsRepository: ThingsRepository) {
    operator fun invoke(): List<Thing> = thingsRepository.getThingsAtHome()
}