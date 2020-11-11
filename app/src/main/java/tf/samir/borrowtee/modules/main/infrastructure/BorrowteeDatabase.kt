package tf.samir.borrowtee.modules.main.infrastructure

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tf.samir.borrowtee.modules.main.infrastructure.dao.ThingModelDao
import tf.samir.borrowtee.modules.main.infrastructure.model.ThingModel

@Database(entities = arrayOf(ThingModel::class), version = 1, exportSchema = false)
abstract class BorrowteeDatabase : RoomDatabase() {

    abstract fun thingModelDao(): ThingModelDao

    companion object {
        @Volatile
        private var INSTANCE: BorrowteeDatabase? = null

        fun getDatabase(context: Context): BorrowteeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BorrowteeDatabase::class.java,
                    "borrowtee_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}