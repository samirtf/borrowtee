package tf.samir.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val tableNameThing = "thing_table"

@Entity(tableName = tableNameThing)
class ThingModel(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "state") val state: Int
)