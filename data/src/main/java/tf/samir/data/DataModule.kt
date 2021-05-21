package tf.samir.data

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import tf.samir.data.usecase.CreateBorrowingUseCaseImpl
import tf.samir.data.usecase.DeleteBorrowingUseCaseImpl
import tf.samir.data.usecase.GetThingsUseCaseImpl
import tf.samir.domain.repository.ThingRepository
import tf.samir.domain.usecases.CreateBorrowingUseCase
import tf.samir.domain.usecases.DeleteBorrowingUseCase
import tf.samir.domain.usecases.GetThingsUseCase

// Repositories will live same as the activity that requires them
@Module
@InstallIn(ActivityRetainedComponent::class)
object DataModule {

    @Provides
    fun providesGetThingsAtHomeUseCase(repository: ThingRepository): GetThingsUseCase {
        return GetThingsUseCaseImpl(repository)
    }

    @Provides
    fun providesCreateBorrowingUseCase(repository: ThingRepository): CreateBorrowingUseCase {
        return CreateBorrowingUseCaseImpl(repository)
    }

    @Provides
    fun providesDeleteBorrowingUseCase(repository: ThingRepository): DeleteBorrowingUseCase {
        return DeleteBorrowingUseCaseImpl(repository)
    }

}