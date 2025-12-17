# RoomApp

Este proyecto es una aplicación Android que utiliza Room como solución de persistencia de datos. Room es una biblioteca de abstracción sobre SQLite que permite un acceso más sencillo y seguro a la base de datos local.

## Descripción general del uso de Room

Room proporciona una capa de abstracción sobre SQLite para permitir un acceso robusto a la base de datos mientras aprovecha el poder de SQLite. Se compone principalmente de tres componentes:

- **Entidad (Entity):** Representa una tabla dentro de la base de datos.
- **DAO (Data Access Object):** Define los métodos de acceso a la base de datos.
- **Base de datos (Database):** Contiene la base de datos y sirve como el punto de acceso principal a los datos persistidos.

## Estructura de archivos y explicación

- `build.gradle.kts` y `app/build.gradle.kts`: Archivos de configuración de Gradle donde se incluyen las dependencias necesarias para Room y otras configuraciones del proyecto.
- `settings.gradle.kts`: Configuración de los módulos del proyecto.
- `app/src/main/java/`: Carpeta principal del código fuente. Aquí se encuentran las siguientes clases relevantes para Room:
    - **Entidades:** Clases anotadas con `@Entity` que representan las tablas de la base de datos.
    - **DAOs:** Interfaces anotadas con `@Dao` que contienen los métodos para acceder y modificar los datos.
    - **Clase de base de datos:** Clase abstracta anotada con `@Database` que extiende de `RoomDatabase` y proporciona los DAOs.
- `app/src/main/AndroidManifest.xml`: Archivo de manifiesto de la aplicación.
- `app/src/main/res/`: Recursos de la aplicación (layouts, strings, etc.).
- `proguard-rules.pro`: Reglas de ofuscación para ProGuard.
- `gradle/` y `gradlew*`: Archivos y scripts para la gestión y ejecución de Gradle.

## Ejemplo de uso de Room

1. **Definición de una entidad:**
   ```kotlin
   @Entity
data class Usuario(
    @PrimaryKey val id: Int,
    val nombre: String,
    val apellido: String
)
   ```
2. **Definición de un DAO:**
   ```kotlin
   @Dao
   interface UsuarioDao {
       @Query("SELECT * FROM Usuario")
       fun obtenerTodos(): List<Usuario>

       @Insert
       fun insertar(vararg usuarios: Usuario)
   }
   ```
3. **Definición de la base de datos:**
   ```kotlin
   @Database(entities = [Usuario::class], version = 1)
   abstract class AppDatabase : RoomDatabase() {
       abstract fun usuarioDao(): UsuarioDao
   }
   ```
4. **Acceso a la base de datos:**
   ```kotlin
   val db = Room.databaseBuilder(
       applicationContext,
       AppDatabase::class.java, "nombre-db"
   ).build()
   val usuarioDao = db.usuarioDao()
   ```

## Notas importantes

- Todas las operaciones de acceso a la base de datos deben realizarse fuera del hilo principal para evitar bloqueos de la interfaz de usuario.
- Room genera automáticamente el código necesario para las operaciones CRUD a partir de las anotaciones.
- Si es estrictamente necesario realizar operaciones en el hilo principal (por ejemplo, durante pruebas o desarrollo), se puede utilizar el método `allowMainThreadQueries()` al construir la base de datos. Sin embargo, esto no es recomendable para aplicaciones en producción, ya que puede afectar negativamente el rendimiento y la experiencia de usuario.

Para más información, consulte la [documentación oficial de Room](https://developer.android.com/training/data-storage/room).

## Configurar GitHub Copilot (MCP) de forma segura

Para usar GitHub Copilot en JetBrains, el plugin puede requerir un archivo local `mcp.json` en `~/.config/github-copilot/intellij/mcp.json` con un token. Este repositorio incluye scripts para crear ese archivo de forma segura sin guardar tokens en el repositorio.

Archivos añadidos:
- `scripts/setup_copilot_mcp.sh` — crea `mcp.json` a partir de la variable de entorno `GITHUB_COPILOT_TOKEN`.
- `scripts/setup_copilot_mcp_from_keyring.sh` — crea `mcp.json` leyendo el token desde el keyring (libsecret/secret-tool).
- `.github/mcp.json.template` — plantilla sin token para documentar el formato.

Recomendación rápida (temporal):
1. En tu terminal, exporta tu token (temporalmente):

```bash
export GITHUB_COPILOT_TOKEN="PASTE_YOUR_TOKEN_HERE"
bash scripts/setup_copilot_mcp.sh
```

Recomendación segura (recomendada):
1. Guarda el token en el keyring (solo una vez):

```bash
secret-tool store --label='GitHub Copilot Token' copilot token
# pega el token cuando se te pida
```
2. Crea `mcp.json` desde el keyring:

```bash
bash scripts/setup_copilot_mcp_from_keyring.sh
```

Buenas prácticas:
- Nunca subas tu token al repositorio. No incluyas `~/.config/github-copilot/intellij/mcp.json` en commits.
- Si un token quedó expuesto, rótalo en GitHub y elimina cualquier copia del archivo.
- Usa permisos restrictivos para `mcp.json`: el script aplica `chmod 600`.

Si trabajas en un equipo y necesitas estandarizar el aprovisionamiento, puedo ayudar a crear un script de inicialización adicional o instrucciones para el equipo.
