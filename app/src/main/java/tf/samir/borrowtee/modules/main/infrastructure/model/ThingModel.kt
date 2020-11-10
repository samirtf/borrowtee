package tf.samir.borrowtee.modules.main.infrastructure.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "thing_table")
class ThingModel(
    @PrimaryKey(autoGenerate = true) val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "state") val state: Int
)