# Guia de Desenvolvimento - Prompter

## 🚀 Setup Inicial

### Pré-requisitos
- **Android Studio**: Hedgehog (2023.1.1) ou superior
- **JDK**: 17
- **Android SDK**: 34
- **Gradle**: 8.2+
- **Git**: Última versão

### Primeiro Build

1. **Clone o repositório**
```bash
git clone <repo-url>
cd Prompter
```

2. **Abra no Android Studio**
   - File → Open → Selecione a pasta `Prompter`
   - Aguarde sincronização do Gradle

3. **Configure o emulador**
   - Device Manager → Create Device
   - Recomendado: Pixel 6 API 34

4. **Execute**
   - Run → Run 'app'
   - Ou: `./gradlew installDebug`

## 📁 Estrutura de Pastas

```
Prompter/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/prompter/app/
│   │   │   │   ├── data/
│   │   │   │   │   ├── entity/
│   │   │   │   │   ├── dao/
│   │   │   │   │   ├── repository/
│   │   │   │   │   └── PrompterDatabase.kt
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screen/
│   │   │   │   │   ├── viewmodel/
│   │   │   │   │   ├── navigation/
│   │   │   │   │   └── theme/
│   │   │   │   ├── security/
│   │   │   │   ├── util/
│   │   │   │   ├── PrompterApplication.kt
│   │   │   │   └── MainActivity.kt
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── README.md
├── ARCHITECTURE.md
└── .gitignore
```

## 🛠️ Tarefas Comuns

### Adicionar Nova Entidade

1. **Criar Entity**
```kotlin
// data/entity/NewEntity.kt
@Entity(tableName = "new_entities")
data class NewEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "name") val name: String
)
```

2. **Criar DAO**
```kotlin
// data/dao/NewEntityDao.kt
@Dao
interface NewEntityDao {
    @Query("SELECT * FROM new_entities")
    fun getAll(): Flow<List<NewEntity>>
    
    @Insert
    suspend fun insert(entity: NewEntity)
}
```

3. **Adicionar ao Database**
```kotlin
// data/PrompterDatabase.kt
@Database(
    entities = [Theme::class, Prompt::class, Output::class, NewEntity::class],
    version = 2  // Incrementar versão!
)
abstract class PrompterDatabase : RoomDatabase() {
    abstract fun newEntityDao(): NewEntityDao
}
```

4. **Criar Repository**
```kotlin
// data/repository/NewEntityRepository.kt
class NewEntityRepository(private val dao: NewEntityDao) {
    val all: Flow<List<NewEntity>> = dao.getAll()
    suspend fun insert(entity: NewEntity) = dao.insert(entity)
}
```

5. **Criar ViewModel**
```kotlin
// ui/viewmodel/NewEntityViewModel.kt
class NewEntityViewModel(private val repository: NewEntityRepository) : ViewModel() {
    val all: StateFlow<List<NewEntity>> = repository.all
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
```

### Adicionar Nova Screen

1. **Criar Composable**
```kotlin
// ui/screen/NewScreen.kt
@Composable
fun NewScreen(
    data: List<NewEntity>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // UI implementation
}
```

2. **Adicionar Rota**
```kotlin
// ui/navigation/Screen.kt
sealed class Screen(val route: String) {
    // ...
    object NewScreen : Screen("new_screen")
}
```

3. **Adicionar ao NavHost**
```kotlin
// MainActivity.kt
composable(Screen.NewScreen.route) {
    NewScreen(/* ... */)
}
```

### Modificar Tema

```kotlin
// ui/theme/Color.kt
val NewColor = Color(0xFF123456)

// Adicionar a ThemeColors se for cor de tema
val ThemeColors = listOf(
    // ...
    NewColor
)
```

### Adicionar Permissão

1. **AndroidManifest.xml**
```xml
<uses-permission android:name="android.permission.NEW_PERMISSION" />
```

2. **Request em runtime** (se necessário)
```kotlin
// MainActivity.kt ou Screen relevante
val launcher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
) { isGranted ->
    // Handle result
}
```

## 🧪 Testes

### Unit Tests

