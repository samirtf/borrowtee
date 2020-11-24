package tf.samir.domain.usecases

import kotlinx.coroutines.flow.Flow
import tf.samir.domain.entities.ThingEntity

interface GetThingsUseCase {
    operator fun invoke(): Result<Flow<List<ThingEntity>>>
}