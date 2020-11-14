package tf.samir.borrowtee.core.dependency_injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import tf.samir.domain.repositories.ThingRepository
import tf.samir.borrowtee.modules.main.infrastructure.repositories.InMemoryThingRepositoryMock

// Repositories will live same as the activity that requires them
@Module
@InstallIn(ActivityComponent::class)
abstract class ThingRepositoryModule {

    @Binds
    abstract fun providesThingRepository(impl: InMemoryThingRepositoryMock): tf.samir.domain.repositories.ThingRepository
}