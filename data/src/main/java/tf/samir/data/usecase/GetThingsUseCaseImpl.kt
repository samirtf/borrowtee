package tf.samir.data.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import tf.samir.domain.entities.ThingEntity
import tf.samir.domain.entities.ThingState
import tf.samir.domain.repository.ThingRepository
import tf.samir.domain.usecases.GetThingsUseCase
import javax.inject.Inject

class GetThingsUseCaseImpl @Inject constructor(private val thingRepository: ThingRepository) :
    GetThingsUseCase {

    override fun invoke(@ThingState filter: Int?): Result<Flow<List<ThingEntity>>> {
        return kotlin.runCatching {
            if (filter != null) {
                thingRepository.allThings.map { value: List<ThingEntity> -> value.filter { thingEntity -> thingEntity.state == filter ?: false } }
            } else {
                thingRepository.allThings
            }
        }
    }


}