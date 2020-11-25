package tf.samir.data.usecase

import kotlinx.coroutines.flow.Flow
import tf.samir.domain.entities.ThingEntity
import tf.samir.domain.repository.ThingRepository
import tf.samir.domain.usecases.GetThingsUseCase
import javax.inject.Inject

class GetThingsUseCaseImpl @Inject constructor(private val thingRepository: ThingRepository) :
    GetThingsUseCase {

    override operator fun invoke(): Result<Flow<List<ThingEntity>>> =
        kotlin.runCatching { thingRepository.allThings }

}