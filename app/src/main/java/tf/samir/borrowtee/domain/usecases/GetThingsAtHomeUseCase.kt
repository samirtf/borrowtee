package tf.samir.borrowtee.domain.usecases

import tf.samir.borrowtee.domain.repositories.ThingsRepository
import tf.samir.borrowtee.domain.entities.Thing

class GetThingsAtHomeUseCase(private val thingsRepository: ThingsRepository) {
    operator fun invoke(): List<Thing> = thingsRepository.getThingsAtHome()
}