```kotlin
// app/src/test/java/com/prompter/app/
class VariableParserTest {
    @Test
    fun extractVariables_withValidInput_returnsVariables() {
        val input = "Hello {name}, you are {age} years old"
        val result = VariableParser.extractVariables(input)
        assertEquals(listOf("name", "age"), result)
    }
}
```

### UI Tests

```kotlin
// app/src/androidTest/java/com/prompter/app/
@Test
fun themesScreen_showsEmptyState() {
    composeTestRule.setContent {
        ThemesScreen(
            themes = emptyList(),
            onThemeClick = {},
            onAddTheme = {},
            onDeleteTheme = {}
        )
    }
    composeTestRule.onNodeWithText("Nenhum tema criado").assertIsDisplayed()
}
```

## 🐛 Debugging

### Database Inspector
1. Run app em debug
2. View → Tool Windows → App Inspection
3. Database Inspector → prompter_database

### Compose Layout Inspector
1. Run app
2. Tools → Layout Inspector
3. Selecione processo da app

### Logcat
```kotlin
import android.util.Log

Log.d("TAG", "Debug message")
Log.e("TAG", "Error message", exception)
```

## 📦 Build Variants

### Debug
```bash
./gradlew assembleDebug
```

### Release
```bash
./gradlew assembleRelease
```

### Instalar
```bash
./gradlew installDebug
./gradlew installRelease
```

## 🔧 Gradle Tasks Úteis

```bash
# Limpar build
./gradlew clean

# Build completo
./gradlew build

# Testes
./gradlew test
./gradlew connectedAndroidTest

# Lint
./gradlew lint

# Dependências
./gradlew dependencies
```

## 📝 Convenções de Código

### Kotlin Style Guide
- **Indentação**: 4 espaços
- **Line length**: 120 caracteres
- **Naming**: camelCase para variáveis, PascalCase para classes

### Compose Best Practices
```kotlin
// ✅ Bom: State hoisting
@Composable
fun MyScreen(
    data: List<Item>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) { }

// ❌ Evitar: Estado interno sem necessidade
@Composable
fun MyScreen() {
    val data = remember { mutableStateListOf<Item>() }
}
```

### ViewModel Pattern
```kotlin
// ✅ Bom: StateFlow para UI state
class MyViewModel : ViewModel() {
    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state.asStateFlow()
}

// ❌ Evitar: LiveData (preferir StateFlow)
```

### Repository Pattern
```kotlin
// ✅ Bom: Expor Flow
class MyRepository(private val dao: MyDao) {
    val items: Flow<List<Item>> = dao.getAll()
}

// ❌ Evitar: Expor DAO diretamente
```

## 🚨 Troubleshooting

### Gradle Sync Failed
```bash
# Limpar cache
./gradlew clean
# Invalidar cache do Android Studio
File → Invalidate Caches → Invalidate and Restart
```

### Room Schema Error
```bash
# Deletar app do emulador
adb uninstall com.prompter.app
# Reinstalar
./gradlew installDebug
```

### Compose Preview Not Working
```bash
# Build → Clean Project
# Build → Rebuild Project
# Restart Android Studio
```

## 📚 Recursos

### Documentação Oficial
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- [Material Design 3](https://m3.material.io/)

### Tutoriais Recomendados
- [Android Basics with Compose](https://developer.android.com/courses/android-basics-compose/course)
- [Modern Android App Architecture](https://developer.android.com/topic/architecture)

## 🤝 Contribuindo

### Workflow
1. Fork o repositório
2. Crie branch: `git checkout -b feature/nova-funcionalidade`
3. Commit: `git commit -m 'Adiciona nova funcionalidade'`
4. Push: `git push origin feature/nova-funcionalidade`
5. Abra Pull Request

### Commit Messages
```
feat: adiciona pesquisa por tags
fix: corrige crash ao deletar tema
docs: atualiza README
refactor: simplifica VariableParser
test: adiciona testes para OutputViewModel
```

## 📊 Checklist de PR

- [ ] Código compila sem erros
- [ ] Testes passam
- [ ] Lint sem warnings críticos
- [ ] Documentação atualizada
- [ ] Screenshots (se UI)
- [ ] Testado em emulador/dispositivo

---

**Dúvidas?** Abra uma issue no repositório!
