package tf.samir.borrowtee.core.dependency_injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import tf.samir.data.repository.InMemoryThingRepositoryMock
import tf.samir.data.usecase.CreateBorrowingUseCaseImpl
import tf.samir.data.usecase.GetThingsUseCaseImpl
import tf.samir.domain.repository.ThingRepository
import tf.samir.domain.usecases.CreateBorrowingUseCase
import tf.samir.domain.usecases.GetThingsUseCase

// Repositories will live same as the activity that requires them
@Module
@InstallIn(ActivityComponent::class)
abstract class ThingRepositoryModule {

    @Binds
    abstract fun providesGetThingsAtHomeUseCase(impl: GetThingsUseCaseImpl): GetThingsUseCase

    @Binds
    abstract fun providesCreateBorrowingUseCase(impl: CreateBorrowingUseCaseImpl): CreateBorrowingUseCase

    @Binds
    abstract fun providesThingRepository(impl: InMemoryThingRepositoryMock): ThingRepository
}