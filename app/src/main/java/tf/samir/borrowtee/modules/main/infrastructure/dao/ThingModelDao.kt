package tf.samir.borrowtee.modules.main.infrastructure.dao

import androidx.room.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import tf.samir.borrowtee.modules.main.domain.entities.AT_HOME
import tf.samir.borrowtee.modules.main.domain.entities.ThingState
import tf.samir.borrowtee.modules.main.infrastructure.model.ThingModel
import tf.samir.borrowtee.modules.main.infrastructure.model.tableNameThing

@Dao
interface ThingModelDao {

    @Query("SELECT * FROM $tableNameThing ORDER BY name ASC")
    fun getAllThings(): Flow<List<ThingModel>>

    @ExperimentalCoroutinesApi
    fun getAllThingsDistinctUntilChanged() = getAllThings().distinctUntilChanged()

    @Query("SELECT * FROM $tableNameThing WHERE state = :state ORDER BY name ASC")
    fun getAllThingsBy(@ThingState state: Int): Flow<List<ThingModel>>

    @ExperimentalCoroutinesApi
    fun getAllThingsAtHomeDistinctUntilChanged() = getAllThingsBy(AT_HOME).distinctUntilChanged()


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(thing: ThingModel)

    @Query("DELETE FROM $tableNameThing")
    suspend fun deleteAll()

}