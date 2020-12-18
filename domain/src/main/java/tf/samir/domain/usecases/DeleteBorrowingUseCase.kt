package tf.samir.domain.usecases


interface DeleteBorrowingUseCase {
    suspend operator fun invoke(thingId: String): Result<Boolean>
}