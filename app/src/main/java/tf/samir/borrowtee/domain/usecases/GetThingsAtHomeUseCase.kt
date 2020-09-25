package tf.samir.borrowtee.domain.usecases

import tf.samir.borrowtee.data.repositories.ThingsRepository
import tf.samir.borrowtee.model.Thing

class GetThingsAtHomeUseCase(private val thingsRepository: ThingsRepository) {
    operator fun invoke(): List<Thing> = thingsRepository.getThingsAtHome()
}