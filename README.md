![RoomApp Logo](./assets/firma.png)

# RoomApp

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple?style=flat-square&logo=kotlin)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-8.0+-green?style=flat-square&logo=android)](https://www.android.com/)
[![API](https://img.shields.io/badge/API-26%2B-brightgreen?style=flat-square)](https://android-arsenal.com/api?level=26)
[![Room Database](https://img.shields.io/badge/Room-Database-blue?style=flat-square&logo=android)](https://developer.android.com/training/data-storage/room)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack-Compose-blue?style=flat-square)](https://developer.android.com/jetpack/compose)
[![Version](https://img.shields.io/badge/Version-1.0-blue?style=flat-square)](https://github.com/damiancastelao/RoomApp/releases)
[![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Active-brightgreen?style=flat-square)](https://github.com/damiancastelao/RoomApp)
[![JDK](https://img.shields.io/badge/JDK-11%2B-orange?style=flat-square&logo=openjdk)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue?style=flat-square&logo=gradle)](https://gradle.org/)

Aplicación Android educativa que demostra a persistencia de datos usando a librería **Room Database**. Este proxecto presenta as mellores prácticas para traballar con bases de datos locais en Android usando Kotlin e Jetpack Compose, con exemplos prácticos e ben documentados.

### Características Técnicas

[![SQLite](https://img.shields.io/badge/SQLite-Database-003B57?style=flat-square&logo=sqlite)](https://www.sqlite.org/)
[![Coroutines](https://img.shields.io/badge/Coroutines-Async-FF6D00?style=flat-square)](https://kotlinlang.org/docs/coroutines-overview.html)
[![KSP](https://img.shields.io/badge/KSP-Processing-7F52FF?style=flat-square)](https://kotlinlang.org/docs/ksp-overview.html)
[![Material Design 3](https://img.shields.io/badge/Material-Design%203-6200EE?style=flat-square)](https://m3.material.io/)
[![MVVM](https://img.shields.io/badge/Architecture-MVVM-2196F3?style=flat-square)](https://developer.android.com/topic/libraries/architecture/viewmodel)
[![Clean Code](https://img.shields.io/badge/Clean-Code-FF6B6B?style=flat-square)](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

## Comezar Rápidamente

```bash
# Clonar o repositorio
git clone https://github.com/damiancastelao/RoomApp.git
cd RoomApp

# Abrir en Android Studio
# File → Open → Selecciona a carpeta do proxecto

# Executar na aplicación
# Conecta un dispositivo ou abre o emulador
# Run (Shift + F10) ou Run → Run 'app'
```

## Características Principais

- **Persistencia de datos local** con Room Database e SQLite
- **Operacións CRUD completas** sobre a base de datos
- **Arquitectura limpa** con DAOs (Data Access Objects) e entidades
- **Interfaz moderna** desenvolvida con Jetpack Compose
- **Material Design 3** para deseño visual coerente
- **Kotlin coroutines** para operacións asincrónicas
- **Procesamento de anotacións** con KSP en tempo de compilación

## Requisitos

| Requisito | Versión Mínima |
|-----------|----------------|
| Android | 8.0 (API 26) |
| JDK | 11 ou superior |
| Android Studio | Versión recente recomendada |
| Gradle | Incluído no proxecto |

## Tecnoloxías Utilizadas

### Backend e Base de Datos

- **Kotlin** - Linguaxe de programación principal
- **Room Database** - ORM para SQLite con type-safety
- **KSP** - Kotlin Symbol Processing para anotacións

### Interface de Usuario

- **Jetpack Compose** - Toolkit declarativo para UI
- **Material Design 3** - Sistema de deseño moderno
- **Lifecycle** - Xestión de ciclo de vida

### Build e Compilación

- **Gradle** con Kotlin DSL
- **Version Catalog** - Xestión centralizada de versións
- **ProGuard** - Ofuscación para release builds

## Estrutura do Proxecto

```
RoomApp/
├── .github/
│   ├── instructions/          # Instrucións para Copilot e colaboradores
│   ├── prompts/               # Prompts para xeración de código
│   └── scripts/               # Scripts de utilidade
├── app/
│   ├── src/main/
│   │   ├── java/gz/dam/roomapp/
│   │   │   ├── AppDatabase.kt         # Configuración da base de datos
│   │   │   ├── MainActivity.kt        # Actividade principal
│   │   │   ├── User.kt               # Entidade de usuario
│   │   │   ├── UserDao.kt            # Data Access Object
│   │   │   └── ui/theme/             # Temas e estilos Compose
│   │   └── res/                       # Recursos (strings, cores, etc.)
│   └── build.gradle.kts              # Dependencias
├── assets/                            # Recursos do proxecto
├── gradle/                            # Configuración de Gradle
└── plan/                              # Plans de implementación
```

## Componentes Principais

### Entidade: User.kt

Representa un usuario na base de datos:

```kotlin
@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?
)
```

### Data Access Object: UserDao.kt

Define as operacións para acceder aos datos:

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

### Base de Datos: AppDatabase.kt

Configura a base de datos e proporciona acceso aos DAOs:

```kotlin
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
```

### Interface: MainActivity.kt

Integra a base de datos coa interfaz de usuario en Jetpack Compose.

## Instalación e Execución

### Paso 1: Clonar o repositorio

```bash
git clone https://github.com/damiancastelao/RoomApp.git
cd RoomApp
```

### Paso 2: Abrir en Android Studio

1. Abre Android Studio
2. Selecciona `File → Open`
3. Navega á carpeta do proxecto e fai clic en `Open`
4. Espera a que se complete a sincronización de Gradle

### Paso 3: Executar a aplicación

**Opción A: Usando o emulador**
- Abre o Android Virtual Device Manager
- Crea ou inicia un emulador (recomendado API 26+)
- Fai clic no botón `Run` ou preme `Shift + F10`

**Opción B: Usando un dispositivo físico**
- Conecta o dispositivo por USB
- Activa o modo de desenvolvedor
- Executa: `Run → Run 'app'`

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

## Dependencias Principais

```gradle
// Room Database
androidx.room:room-runtime
androidx.room:room-ktx        // Extensións Kotlin
androidx.room:room-paging     // Integración con Paging 3

// Jetpack Compose
androidx.compose.ui:ui
androidx.compose.material3:material3
androidx.activity:activity-compose

// Android Jetpack
androidx.lifecycle:lifecycle-runtime-ktx
androidx.core:core-ktx

// Kotlin Symbol Processing
com.google.devtools.ksp
```

Ver arquivo completo en: [app/build.gradle.kts](app/build.gradle.kts)

## Configuración de Compilación

O proxecto utiliza:

- **Kotlin DSL** - Configuración de Gradle en Kotlin
- **Version Catalog** - Xestión centralizada de versións en `gradle/libs.versions.toml`
- **KSP** - Procesamento de anotacións de Room en tempo de compilación
- **Compose** - Feature de compilación activa

## Notas Importantes

> **Aviso sobre threads principal**
> 
> O uso de `allowMainThreadQueries()` é **só para demostración**. En aplicacións de produción, recomendase usar coroutines ou reactive streams para evitar bloquear a thread principal.

> **Edge-to-Edge Display**
> 
> A aplicación utiliza `enableEdgeToEdge()` para aproveitar toda a pantalla en dispositivos modernos, respectando as áreas de sistema (status bar, navigation bar).

## Próximos Pasos

Consulta o plan de implementación para a integración con MongoDB en: [plan/feature-mongodb-integration-1.md](plan/feature-mongodb-integration-1.md)

Características planificadas:
- Sincronización con servidor remoto
- Modo offline con sincronización automática
- Caché inteligente de datos
- Manexo de conflictos

## Recursos e Referencias

### Documentación Oficial

- [Room Database](https://developer.android.com/training/data-storage/room)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Android Jetpack](https://developer.android.com/jetpack)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Kotlin Symbol Processing](https://kotlinlang.org/docs/ksp-overview.html)

### Recursos Educacionais

- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
- [MVVM Pattern en Android](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

## Información del Proxecto

| Propiedad | Valor |
|-----------|-------|
| **Linguaxe** | [![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple?style=flat-square&logo=kotlin)](https://kotlinlang.org/) |
| **Versión** | [![Version](https://img.shields.io/badge/Version-1.0-blue?style=flat-square)](https://github.com/damiancastelao/RoomApp/releases) |
| **API Mínima** | [![API](https://img.shields.io/badge/API-26%2B-brightgreen?style=flat-square)](https://android-arsenal.com/api?level=26) |
| **API Obxectivo** | [![Target API](https://img.shields.io/badge/Target-36-green?style=flat-square)](https://developer.android.com/about/versions/15) |
| **Estado** | [![Status](https://img.shields.io/badge/Status-Active-brightgreen?style=flat-square)](https://github.com/damiancastelao/RoomApp) |
| **Licencia** | [![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)](LICENSE) |

## Autor

**Profesor Damián Nogueiras**

Proxecto educativo desenvolvido para demostrar as mellores prácticas no desenvolvemento de aplicacións Android con persistencia de datos.

---

**Última actualización**: Xaneiro 2026  
**Licencia**: Véxase o arquivo LICENSE no repositorio
