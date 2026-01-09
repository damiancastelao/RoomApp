# Instrucciones: Crear Pull Request master → release

## Situación Actual

```
Rama origen: master (9d9e23b)
Rama destino: release (2b3292f)
Commits a fusionar: 4
Usuario actual: damiannogueiras
Repositorio: damiancastelao/RoomApp
```

## Problema

El usuario autenticado (damiannogueiras) no tiene permisos de push en el repositorio damiancastelao/RoomApp.

## Solución

### Opción 1: Con credenciales de damiancastelao

Si tienes acceso como damiancastelao, autentica con esas credenciales:

```bash
cd /Users/mini/Damian/castelao/PMDM

# Cambiar credenciales de git
git config --local credential.helper osxkeychain

# Hacer push (te pedirá credenciales de damiancastelao)
git push origin master
```

### Opción 2: Usar GitHub CLI como damiancastelao

```bash
# Autenticarte como damiancastelao
gh auth login --with-token
# Pega el personal access token de damiancastelao

# Verificar autenticación
gh auth status

# Crear el PR
gh pr create \
  --repo damiancastelao/RoomApp \
  --base release \
  --head master \
  --title "Merge master → release: Documentación, scripts e plan de implementación" \
  --body "## Descripción

Merge de los cambios desarrollados en master hacia la rama release. Este pull request consolida documentación, scripts e instrucciones para el proyecto RoomApp.

## Cambios Principales

- **Documentación**: README.md completo en gallego
- **Scripts**: Herramientas bash para gestión del repositorio
- **Instrucciones**: Prompts y directrices para agentes IA
- **Plan de Implementación**: Plan detallado para integración con MongoDB
- **Configuración**: Archivos de instrucciones y templates

## Commits Inclusos

- b556321: Instrucciones para mensajes de commit
- 05ed13f: Scripts y documentación de RoomApp
- e6844fe: Plan de implementación para MongoDB
- 9d9e23b: Nuevos archivos para Pull Requests

## Ficheiros Modificados

\`\`\`
15 files changed, 1349 insertions(+), 199 deletions(-)
\`\`\`"
```

### Opción 3: A través de la interfaz web de GitHub

1. Ve a https://github.com/damiancastelao/RoomApp
2. Debería aparecer un banner sugiriendo crear PR de master
3. Si no aparece, ve a "Pull requests" → "New pull request"
4. Configura:
   - **Base**: release
   - **Compare**: master
5. Completa el formulario con la descripción anterior
6. Clic en "Create pull request"

## Detalles del PR

### Título
```
Merge master → release: Documentación, scripts e plan de implementación
```

### Descripción
```markdown
## Descripción

Merge de los cambios desarrollados en master hacia la rama release. Este pull request consolida documentación, scripts e instrucciones para el proyecto RoomApp.

## Cambios Principales

- **Documentación**: README.md completo en gallego
- **Scripts**: Herramientas bash para gestión del repositorio
- **Instrucciones**: Prompts y directrices para agentes IA
- **Plan de Implementación**: Plan detallado para integración con MongoDB
- **Configuración**: Archivos de instrucciones y templates

## Commits Inclusos

- b556321: Instrucciones para mensajes de commit
- 05ed13f: Scripts y documentación de RoomApp
- e6844fe: Plan de implementación para MongoDB
- 9d9e23b: Nuevos archivos para Pull Requests

## Estadísticas

```
15 files changed, 1349 insertions(+), 199 deletions(-)
```

## Cambios por Tipo

### Documentación
- README.md (209 líneas) - Documentación principal
- .github/Readme.md (17 líneas) - Índice de recursos
- .github/pr-template.md (35 líneas) - Template de PR

### Scripts
- .github/scripts/find-last-modified-files.sh (55 líneas)
- .github/scripts/find-last-modified-files-simple.sh (~30 líneas)
- .github/scripts/README.md (203 líneas)

### Instrucciones y Prompts
- .github/git-commit-instructions.md (actualizado)
- .github/instructions/shell.instrucctions.md (132 líneas)
- .github/prompts/crear-pr.prompt.md (24 líneas)
- .github/prompts/crear-readme.prompt.md (22 líneas)
- .github/prompts/create-implemention-plan.prompt.md (158 líneas)
- .github/add-comment.md (129 líneas)

### Planeamiento
- plan/feature-mongodb-integration-1.md (363 líneas)

### Recursos
- assets/logoAzul400.png (nuevo)
```

## Commits Detallados

### b556321 - Instrucciones para mensajes de commit
Guía para crear mensajes de commit en gallego con estructura y convenciones.

### 05ed13f - Scripts y documentación
Herramientas bash para buscar archivos modificados y documentación completa.

### e6844fe - Plan de implementación
Plan detallado (363 líneas) para integrar conectividad con MongoDB:
- 4 fases de implementación
- 20 tareas detalladas
- Requisitos, riesgos y suposiciones

### 9d9e23b - Nuevos archivos para PR
Consolidación de toda la documentación, instrucciones y configuración del proyecto.
```

## Checklist Antes de Crear el PR

- [ ] Los cambios están listos en la rama master
- [ ] El nombre del usuario en git es damiancastelao
- [ ] El email es damian@danielcastelao.org
- [ ] Tienes permisos de push en damiancastelao/RoomApp
- [ ] Has revisado los cambios (git diff release..master)

## Después de Crear el PR

1. Verifica que el PR se cree correctamente
2. Los cambios deberían mostrar:
   - 4 commits
   - 15 archivos modificados
   - 1,349 adiciones / 199 eliminaciones
3. Espera la revisión
4. Mergea cuando esté aprobado

## Enlace Rápido

Una vez creado, el PR estará en:
```
https://github.com/damiancastelao/RoomApp/pull/[NUMBER]
```

---

**Generado**: Enero 9, 2026
**Repositorio**: damiancastelao/RoomApp
**Rama origen**: master
**Rama destino**: release

