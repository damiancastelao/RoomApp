![RoomApp Logo](./assets/logoAzul400.png)

# RoomApp

Aplicación Android que demostra a persistencia de datos usando a librería **Room Database**. Este proxecto educativo presenta as mellores prácticas para traballar con bases de datos locais en Android usando Kotlin e Jetpack Compose.

## Características

- **Persistencia de datos local** con Room Database
- **Operacións CRUD** completas sobre a base de datos
- **Arquitectura limpa** con DAOs e entidades
- **Interfaz moderna** desenvolvida con Jetpack Compose
- **Material Design 3** para a interface de usuario
- **Kotlin coroutines** para operacións asincrónicas
- **Paging 3** para manexo eficiente de datos

## Requisitos

- Android API 26 (Android 8.0) ou superior
- Android Studio (versión recente recomendada)
- JDK 11 ou superior
- Gradle (incluído no proxecto)

## Tecnoloxías Utilizadas

- **Linguaxe**: Kotlin
- **Base de datos**: Room (ORM para SQLite)
- **UI**: Jetpack Compose
- **Deseño**: Material Design 3
- **Compilación**: Gradle con Kotlin DSL
- **Procesamento de anotacións**: KSP (Kotlin Symbol Processing)
- **Build SDK**: Android API 36
- **Min SDK**: Android API 26

## Estrutura do Proxecto

```
PMDM/
├── app/
│   ├── src/main/
│   │   ├── java/gz/dam/roomapp/
│   │   │   ├── AppDatabase.kt          # Configuración da base de datos
│   │   │   ├── MainActivity.kt         # Actividade principal
│   │   │   ├── User.kt                # Modelo de datos (entidade)
│   │   │   ├── UserDao.kt             # Data Access Object
│   │   │   └── ui/theme/              # Temas e estilos
│   │   ├── res/                        # Recursos (strings, cores, etc.)
│   │   └── AndroidManifest.xml        # Configuración da aplicación
│   └── build.gradle.kts               # Dependencias e configuración Gradle
├── gradle/                            # Configuración de Gradle
├── assets/                            # Recursos do proxecto
└── README.md                          # Este arquivo

```

## Componentes Principais

### User.kt

Entidade que representa un usuario na base de datos:

```kotlin
@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?
)
```

- **uid**: Identificador único (clave primaria)
- **firstName**: Nome do usuario
- **lastName**: Apelido do usuario

### UserDao.kt

Data Access Object que define as operacións dispoñibles:

```kotlin
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>
    
    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>
    
    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User
    
    @Insert
    fun insertAll(vararg users: User)
    
    @Delete
    fun delete(user: User)
}
```

### AppDatabase.kt

Configuración da base de datos e punto de acceso aos DAOs:

```kotlin
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
```

### MainActivity.kt

Actividade principal que integra a base de datos e a interfaz de usuario con Jetpack Compose.

## Instalación e Execución

### 1. Clonar o repositorio

```bash
git clone <url-do-repositorio>
cd PMDM
```

### 2. Abrir o proxecto en Android Studio

- Abre Android Studio
- Selecciona "Open an existing Android Studio project"
- Navega ata a carpeta do proxecto e abre
- Espera a que se complete a sincronización de Gradle

### 3. Executar a aplicación

- Conecta un dispositivo Android ou abre o emulador
- Selecciona a aplicación RoomApp no menú de execución
- Preme `Shift + F10` ou fai clic en "Run"

## Dependencias Principais

O proxecto utiliza as seguintes dependencias principais (ver `app/build.gradle.kts`):

- **androidx.core:core-ktx** - Extensións Kotlin para Android Core
- **androidx.lifecycle:lifecycle-runtime-ktx** - Lifecycle components
- **androidx.activity:activity-compose** - Integración Activity con Compose
- **androidx.compose.ui** - Componentes base de Compose
- **androidx.compose.material3** - Material Design 3 para Compose
- **androidx.room:room-runtime** - Room Database runtime
- **androidx.room:room-ktx** - Extensións Kotlin para Room (coroutines)
- **androidx.room:room-paging** - Integración con Paging 3
- **com.google.devtools.ksp** - Kotlin Symbol Processing

## Exemplo de Uso

### Crear e acceder á base de datos

```kotlin
// Crear instancia da base de datos
val db = Room.databaseBuilder(
    applicationContext,
    AppDatabase::class.java,
    "database-name"
).allowMainThreadQueries()  // Só para demostración
 .build()

// Obter acceso ao DAO
val userDao = db.userDao()

// Recuperar todos os usuarios
val users: List<User> = userDao.getAll()

// Buscar usuario por nome
val usuario = userDao.findByName("John", "Doe")

// Insertar novo usuario
val nuevoUsuario = User(1, "Jane", "Smith")
userDao.insertAll(nuevoUsuario)

// Eliminar usuario
userDao.delete(usuario)
```

## Configuración de Compilación

O proxecto utiliza:

- **Kotlin DSL** para a configuración de Gradle
- **Version Catalog** (`gradle/libs.versions.toml`) para xestión centralizada de versións
- **KSP** para procesamento de anotacións de Room en tempo de compilación
- **Compose** activo como feature de compilación

## Notas Importantes

> **Aviso**: O uso de `allowMainThreadQueries()` no exemplo é só para demostración. En aplicacións de produción, recomendase usar coroutines ou reactive streams para evitar bloquear a thread principal.

> **Edge-to-Edge**: A aplicación utiliza `enableEdgeToEdge()` para aproveitar toda a pantalla en dispositivos modernos, respectando as áreas de sistema.

## Recursos Adicionais

- [Documentación oficial de Room](https://developer.android.com/jetpack/androidx/releases/room)
- [Documentación de Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Guía de Android Jetpack](https://developer.android.com/jetpack)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

## Autor

Profesor Damián Nogueiras

---

**Versión do proxecto**: 1.0  
**Data de última actualización**: Xaneiro 2026
