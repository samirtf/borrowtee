package tf.samir.data.usecase

import android.util.Log
import tf.samir.domain.repository.ThingRepository
import tf.samir.domain.usecases.DeleteBorrowingUseCase
import javax.inject.Inject

class DeleteBorrowingUseCaseImpl @Inject constructor(private val thingRepository: ThingRepository) :
    DeleteBorrowingUseCase {

    override suspend operator fun invoke(thingId: String): Result<Boolean> =
        runCatching {
            thingRepository.deleteThing(thingId)
            Log.d("STF", "SUCESSO")
            return Result.success(true)
        }.onFailure {
            Log.d("STF", "$it")
            return Result.failure(it)
        }

}