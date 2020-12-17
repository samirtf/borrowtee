package tf.samir.infrastructure.dao

import androidx.room.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import tf.samir.data.model.LocalThingModel

import tf.samir.data.model.tableNameThing
import tf.samir.domain.entities.AT_HOME
import tf.samir.domain.entities.ThingState

@Dao
interface ThingModelDao {

    @Query("SELECT * FROM $tableNameThing ORDER BY name ASC")
    fun getAllThings(): Flow<List<LocalThingModel>>

    @ExperimentalCoroutinesApi
    fun getAllThingsDistinctUntilChanged() = getAllThings().distinctUntilChanged()

    @Query("SELECT * FROM $tableNameThing WHERE state = :state ORDER BY name ASC")
    fun getAllThingsBy(@ThingState state: Int): Flow<List<LocalThingModel>>

    @ExperimentalCoroutinesApi
    fun getAllThingsAtHomeDistinctUntilChanged() = getAllThingsBy(AT_HOME).distinctUntilChanged()

    @ExperimentalCoroutinesApi
    fun getAllThingsByBorrowStateDistinctUntilChanged(@ThingState state: Int) = getAllThingsBy(state).distinctUntilChanged()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg thing: LocalThingModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg thing: LocalThingModel)

    @Query("DELETE FROM $tableNameThing")
    suspend fun deleteAll()

}