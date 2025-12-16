package gz.dam.roomapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Note 1: La anotación @Entity indica que esta clase representa una tabla en la base de datos Room.
@Entity
data class User(
    // Note 2: @PrimaryKey define la clave primaria de la tabla. Cada usuario tendrá un identificador único.
    @PrimaryKey val uid: Int,
    // Note 3: @ColumnInfo permite personalizar el nombre de la columna en la base de datos. Aquí se usa para los nombres y apellidos.
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?
)