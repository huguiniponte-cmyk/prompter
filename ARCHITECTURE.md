# Arquitetura Técnica - Prompter

## 📐 Visão Geral

O Prompter segue uma arquitetura **MVVM (Model-View-ViewModel)** com **Repository Pattern**, garantindo separação de responsabilidades e testabilidade.

## 🏛️ Camadas da Aplicação

```
┌─────────────────────────────────────┐
│         UI Layer (Compose)          │
│  - Screens                          │
│  - Navigation                       │
│  - Theme                            │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      ViewModel Layer                │
│  - ThemeViewModel                   │
│  - PromptViewModel                  │
│  - OutputViewModel                  │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      Repository Layer               │
│  - ThemeRepository                  │
│  - PromptRepository                 │
│  - OutputRepository                 │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      Data Layer (Room)              │
│  - DAOs                             │
│  - Entities                         │
│  - Database                         │
└─────────────────────────────────────┘
```

## 📦 Componentes Principais

### 1. Data Layer

#### Entities
- **Theme**: Categoria de organização
- **Prompt**: Template reutilizável com variáveis
- **Output**: Histórico de execução

#### DAOs (Data Access Objects)
- Operações CRUD
- Queries de pesquisa
- Relacionamentos FK

#### Database
- SQLite via Room
- Versão 1
- Sem encriptação (MVP)

### 2. Repository Layer

Abstração sobre os DAOs, expondo:
- `Flow<List<T>>` para observação reativa
- Métodos suspend para operações assíncronas
- Lógica de negócio simples (ex: toggleFavorite)

### 3. ViewModel Layer

Gerencia estado da UI:
- `StateFlow` para dados reativos
- `viewModelScope` para coroutines
- Separação de concerns (um ViewModel por entidade)

### 4. UI Layer (Jetpack Compose)

#### Screens
- **ThemesScreen**: Lista e CRUD de temas
- **PromptsScreen**: Lista, pesquisa e favoritos
- **HistoryScreen**: Histórico de outputs
- **PromptUseScreen**: Uso de prompt com variáveis
- **SettingsScreen**: Configurações
- **PinScreen**: Setup e verificação de PIN

#### Navigation
- Bottom Navigation Bar (4 tabs)
- NavHost com rotas tipadas
- Deep navigation para detalhes

## 🔄 Fluxo de Dados

### Leitura (Unidirecional)
```
Database → DAO → Repository → ViewModel → UI
         (Flow)              (StateFlow)
```

### Escrita
```
UI → ViewModel → Repository → DAO → Database
   (suspend)   (suspend)   (suspend)
```

## 🧩 Módulos Especiais

### VariableParser
Utilitário para:
- Extrair variáveis de prompts
- Validar preenchimento
- Substituir variáveis por valores

**Regex**: `\{([a-zA-Z0-9_]+)\}`

### SecurityManager
Gerencia segurança:
- DataStore para persistência de configurações
- Hash SHA-256 para PIN
- Flags de App Lock e Biometria

## 🎨 Design System

### Material Design 3
- Dark theme permanente
- Color scheme customizado
- Typography escalável

### Cores Predefinidas
8 cores para temas:
- Blue, Green, Orange, Purple
- Red, Yellow, Pink, Teal

## 🔐 Segurança

### App Lock
1. **PIN**: 4 dígitos, hash SHA-256
2. **Biometria**: Android Biometric API
3. **DataStore**: Encrypted Preferences

### Fluxo de Autenticação
```
App Start
    ↓
App Lock Enabled?
    ↓ Yes
PIN/Biometric Screen
    ↓ Verified
Main App
```

## 📊 Decisões Arquiteturais

### Por que Room?
- ✅ Type-safe SQL
- ✅ Compile-time verification
- ✅ Flow support nativo
- ✅ Migrations automáticas

### Por que Compose?
- ✅ Declarative UI
- ✅ Menos boilerplate
- ✅ State hoisting natural
- ✅ Material 3 nativo

### Por que MVVM?
- ✅ Separação UI/Lógica
- ✅ Testabilidade
- ✅ Lifecycle-aware
- ✅ Padrão Android moderno

### Por que Repository Pattern?
- ✅ Abstração de fonte de dados
- ✅ Facilita testes
- ✅ Single source of truth
- ✅ Flexibilidade futura

## 🚀 Performance

### Otimizações
1. **Lazy Loading**: ViewModels e Database
2. **StateFlow**: Apenas updates necessários
3. **Room Indexes**: FK indexadas
4. **Compose**: Recomposição inteligente

### Memory Management
- `WhileSubscribed(5000)`: StateFlow timeout
- `launchSingleTop`: Navegação sem duplicação
- `saveState/restoreState`: Preservação de estado

## 🧪 Testabilidade

### Camadas Testáveis
1. **VariableParser**: Unit tests
2. **Repositories**: Unit tests com DAOs mockados
3. **ViewModels**: Unit tests com Repositories mockados
4. **UI**: Compose UI tests

### Injeção de Dependências
- Manual via Application class
- Factories para ViewModels
- Facilita mocking

## 📈 Escalabilidade

### Possíveis Extensões (Pós-MVP)
1. **Backup/Restore**: Export/Import JSON
2. **Encriptação**: SQLCipher
3. **Sync**: WorkManager + Backend
4. **Templates**: Prompts pré-definidos
5. **Tags**: Sistema de tags adicional
6. **Analytics**: Uso de prompts

### Limitações Atuais
- Sem paginação (assumindo < 1000 items)
- Sem cache de imagens (não aplicável)
- Sem offline queue (não aplicável)

## 🔧 Manutenção

### Versionamento de DB
```kotlin
@Database(version = 1)
// Futuro: Migrations com @Migration(1, 2)
```

### Logging
- Sem logging no MVP
- Futuro: Timber ou similar

### Crash Reporting
- Sem crash reporting no MVP
- Futuro: Firebase Crashlytics (opcional)

## 📝 Convenções de Código

### Naming
- **Entities**: Singular (Theme, Prompt, Output)
- **DAOs**: EntityDao (ThemeDao)
- **Repositories**: EntityRepository
- **ViewModels**: EntityViewModel
- **Screens**: EntityScreen

### Package Structure
```
com.prompter.app/
├── data/           # Camada de dados
├── ui/             # Camada de UI
├── security/       # Segurança
├── util/           # Utilitários
└── *.kt            # Application, MainActivity
```

## 🎯 Princípios SOLID

- **S**: Cada classe tem uma responsabilidade
- **O**: Extensível via interfaces (DAOs)
- **L**: Substituição via Repository Pattern
- **I**: Interfaces segregadas (DAOs específicos)
- **D**: Dependência de abstrações (Repositories)

---

**Última atualização**: 2026-02-06  
**Versão da arquitetura**: 1.0
