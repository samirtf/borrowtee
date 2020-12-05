package tf.samir.domain.usecases

import tf.samir.domain.entities.ThingEntity


interface CreateBorrowingUseCase {
    suspend operator fun invoke(param: ThingEntity): Result<Boolean>
}