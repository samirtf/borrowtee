package tf.samir.domain.usecases

import kotlinx.coroutines.flow.Flow
import tf.samir.domain.entities.ThingEntity
import tf.samir.domain.entities.ThingState

interface GetThingsUseCase {
    operator fun invoke(@ThingState filter: Int? = null): Result<Flow<List<ThingEntity>>>
}