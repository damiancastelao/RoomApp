package gz.dam.roomapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

// Note 1: La anotación @Dao indica que esta interfaz define métodos de acceso a la base de datos (Data Access Object).
@Dao
interface UserDao {
    // Note 2: Consulta que recupera todos los usuarios de la tabla 'user'. Devuelve una lista de objetos User.
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    // Note 3: Consulta que recupera los usuarios cuyos uid están en el array proporcionado.
    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    // Note 4: Consulta que busca un usuario por nombre y apellido. Utiliza LIKE para coincidencias parciales y limita el resultado a uno.
    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Query("SELECT * FROM user WHERE date LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByDate(first: String, last: String): User

    // Note 5: Inserta uno o varios usuarios en la base de datos.
    @Insert
    fun insertAll(vararg users: User)

    // Note 6: Elimina un usuario de la base de datos.
    @Delete
    fun delete(user: User)
}