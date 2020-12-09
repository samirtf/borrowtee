package tf.samir.borrowtee.core.dependency_injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import tf.samir.infrastructure.BorrowteeDatabase
import tf.samir.infrastructure.dao.ThingModelDao
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object DatabaseModule {

    @Provides
    fun providesThingsDao(@ApplicationContext appContext: Context): ThingModelDao {
        return BorrowteeDatabase.getDatabase(appContext).thingModelDao()
    }

}
