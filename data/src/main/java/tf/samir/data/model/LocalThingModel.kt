package tf.samir.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

const val tableNameThing = "thing_table"

@Entity(
    tableName = tableNameThing,
    indices = [Index(
        value = ["name"],
        unique = true
    )]
)
class LocalThingModel(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "state") val state: Int
)