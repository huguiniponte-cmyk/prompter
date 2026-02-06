# Changelog

Todas as mudanças notáveis neste projeto serão documentadas neste ficheiro.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt/1.0.0/),
e este projeto adere ao [Semantic Versioning](https://semver.org/lang/pt-BR/).

## [1.0.0] - 2026-02-06

### ✨ Adicionado

#### Core Features
- **CRUD completo** para Temas, Prompts e Outputs
- **Sistema de variáveis** dinâmico com sintaxe `{nome_variavel}`
- **Parser de variáveis** com regex para extração e substituição automática
- **Pesquisa global** em prompts (título e corpo) e outputs (texto)
- **Sistema de favoritos** para acesso rápido a prompts
- **Histórico cronológico** de outputs com timestamps
- **Rating system** (1-5 estrelas) para outputs

#### UI/UX
- **Bottom Navigation** com 4 tabs principais:
  - Temas 📁
  - Prompts 📝
  - Histórico 🕒
  - Definições ⚙️
- **Dark Mode** permanente com Material Design 3
- **Paleta de cores** customizada (8 cores predefinidas)
- **Ícones emoji** para identificação visual de temas
- **Empty states** informativos em todas as telas
- **Formulário dinâmico** de variáveis gerado automaticamente

#### Segurança
- **App Lock** com PIN de 4 dígitos
- **Hash SHA-256** para armazenamento seguro de PIN
- **Suporte a biometria** (preparado para implementação futura)
- **DataStore** para persistência de configurações de segurança

#### Arquitetura
- **MVVM** com Repository Pattern
- **Room Database** com SQLite
- **Jetpack Compose** para UI declarativa
- **Navigation Compose** para navegação type-safe
- **StateFlow** para estado reativo
- **Coroutines** para operações assíncronas
- **100% Kotlin**

#### Funcionalidades Técnicas
- **Foreign Keys** com CASCADE delete
- **Indexes** em colunas FK para performance
- **Flow** para observação reativa de dados
- **Lazy initialization** de ViewModels e Database
- **State hoisting** em componentes Compose
- **Singleton pattern** para Database

#### Documentação
- README completo com instruções de uso
- ARCHITECTURE.md com detalhes técnicos
- DEVELOPMENT.md com guia de desenvolvimento
- Testes unitários para VariableParser
- Comentários inline em código crítico

### 🎨 Design
- **Material Design 3** color scheme
- **Typography** escalável e acessível
- **Cards** com elevação e bordas arredondadas
- **Chips** para visualização de variáveis
- **Dialogs** para criação e confirmação
- **Search bar** integrada em Prompts e Histórico

### 🔧 Técnico
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)
- **Compile SDK**: 34
- **Kotlin**: 1.9.20
- **Compose**: BOM 2023.10.01
- **Room**: 2.6.1
- **Navigation**: 2.7.6

### 📦 Dependências
- androidx.core:core-ktx:1.12.0
- androidx.lifecycle:lifecycle-runtime-ktx:2.7.0
- androidx.activity:activity-compose:1.8.2
- androidx.compose.material3:material3
- androidx.navigation:navigation-compose:2.7.6
- androidx.room:room-runtime:2.6.1
- androidx.biometric:biometric:1.2.0-alpha05
- androidx.datastore:datastore-preferences:1.0.0
- kotlinx-coroutines-android:1.7.3
- kotlinx-serialization-json:1.6.2

### 🚫 Exclusões Explícitas (MVP)
- Firebase
- APIs externas
- Sincronização cloud
- Overlay flutuante
- SQLCipher
- Encriptação de backup
- Analytics
- Crash reporting

### 📝 Notas
- Base de dados **não encriptada** (decisão consciente para MVP)
- **Offline-first** por design - zero dependências de rede
- **Simplicidade** sobre completude - apenas 3 entidades
- **Timestamp como versão** - sem versionamento explícito

### 🐛 Problemas Conhecidos
Nenhum problema conhecido nesta versão inicial.

### 🔮 Próximas Versões (Roadmap)

#### [1.1.0] - Planejado
- [ ] Backup/Restore em JSON
- [ ] Export de prompts individuais
- [ ] Import de templates
- [ ] Biometria funcional (implementação completa)
- [ ] Temas customizados (cores personalizadas)

#### [1.2.0] - Planejado
- [ ] Sistema de tags
- [ ] Filtros avançados
- [ ] Ordenação customizada
- [ ] Estatísticas de uso
- [ ] Gráficos de rating

#### [2.0.0] - Futuro
- [ ] Encriptação com SQLCipher
- [ ] Sincronização opcional (cloud)
- [ ] Compartilhamento de prompts
- [ ] Templates da comunidade
- [ ] Modo colaborativo

---

## Formato de Versão

- **MAJOR**: Mudanças incompatíveis na API
- **MINOR**: Novas funcionalidades compatíveis
- **PATCH**: Correções de bugs compatíveis

## Tipos de Mudanças

- **Adicionado**: Novas funcionalidades
- **Modificado**: Mudanças em funcionalidades existentes
- **Depreciado**: Funcionalidades que serão removidas
- **Removido**: Funcionalidades removidas
- **Corrigido**: Correções de bugs
- **Segurança**: Vulnerabilidades corrigidas

---

**Última atualização**: 2026-02-06
