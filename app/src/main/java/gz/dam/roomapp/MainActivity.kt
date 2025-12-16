package gz.dam.roomapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import gz.dam.roomapp.ui.theme.RoomAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Note 1: Habilita el modo edge-to-edge para aprovechar toda la pantalla en dispositivos modernos.
        setContent {
            RoomAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        // Note 2: Instanciamos la base de datos Room. El método databaseBuilder crea o abre una base de datos local.
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        )
            // Note 3: allowMainThreadQueries() permite ejecutar consultas en el hilo principal. Esto es útil solo para pruebas o ejemplos sencillos, pero no se recomienda en producción porque puede bloquear la interfaz de usuario si la operación es lenta.
            .allowMainThreadQueries()
            .build()

        // Note 4: Obtenemos el DAO (Data Access Object) para interactuar con la tabla de usuarios.
        val userDao = db.userDao()
        // Note 5: Recuperamos todos los usuarios almacenados en la base de datos. Esta operación devuelve una lista de objetos User.
        val users: List<User> = userDao.getAll()
        Log.d("ROOMTEST", "Users: $users" ) // Note 6: Mostramos en el log los usuarios recuperados.
        // Note 7: Creamos un nuevo usuario y lo insertamos en la base de datos.
        val newUser = User(1,"John", "Doe")
        userDao.insertAll(newUser)
        // Note 8: Volvemos a recuperar todos los usuarios para comprobar que el nuevo usuario se ha insertado correctamente.
        val updatedUsers: List<User> = userDao.getAll()
        Log.d("ROOMTEST", "Updated Users: $updatedUsers" ) // Note 9: Mostramos en el log la lista actualizada de usuarios.

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    // Note 10: Composable que muestra un texto de saludo. Los composables son funciones que describen la UI en Jetpack Compose.
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    // Note 11: Función de previsualización para ver el composable Greeting en el editor.
    RoomAppTheme {
        Greeting("Android")
    }
}