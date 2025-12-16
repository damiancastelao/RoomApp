package gz.dam.roomapp

import androidx.room.Database
import androidx.room.RoomDatabase

// Note 1: La anotación @Database define la configuración de la base de datos Room, incluyendo las entidades y la versión.
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    // Note 2: Método abstracto que expone el DAO para la entidad User. Room genera la implementación automáticamente.
    abstract fun userDao(): UserDao
}