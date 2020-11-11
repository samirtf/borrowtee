package tf.samir.borrowtee.modules.main.infrastructure.dao

import androidx.room.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import tf.samir.borrowtee.modules.main.infrastructure.model.ThingModel
import tf.samir.borrowtee.modules.main.infrastructure.model.tableNameThing

@Dao
interface ThingModelDao {

    @Query("SELECT * FROM $tableNameThing ORDER BY name ASC")
    fun getAllThings(): Flow<List<ThingModel>>

    @ExperimentalCoroutinesApi
    fun getAllThingsDistinctUntilChanged() = getAllThings().distinctUntilChanged()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(thing: ThingModel)

    @Query("DELETE FROM $tableNameThing")
    suspend fun deleteAll()

}