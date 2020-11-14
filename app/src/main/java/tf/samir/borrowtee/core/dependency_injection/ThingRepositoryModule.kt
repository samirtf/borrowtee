package tf.samir.borrowtee.core.dependency_injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import tf.samir.data.repository.InMemoryThingRepositoryMock
import tf.samir.domain.repository.ThingRepository

// Repositories will live same as the activity that requires them
@Module
@InstallIn(ActivityComponent::class)
abstract class ThingRepositoryModule {

    @Binds
    abstract fun providesThingRepository(impl: InMemoryThingRepositoryMock): ThingRepository
}