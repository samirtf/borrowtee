package tf.samir.domain.usecases

import kotlinx.coroutines.flow.Flow
import tf.samir.domain.entities.ThingEntity

interface CreateBorrowingUseCase {
    operator fun invoke(): Result<Flow<List<ThingEntity>>>
}