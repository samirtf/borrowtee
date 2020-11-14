package tf.samir.data.usecase

import kotlinx.coroutines.flow.Flow
import tf.samir.domain.entities.ThingEntity
import tf.samir.domain.usecases.GetThingsAtHomeUseCase

class GetThingsAtHomeUseCaseCase(private val thingRepository: tf.samir.domain.repository.ThingRepository) :
    GetThingsAtHomeUseCase {
    override operator fun invoke(): Flow<List<ThingEntity>> = thingRepository.thingsAtHome
}