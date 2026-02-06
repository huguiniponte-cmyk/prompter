# ⚡ Quick Start Guide - Prompter

**Tempo estimado**: 5 minutos

---

## 🚀 Instalação

### Opção 1: Android Studio (Desenvolvimento)

```bash
# 1. Clone o repositório
git clone <repo-url>
cd Prompter

# 2. Abra no Android Studio
# File → Open → Selecione a pasta Prompter

# 3. Aguarde sincronização do Gradle
# (automático)

# 4. Execute
# Run → Run 'app'
# ou pressione Shift+F10
```

### Opção 2: APK Direto (Uso)

```bash
# 1. Build do APK
./gradlew assembleDebug

# 2. Instale no dispositivo
adb install app/build/outputs/apk/debug/app-debug.apk

# 3. Abra a app
# Procure por "Prompter" no launcher
```

---

## 📱 Primeiro Uso (3 minutos)

### Passo 1: Criar Tema (30 segundos)

1. Abra a app
2. Tab **Temas** 📁
3. Toque no botão **+**
4. Preencha:
   - Nome: `Trabalho`
   - Cor: Azul
   - Ícone: 💼
5. Toque **Criar**

✅ **Resultado**: Tema criado!

---

### Passo 2: Criar Prompt (1 minuto)

1. Tab **Prompts** 📝
2. Toque no botão **+**
3. Preencha:
   - **Título**: `Email Profissional`
   - **Tema**: Trabalho
   - **Corpo**:
     ```
     Escreve um email para {destinatario} sobre {assunto}.
     Tom: {tom}
     ```
   - **Notas**: `Use linguagem formal`
4. Toque **Criar**

✅ **Resultado**: Prompt com 3 variáveis criado!

---

### Passo 3: Usar Prompt (1 minuto)

1. Toque no prompt criado
2. Preencha as variáveis:
   - destinatario: `João Silva`
   - assunto: `Reunião`
   - tom: `Formal`
3. Veja o preview atualizar automaticamente
4. Toque **Copiar Prompt**
5. Cole num LLM (ChatGPT, Claude, etc.)

✅ **Resultado**: Prompt copiado e pronto para usar!

---

### Passo 4: Guardar Output (30 segundos)

1. Após obter resposta do LLM, volte à app
2. Cole o resultado no campo **Guardar Resultado**
3. Dê um rating (1-5 estrelas)
4. Toque **Guardar Output**

✅ **Resultado**: Output guardado no histórico!

---

### Passo 5: Consultar Histórico (30 segundos)

1. Tab **Histórico** 🕒
2. Veja o output guardado
3. Toque para ver detalhes
4. Reutilize quando necessário

✅ **Resultado**: Histórico acessível!

---

## 🎯 Fluxo Completo (Resumo)

```
1. Criar Tema
   ↓
2. Criar Prompt com {variáveis}
   ↓
3. Usar Prompt → Preencher → Copiar
   ↓
4. Usar em LLM externo
   ↓
5. Guardar Output na app
   ↓
6. Consultar Histórico quando necessário
```

---

## 💡 Dicas Rápidas

### Variáveis
```
✅ Use: {nome}, {idade}, {cidade}
❌ Evite: {var1}, {x}, {temp}
```

### Organização
```
📁 Trabalho
   ├── Emails
   ├── Relatórios
   └── Apresentações

📁 Pessoal
   ├── Ideias
   └── Planejamento
```

### Favoritos
- Marque prompts usados frequentemente
- Acesso rápido via estrela ⭐

### Pesquisa
- Pesquise por título ou conteúdo
- Use palavras-chave específicas

---

## 🔐 App Lock (Opcional)

### Configurar PIN

1. Tab **Definições** ⚙️
2. Ative **App Lock**
3. Crie PIN de 4 dígitos
4. Confirme PIN

✅ **Resultado**: App protegida!

### Desativar

1. Tab **Definições** ⚙️
2. Desative **App Lock**

---

## 📚 Exemplos Prontos

### Email Profissional
```
Título: Email Profissional
Corpo:
Escreve um email para {destinatario} sobre {assunto}.
Tom: {tom}
Comprimento: {comprimento}
```

### Post Instagram
```
Título: Post Instagram
Corpo:
Cria um post sobre {produto} para {publico_alvo}.
Tom: {tom}
Hashtags: {numero_hashtags}
```

### Código Python
```
Título: Função Python
Corpo:
Escreve uma função Python que {funcionalidade}.
Parâmetros: {parametros}
Retorna: {tipo_retorno}
```

---

## 🆘 Troubleshooting

### App não abre
```bash
# Limpar e reinstalar
adb uninstall com.prompter.app
./gradlew clean
./gradlew installDebug
```

### Gradle sync failed
```bash
# Limpar cache
./gradlew clean
# No Android Studio:
File → Invalidate Caches → Invalidate and Restart
```

### Prompt não copia
- Verifique permissões de clipboard
- Reinicie a app

---

## 📖 Próximos Passos

### Aprender Mais
1. Leia **README.md** - Visão geral completa
2. Veja **EXAMPLES.md** - Casos de uso práticos
3. Consulte **ARCHITECTURE.md** - Detalhes técnicos

### Explorar Features
- [ ] Criar múltiplos temas
- [ ] Testar pesquisa
- [ ] Usar favoritos
- [ ] Experimentar ratings
- [ ] Explorar histórico

### Personalizar
- [ ] Criar prompts personalizados
- [ ] Organizar por temas
- [ ] Desenvolver workflow próprio

---

## ⚡ Atalhos de Teclado (Android Studio)

```
Shift + F10     - Run app
Ctrl + F9       - Build
Ctrl + Shift+A  - Find Action
```

---

## 🎓 Conceitos-Chave

### Variáveis
Placeholders no formato `{nome}` que são substituídos por valores reais.

### Temas
Categorias para organizar prompts relacionados.

### Outputs
Resultados guardados de execuções de prompts.

### Rating
Avaliação de 1-5 estrelas para qualidade do output.

---

## 📊 Métricas de Sucesso

Após 1 semana de uso, você deve ter:
- ✅ 3-5 temas criados
- ✅ 10-20 prompts
- ✅ 20-50 outputs guardados
- ✅ 3-5 prompts favoritos

---

## 🚀 Você está pronto!

A app está configurada e você já sabe:
- ✅ Criar temas
- ✅ Criar prompts com variáveis
- ✅ Usar prompts
- ✅ Guardar outputs
- ✅ Consultar histórico

**Comece a usar agora e aumente sua produtividade!**

---

## 📞 Precisa de Ajuda?

- **Documentação completa**: README.md
- **Exemplos práticos**: EXAMPLES.md
- **Guia de desenvolvimento**: DEVELOPMENT.md
- **Arquitetura**: ARCHITECTURE.md

---

**Versão**: 1.0.0  
**Tempo de leitura**: 5 minutos  
**Tempo de setup**: 5 minutos  
**Total**: ⚡ **10 minutos para começar!**
