package tf.samir.data.usecase

import tf.samir.domain.entities.ThingEntity
import tf.samir.domain.repository.ThingRepository
import tf.samir.domain.usecases.CreateBorrowingUseCase
import javax.inject.Inject

class CreateBorrowingUseCaseImpl @Inject constructor(private val thingRepository: ThingRepository) :
    CreateBorrowingUseCase {

    override suspend operator fun invoke(param: ThingEntity): Result<Boolean> =
        runCatching {
            thingRepository.insertThing(param)
            return Result.success(true)
        }.onFailure { return Result.failure(it) }

}