---
goal: Engadir conectividade con MongoDB á aplicación RoomApp
version: 1.0
date_created: 2026-01-09
last_updated: 2026-01-09
owner: Damián Nogueiras
status: 'Planned'
tags: ['feature', 'mongodb', 'backend', 'synchronization', 'architecture']
---

# Introdución

![Status: Planned](https://img.shields.io/badge/status-Planned-blue)

Este plan de implementación describe os pasos necesarios para engadir conectividade con MongoDB á aplicación RoomApp. O obxectivo é permitir a sincronización de datos entre a base de datos local (Room) e un servidor remoto de MongoDB, mantendo a arquitectura limpa e o deseño modular.

## 1. Requisitos e Restricións

### Requisitos Funcionais

- **REQ-001**: A aplicación debe ser capaz de conectar a un servidor MongoDB remoto mediante Retrofit e a API REST
- **REQ-002**: Debe haber sincronización bidireccional de datos (local ↔ remoto)
- **REQ-003**: A aplicación debe funcionar offline con datos locais cando non hai conexión de rede
- **REQ-004**: Os datos deben sincronizarse automaticamente cando se restaura a conexión
- **REQ-005**: Debe permitir operacións CRUD completas (Create, Read, Update, Delete) tanto locais como remotas
- **REQ-006**: A aplicación debe validar os datos antes de envialos ao servidor

### Requisitos Non Funcionais

- **PER-001**: A sincronización non debe bloquear a interfaz de usuario (usar coroutines)
- **PER-002**: O tempo de resposta para operacións locais debe ser < 100ms
- **SEC-001**: Os datos sensibles deben transmitirse con HTTPS
- **SEC-002**: As credenciais de acceso ao servidor deben almacenarse de forma segura
- **REL-001**: A aplicación debe reintentarse automaticamente en caso de fallos de rede

### Restricións

- **CON-001**: Compatibilidade con Android API 26+
- **CON-002**: Versión mínima de Kotlin 1.8.0
- **CON-003**: Non debe aumentar o tamaño do APK máis do 15%
- **CON-004**: A sincronización debe ser transparent ao usuario final

### Directrices

- **GUD-001**: Seguir o patrón de arquitectura MVVM (Model-View-ViewModel)
- **GUD-002**: Usar Dependency Injection con Hilt para a xestión de dependencias
- **GUD-003**: Implementar logs estruturados para monitorización
- **GUD-004**: Crear testes unitarios para todas as novas funcións
- **PAT-001**: Usar sealed classes para estados de sincronización
- **PAT-002**: Implementar ViewModel para cada pantalla que acceda a datos

## 2. Pasos de Implementación

### Fase de Implementación 1: Configuración de Dependencias e Infraestructura

**GOAL-001**: Engadir as dependencias necesarias e configurar a infraestructura para comunicación con MongoDB

| Tarefa | Descrición | Completada | Data |
|--------|-----------|-----------|------|
| TASK-001 | Engadir dependencias de Retrofit, OkHttp, Hilt e Serialization ao `build.gradle.kts` | | |
| TASK-002 | Crear a carpeta `network/` en `src/main/java/gz/dam/roomapp/` | | |
| TASK-003 | Configurar o cliente de Retrofit con interceptores para autenticación e logging | | |
| TASK-004 | Crear a entidade `RemoteUser` para serialización JSON con MongoDB | | |
| TASK-005 | Crear o servizo `UserApiService` con operacións CRUD remotas | | |

**Detalles de Tarefas:**

**TASK-001**: Engadir dependencias
- **Arquivo**: `app/build.gradle.kts`
- **Liña de inserción**: Despois das dependencias de Room (liña ~90)
- **Dependencias a engadir**:
  - `com.squareup.retrofit2:retrofit:2.9.0`
  - `com.squareup.retrofit2:converter-kotlinx-serialization:2.9.0`
  - `com.squareup.okhttp3:okhttp:4.11.0`
  - `com.squareup.okhttp3:logging-interceptor:4.11.0`
  - `com.google.dagger:hilt-android:2.48`
  - `com.google.dagger:hilt-compiler:2.48`
  - `org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0`
  - `androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2`
  - `androidx.lifecycle:lifecycle-runtime-compose:2.6.2`

**TASK-002**: Estructura de carpetas
```
src/main/java/gz/dam/roomapp/
├── network/
│   ├── api/
│   │   └── UserApiService.kt
│   ├── interceptor/
│   │   └── AuthInterceptor.kt
│   │   └── LoggingInterceptor.kt
│   ├── model/
│   │   └── RemoteUser.kt
│   └── RetrofitClient.kt
├── di/
│   └── NetworkModule.kt
├── (arquivos existentes)
```

**TASK-003**: Configuración de Retrofit
- Crear ficheiro: `src/main/java/gz/dam/roomapp/network/RetrofitClient.kt`
- Configurar base URL (variable de entorno ou constante)
- Engadir interceptador de autenticación
- Engadir interceptador de logging

**TASK-004**: Entidade RemoteUser
- Crear ficheiro: `src/main/java/gz/dam/roomapp/network/model/RemoteUser.kt`
- Usar `@Serializable` de kotlinx.serialization
- Mapear campos: `_id`, `firstName`, `lastName`

**TASK-005**: UserApiService
- Crear ficheiro: `src/main/java/gz/dam/roomapp/network/api/UserApiService.kt`
- Métodos: GET /users, POST /users, PUT /users/{id}, DELETE /users/{id}

---

### Fase de Implementación 2: Repositorio e Sincronización

**GOAL-002**: Crear una capa de repositorio que xestione a sincronización entre Room e MongoDB

| Tarefa | Descrición | Completada | Data |
|--------|-----------|-----------|------|
| TASK-006 | Crear a clase `UserRepository` que combina UserDao e UserApiService | | |
| TASK-007 | Implementar o patrón de estado de sincronización con sealed class | | |
| TASK-008 | Crear a clase `SyncManager` para xestionar a sincronización en background | | |
| TASK-009 | Implementar Worker de WorkManager para sincronización periódica | | |
| TASK-010 | Engadir xestión de rede e detección de conectividade | | |

**Detalles de Tarefas:**

**TASK-006**: UserRepository
- Crear ficheiro: `src/main/java/gz/dam/roomapp/repository/UserRepository.kt`
- Métodos:
  - `getAllUsersLocal()`: Retorna List<User> de Room
  - `getAllUsersRemote()`: Retorna List<RemoteUser> de MongoDB
  - `syncUsers()`: Sincroniza datos local e remoto
  - `insertUserLocally()`: Inserta en Room
  - `insertUserRemotely()`: Inserta en MongoDB

**TASK-007**: Estados de Sincronización
- Crear ficheiro: `src/main/java/gz/dam/roomapp/model/SyncState.kt`
```kotlin
sealed class SyncState {
    object Idle : SyncState()
    object Syncing : SyncState()
    class Success(val message: String) : SyncState()
    class Error(val exception: Throwable) : SyncState()
    object OfflineMode : SyncState()
}
```

**TASK-008**: SyncManager
- Crear ficheiro: `src/main/java/gz/dam/roomapp/sync/SyncManager.kt`
- Xestionar a lóxica de sincronización
- Usar Kotlin Flow para emitir cambios de estado

**TASK-009**: WorkManager Integration
- Engadir dependencia: `androidx.work:work-runtime-ktx:2.8.1`
- Crear ficheiro: `src/main/java/gz/dam/roomapp/sync/SyncWorker.kt`
- Configurar sincronización cada 15 minutos

**TASK-010**: Detección de Conectividade
- Crear ficheiro: `src/main/java/gz/dam/roomapp/network/ConnectivityManager.kt`
- Usar `ConnectivityManager` de Android
- Emitir cambios de estado mediante Flow

---

### Fase de Implementación 3: Actualización da Interfaz de Usuario

**GOAL-003**: Integrar a sincronización de MongoDB na interfaz de usuario e mostrar o estado

| Tarefa | Descrición | Completada | Data |
|--------|-----------|-----------|------|
| TASK-011 | Crear `UserViewModel` que integra o repositorio | | |
| TASK-012 | Actualizar `MainActivity` para mostrar estado de sincronización | | |
| TASK-013 | Engadir indicador visual de estado de rede | | |
| TASK-014 | Implementar diálogos de confirmación para sincronización | | |
| TASK-015 | Engadir funcionalidade de forzar sincronización manual | | |

**Detalles de Tarefas:**

**TASK-011**: UserViewModel
- Crear ficheiro: `src/main/java/gz/dam/roomapp/ui/viewmodel/UserViewModel.kt`
- Expor datos mediante StateFlow
- Xestionar eventos do usuario (insertar, actualizar, eliminar)

**TASK-012**: MainActivity Actualizado
- Modificar `src/main/java/gz/dam/roomapp/MainActivity.kt`
- Integrar ViewModel
- Mostrar lista de usuarios local e remota
- Mostrar estado de sincronización

**TASK-013**: Indicador de Rede
- Crear composable: `UserInterface.kt` con indicador de status
- Verde: Sincronizado
- Amarelo: Sincronizando
- Vermello: Erro de rede

**TASK-014**: Diálogos
- Crear composables para confirmacións de sincronización
- Permitir que o usuario elxa sincronizar manualmente

**TASK-015**: Botón de Sincronización
- Engadir botón en MainActivity
- Chamar a `SyncManager.forcedSync()`

---

### Fase de Implementación 4: Testes e Validación

**GOAL-004**: Implementar testes para validar a funcionalidade de MongoDB

| Tarefa | Descrición | Completada | Data |
|--------|-----------|-----------|------|
| TASK-016 | Crear testes unitarios para UserRepository | | |
| TASK-017 | Crear testes para SyncManager | | |
| TASK-018 | Crear testes de integración con Retrofit Mock | | |
| TASK-019 | Crear testes de conectividade | | |
| TASK-020 | Validar sincronización end-to-end | | |

**Detalles de Tarefas:**

**TASK-016**: Testes do Repositorio
- Crear ficheiro: `src/test/java/gz/dam/roomapp/repository/UserRepositoryTest.kt`
- Probar: inserción local, inserción remota, sincronización

**TASK-017**: Testes de SyncManager
- Crear ficheiro: `src/test/java/gz/dam/roomapp/sync/SyncManagerTest.kt`
- Probar: sincronización correcta, manexo de erros

**TASK-018**: Testes de Integración
- Usar MockWebServer para simular servidor
- Probar: respostas de HTTP, serialización

**TASK-019**: Testes de Conectividade
- Simular desconexión de rede
- Validar que a aplicación funciona offline

**TASK-020**: Testes End-to-End
- Insertar datos localmente
- Sincronizar con MongoDB
- Verificar que os datos aparecen no servidor

---

## 3. Alternativas

- **ALT-001**: Usar Firebase Realtime Database en lugar de MongoDB
  - *Razón de rexeición*: MongoDB ofrece máis control e flexibilidade para casos educativos
  
- **ALT-002**: Usar Spring Boot como backend en lugar de REST directo
  - *Razón de rexeición*: REST directo é máis sinxelo para este exemplo educativo
  
- **ALT-003**: Usar Room Flow directamente en MainActivity
  - *Razón de rexeición*: ViewModel proporciona mellor manexo de ciclo de vida e estado

---

## 4. Dependencias

- **DEP-001**: Servidor MongoDB configurado e accesible (url base, porta, credenciais)
- **DEP-002**: API REST implementada no servidor (endpoints CRUD)
- **DEP-003**: Autenticación (token JWT ou basic auth)
- **DEP-004**: Base de datos Room xa existente no proxecto
- **DEP-005**: Android API 26+ dispoñible

---

## 5. Ficheiros Afectados

### Novos Ficheiros

- **FILE-001**: `app/src/main/java/gz/dam/roomapp/network/RetrofitClient.kt` - Cliente HTTP
- **FILE-002**: `app/src/main/java/gz/dam/roomapp/network/api/UserApiService.kt` - Servizo API
- **FILE-003**: `app/src/main/java/gz/dam/roomapp/network/model/RemoteUser.kt` - Modelo remoto
- **FILE-004**: `app/src/main/java/gz/dam/roomapp/network/interceptor/AuthInterceptor.kt` - Autenticación
- **FILE-005**: `app/src/main/java/gz/dam/roomapp/repository/UserRepository.kt` - Repositorio
- **FILE-006**: `app/src/main/java/gz/dam/roomapp/model/SyncState.kt` - Estados
- **FILE-007**: `app/src/main/java/gz/dam/roomapp/sync/SyncManager.kt` - Xestor de sincronización
- **FILE-008**: `app/src/main/java/gz/dam/roomapp/sync/SyncWorker.kt` - Worker background
- **FILE-009**: `app/src/main/java/gz/dam/roomapp/network/ConnectivityManager.kt` - Xestor de rede
- **FILE-010**: `app/src/main/java/gz/dam/roomapp/di/NetworkModule.kt` - Módulo Hilt
- **FILE-011**: `app/src/main/java/gz/dam/roomapp/ui/viewmodel/UserViewModel.kt` - ViewModel
- **FILE-012**: `app/src/main/java/gz/dam/roomapp/ui/UserInterface.kt` - Composables UI

### Ficheiros Modificados

- **FILE-013**: `app/build.gradle.kts` - Engadir dependencias
- **FILE-014**: `app/src/main/AndroidManifest.xml` - Engadir permisos de rede
- **FILE-015**: `app/src/main/java/gz/dam/roomapp/MainActivity.kt` - Integración con ViewModel
- **FILE-016**: `app/src/main/java/gz/dam/roomapp/User.kt` - Engadir anotacións serializables

### Ficheiros de Testes

- **FILE-017**: `app/src/test/java/gz/dam/roomapp/repository/UserRepositoryTest.kt`
- **FILE-018**: `app/src/test/java/gz/dam/roomapp/sync/SyncManagerTest.kt`
- **FILE-019**: `app/src/androidTest/java/gz/dam/roomapp/integration/MongoDBIntegrationTest.kt`

---

## 6. Testes

- **TEST-001**: Verificar que Retrofit se conecta correctamente ao servidor MongoDB
- **TEST-002**: Validar que os datos se sincronizen bidireccionalemente
- **TEST-003**: Probar que a aplicación funciona offline
- **TEST-004**: Validar que os datos se sincronizen automaticamente cando se restaura a rede
- **TEST-005**: Probar a inserción, actualización e eliminación de usuarios localmente e remotamente
- **TEST-006**: Validar manexo de erros de rede e reintentos
- **TEST-007**: Probar que os datos sensibles se almacenan de forma segura
- **TEST-008**: Validar que a sincronización non bloquea a UI
- **TEST-009**: Probar a sincronización periódica con WorkManager
- **TEST-010**: Validar a detección de cambios de conectividade

---

## 7. Riscos e Suposicións

### Riscos

- **RISK-001**: Posible perda de datos se hai conflicto entre versión local e remota
  - *Mitigación*: Implementar versionado e timestamp nos datos
  
- **RISK-002**: O servidor MongoDB pode non estar dispoñible
  - *Mitigación*: Implementar reintentos automáticos e modo offline
  
- **RISK-003**: Consumo elevado de datos en sincronización
  - *Mitigación*: Implementar sincronización diferencial (só cambios)
  
- **RISK-004**: Vulnerabilidades de seguridade en transmisión de datos
  - *Mitigación*: Usar HTTPS obrigatorio e validación de certificados
  
- **RISK-005**: Performance degradada con gran volume de datos
  - *Mitigación*: Implementar paginación e índices en MongoDB

### Suposicións

- **ASSUMPTION-001**: O servidor MongoDB está xa configurado e dispoñible
- **ASSUMPTION-002**: Existe un desenvolvedor backend que implementará a API REST
- **ASSUMPTION-003**: Os desenvolvedores teñen coñecemento de Kotlin e Android Jetpack
- **ASSUMPTION-004**: A conexión de rede é relativamente fiable (retardo < 5 segundos)
- **ASSUMPTION-005**: Non hai requisitos de sincronización en tempo real (WebSocket)

---

## 8. Especificacións Relacionadas e Lecturas Adicionais

- [MongoDB Kotlin Driver](https://www.mongodb.com/docs/languages/kotlin/)
- [Retrofit Documentation](https://square.github.io/retrofit/)
- [Android Room Database](https://developer.android.com/training/data-storage/room)
- [Hilt Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)
- [Kotlin Flow](https://kotlinlang.org/docs/flow.html)
- [Android WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
- [Android Coroutines](https://developer.android.com/kotlin/coroutines)
- [REST API Best Practices](https://restfulapi.net/)
- [OWASP Mobile Security](https://owasp.org/www-project-mobile-top-10/)
- [Jetpack Compose State Management](https://developer.android.com/jetpack/compose/state)

---

**Versión do plan**: 1.0  
**Data de creación**: Xaneiro 9, 2026  
**Autor**: Damián Nogueiras

