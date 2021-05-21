package tf.samir.infrastructure

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import tf.samir.data.datasource.ThingLocalDataSource
import tf.samir.data.repository.ThingRepositoryImpl
import tf.samir.domain.repository.ThingRepository
import tf.samir.infrastructure.dao.ThingModelDao
import tf.samir.infrastructure.datasource.ThingLocalDataSourceImpl
import tf.samir.infrastructure.mapper.DbMapper
import tf.samir.infrastructure.mapper.DbMapperImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
object InfrastructureModule {

    @Provides
    fun providesThingsDao(@ApplicationContext appContext: Context): ThingModelDao {
        return BorrowteeDatabase.getDatabase(appContext).thingModelDao()
    }

    @Provides
    fun providesDbMapper(): DbMapper {
        return DbMapperImpl()
    }

    @Provides
    fun providesThingLocalDataSource(dbMapper: DbMapper, thingModelDao: ThingModelDao): ThingLocalDataSource {
        return ThingLocalDataSourceImpl(dbMapper, thingModelDao)
    }

    @Provides
    fun providesThingRepository(localDataSource: ThingLocalDataSource): ThingRepository {
        return ThingRepositoryImpl(localDataSource)
    }



}