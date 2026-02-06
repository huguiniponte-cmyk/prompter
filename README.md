# Prompter - Gestão de Prompts Offline

![Android](https://img.shields.io/badge/Platform-Android-green.svg)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-blue.svg)
![Offline First](https://img.shields.io/badge/Mode-Offline%20First-orange.svg)

## 📱 Sobre

**Prompter** é uma aplicação Android nativa, 100% offline, focada em gestão simples de prompts com histórico reutilizável.

### Objetivo

Criar, organizar e reutilizar prompts de forma eficiente, mantendo um histórico cronológico de outputs para referência futura.

## ✨ Funcionalidades Core

- ✅ **CRUD completo** de Temas, Prompts e Outputs
- ✅ **Sistema de variáveis** dinâmico com `{nome_variavel}`
- ✅ **Pesquisa global** por título, corpo e outputs
- ✅ **Favoritos** para acesso rápido
- ✅ **Histórico cronológico** de execuções
- ✅ **App Lock** com PIN ou biometria
- ✅ **Dark Mode** permanente
- ✅ **100% Offline** - sem internet, sem APIs, sem cloud

## 🏗️ Arquitetura

### Stack Tecnológico

- **Linguagem**: Kotlin
- **UI**: Jetpack Compose (Material Design 3)
- **Persistência**: SQLite via Room
- **Navegação**: Navigation Compose
- **Segurança**: DataStore + Biometric API
- **Padrão**: MVVM + Repository Pattern

### Modelo de Dados

```kotlin
Theme
├── id: UUID
├── name: String
├── color: Int
└── icon: String

Prompt
├── id: UUID
├── theme_id: UUID (FK)
├── title: String
├── body: String          // Template com {variáveis}
├── notes: String?
├── is_favorite: Boolean
└── created_at: Timestamp

Output
├── id: UUID
├── prompt_id: UUID (FK)
├── input_filled: String  // JSON com valores das variáveis
├── output_text: String   // Resultado colado
├── rating: Int (1-5)
└── created_at: Timestamp
```

## 🎯 Fluxo de Uso

1. **Criar Tema** → Organizar prompts por categoria
2. **Criar Prompt** → Definir template com variáveis `{nome}`
3. **Usar Prompt** → Preencher variáveis → Copiar texto final
4. **Guardar Output** → Colar resultado obtido externamente
5. **Consultar Histórico** → Reutilizar outputs antigos

## 🔐 Segurança

- **App Lock**: PIN de 4 dígitos (SHA-256) ou biometria
- **Base de dados**: SQLite local não encriptada
- **Sem rede**: Zero comunicação externa

## 📂 Estrutura do Projeto

```
app/src/main/java/com/prompter/app/
├── data/
│   ├── entity/          # Theme, Prompt, Output
│   ├── dao/             # DAOs Room
│   ├── repository/      # Repositories
│   └── PrompterDatabase.kt
├── ui/
│   ├── screen/          # Telas Compose
│   ├── viewmodel/       # ViewModels
│   ├── navigation/      # Navegação
│   └── theme/           # Tema Material 3
├── security/
│   └── SecurityManager.kt
├── util/
│   └── VariableParser.kt
├── PrompterApplication.kt
└── MainActivity.kt
```

## 🚀 Como Compilar

### Requisitos

- Android Studio Hedgehog (2023.1.1) ou superior
- JDK 17
- Android SDK 34
- Gradle 8.2+

### Passos

1. Clone o repositório:
```bash
git clone <repo-url>
cd Prompter
```

2. Abra no Android Studio

3. Sincronize o Gradle:
```bash
./gradlew build
```

4. Execute no emulador ou dispositivo:
```bash
./gradlew installDebug
```

## 📱 Navegação

A app possui **4 tabs principais**:

1. **Temas** 📁 - Gestão de categorias
2. **Prompts** 📝 - Criação e pesquisa de prompts
3. **Histórico** 🕒 - Consulta de outputs guardados
4. **Definições** ⚙️ - Configurações e segurança

## 🎨 Design

- **Material Design 3** com dark theme
- **Cores vibrantes** para temas personalizados
- **Ícones emoji** para identificação visual
- **Tipografia otimizada** para leitura de texto longo

## 🔧 Sistema de Variáveis

### Sintaxe

```
{nome_variavel}
```

### Exemplo

**Prompt**:
```
Escreve um email para {destinatario} sobre {assunto}.
Tom: {tom}
```

**Formulário gerado automaticamente**:
- destinatario: [input]
- assunto: [input]
- tom: [input]

**Resultado**:
```
Escreve um email para João Silva sobre Reunião de Projeto.
Tom: Formal
```

## 📊 Princípios de Design

1. **Simplicidade > Completude**
2. **3 entidades apenas** (Theme, Prompt, Output)
3. **Timestamp como versão** (sem versionamento explícito)
4. **Máximo 3 toques** para qualquer ação
5. **Zero dependências externas**

## 🚫 Exclusões Explícitas (MVP)

- ❌ Firebase
- ❌ APIs externas
- ❌ Sincronização cloud
- ❌ Overlay flutuante
- ❌ SQLCipher
- ❌ Encriptação de backup

## 📝 Licença

Este projeto é de código aberto para fins educacionais.

## 👨‍💻 Autor

Desenvolvido como projeto de demonstração de arquitetura Android moderna.

---

**Versão**: 1.0.0  
**Min SDK**: 26 (Android 8.0)  
**Target SDK**: 34 (Android 14)
