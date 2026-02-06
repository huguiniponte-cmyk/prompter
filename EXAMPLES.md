# 📚 Exemplos de Uso - Prompter

Este documento contém exemplos práticos de como usar a aplicação Prompter.

---

## 🎯 Caso de Uso 1: Email Profissional

### Passo 1: Criar Tema
- **Nome**: Trabalho
- **Cor**: Azul
- **Ícone**: 💼

### Passo 2: Criar Prompt
```
Título: Email Profissional

Corpo:
Escreve um email profissional para {destinatario} sobre {assunto}.

Contexto: {contexto}
Tom: {tom}
Comprimento: {comprimento}

Pontos principais a incluir:
{pontos_chave}

Notas: Use linguagem formal e estrutura clara
```

### Passo 3: Usar Prompt
**Variáveis preenchidas**:
- destinatario: João Silva
- assunto: Proposta de Projeto
- contexto: Reunião da semana passada
- tom: Formal e cordial
- comprimento: Médio (200-300 palavras)
- pontos_chave: Orçamento, Timeline, Equipa

**Prompt final copiado**:
```
Escreve um email profissional para João Silva sobre Proposta de Projeto.

Contexto: Reunião da semana passada
Tom: Formal e cordial
Comprimento: Médio (200-300 palavras)

Pontos principais a incluir:
Orçamento, Timeline, Equipa
```

### Passo 4: Guardar Output
Após usar o prompt num LLM externo, colar o resultado:

```
Assunto: Proposta de Projeto - Seguimento da Reunião

Caro João Silva,

Espero que este email o encontre bem.

Na sequência da nossa reunião da semana passada, venho por este meio 
apresentar a proposta de projeto que discutimos...

[resto do email]

Atenciosamente,
[Nome]
```

**Rating**: ⭐⭐⭐⭐⭐ (5 estrelas)

---

## 🎯 Caso de Uso 2: Conteúdo para Redes Sociais

### Passo 1: Criar Tema
- **Nome**: Marketing
- **Cor**: Laranja
- **Ícone**: 🎨

### Passo 2: Criar Prompt
```
Título: Post Instagram

Corpo:
Cria um post para Instagram sobre {produto} direcionado a {publico_alvo}.

Objetivo: {objetivo}
Tom: {tom}
Hashtags: {numero_hashtags}

Call-to-action: {cta}

Notas: Máximo 2200 caracteres, incluir emojis relevantes
```

### Passo 3: Usar Prompt
**Variáveis**:
- produto: Curso de Fotografia Online
- publico_alvo: Fotógrafos iniciantes
- objetivo: Aumentar inscrições
- tom: Inspirador e acessível
- numero_hashtags: 10-15
- cta: Link na bio

### Passo 4: Histórico
Guardar múltiplas versões e comparar ratings para reutilizar as melhores.

---

## 🎯 Caso de Uso 3: Código de Programação

### Passo 1: Criar Tema
- **Nome**: Desenvolvimento
- **Cor**: Verde
- **Ícone**: 💻

### Passo 2: Criar Prompt
```
Título: Função Python

Corpo:
Escreve uma função em Python que {funcionalidade}.

Requisitos:
- Linguagem: {linguagem}
- Tipo de retorno: {tipo_retorno}
- Parâmetros: {parametros}
- Tratamento de erros: {erros}

Inclui:
- Docstring
- Type hints
- Testes unitários

Notas: Seguir PEP 8
```

### Passo 3: Usar Prompt
**Variáveis**:
- funcionalidade: calcula a média de uma lista de números
- linguagem: Python 3.10+
- tipo_retorno: float
- parametros: lista de números (list[float])
- erros: ValueError para lista vazia

---

## 🎯 Caso de Uso 4: Conteúdo Educacional

### Passo 1: Criar Tema
- **Nome**: Educação
- **Cor**: Roxo
- **Ícone**: 📚

### Passo 2: Criar Prompt
```
Título: Explicação Simples

Corpo:
Explica {conceito} para {nivel_conhecimento}.

Formato: {formato}
Comprimento: {comprimento}
Incluir: {elementos}

Use analogias e exemplos práticos.

Notas: Linguagem clara e acessível
```

### Passo 3: Usar Prompt
**Variáveis**:
- conceito: Blockchain
- nivel_conhecimento: Iniciante (sem conhecimento técnico)
- formato: Texto com bullet points
- comprimento: 300-400 palavras
- elementos: Definição, Como funciona, Exemplos práticos

---

## 🎯 Caso de Uso 5: Análise de Dados

### Passo 1: Criar Tema
- **Nome**: Análise
- **Cor**: Azul Escuro
- **Ícone**: 📊

### Passo 2: Criar Prompt
```
Título: Análise de Dados

Corpo:
Analisa os seguintes dados sobre {tema}:

Dados: {dados}

Foco da análise: {foco}
Métricas importantes: {metricas}
Formato de saída: {formato}

Inclui:
- Insights principais
- Tendências
- Recomendações

Notas: Baseado em dados, não em suposições
```

### Passo 3: Usar Prompt
**Variáveis**:
- tema: Vendas mensais
- dados: [colar CSV ou JSON]
- foco: Identificar produtos mais vendidos
- metricas: Volume, Receita, Crescimento
- formato: Relatório executivo

---

## 🎯 Caso de Uso 6: Criação de Conteúdo

### Passo 1: Criar Tema
- **Nome**: Escrita Criativa
- **Cor**: Rosa
- **Ícone**: ✍️

### Passo 2: Criar Prompt
```
Título: História Curta

Corpo:
Escreve uma história curta sobre {tema}.

Personagem principal: {personagem}
Cenário: {cenario}
Conflito: {conflito}
Tom: {tom}
Comprimento: {comprimento}

Estrutura:
- Introdução
- Desenvolvimento
- Clímax
- Resolução

Notas: Foco em descrições vívidas
```

---

## 💡 Dicas de Uso

### 1. Organização de Temas
```
📁 Trabalho
   ├── Emails
   ├── Relatórios
   └── Apresentações

📁 Pessoal
   ├── Cartas
   ├── Planejamento
   └── Ideias

📁 Criativo
   ├── Histórias
   ├── Poemas
   └── Roteiros
```

### 2. Nomenclatura de Prompts
✅ **Bom**: "Email Profissional - Proposta"
✅ **Bom**: "Post Instagram - Produto"
❌ **Evitar**: "Prompt 1", "Teste"

### 3. Uso de Variáveis
```
✅ Específicas: {destinatario}, {data_entrega}
✅ Descritivas: {tom_comunicacao}, {publico_alvo}
❌ Genéricas: {var1}, {x}, {texto}
```

### 4. Notas Úteis
Adicione sempre notas com:
- Contexto de uso
- Limitações
- Exemplos
- Melhores práticas

### 5. Sistema de Rating
```
⭐⭐⭐⭐⭐ (5) - Perfeito, reutilizar sempre
⭐⭐⭐⭐ (4) - Muito bom, pequenos ajustes
⭐⭐⭐ (3) - Bom, precisa melhorias
⭐⭐ (2) - Fraco, revisar prompt
⭐ (1) - Não funcionou, refazer
```

---

## 🔄 Workflow Recomendado

### Fluxo Diário
```
1. Manhã
   └── Revisar prompts favoritos
   └── Preparar prompts do dia

2. Durante o dia
   └── Usar prompts conforme necessário
   └── Guardar outputs com rating

3. Fim do dia
   └── Revisar outputs do dia
   └── Ajustar prompts baseado em resultados
   └── Criar novos prompts para amanhã
```

### Fluxo Semanal
```
1. Segunda
   └── Planear prompts da semana

2. Quarta
   └── Revisar meio da semana
   └── Ajustar estratégia

3. Sexta
   └── Análise semanal
   └── Identificar melhores prompts
   └── Arquivar outputs importantes
```

---

## 📊 Métricas de Sucesso

### Acompanhe
- **Prompts mais usados**: Identifique padrões
- **Ratings médios**: Melhore prompts com baixo rating
- **Tempo economizado**: Compare antes/depois
- **Qualidade dos outputs**: Evolução ao longo do tempo

### Otimize
1. **Prompts com rating < 3**: Revisar e melhorar
2. **Prompts não usados há 30 dias**: Arquivar ou deletar
3. **Variáveis repetidas**: Criar templates
4. **Outputs similares**: Consolidar prompts

---

## 🎓 Boas Práticas

### ✅ Faça
- Use nomes descritivos
- Adicione notas contextuais
- Rate todos os outputs
- Revise prompts regularmente
- Organize por temas
- Reutilize outputs de sucesso

### ❌ Evite
- Prompts genéricos demais
- Muitas variáveis (máx 5-7)
- Nomes confusos
- Outputs sem rating
- Temas desorganizados
- Deletar outputs úteis

---

## 🚀 Casos Avançados

### Template de Template
Crie um prompt para gerar outros prompts:

```
Título: Gerador de Prompts

Corpo:
Cria um prompt otimizado para {objetivo}.

Contexto: {contexto_uso}
Variáveis necessárias: {variaveis}
Formato de saída: {formato}

O prompt deve:
- Ser claro e específico
- Incluir 3-5 variáveis
- Ter instruções detalhadas
- Incluir notas de uso
```

### Chains de Prompts
Use outputs de um prompt como input de outro:

```
Prompt 1: Brainstorm de Ideias
   ↓ (output)
Prompt 2: Desenvolver Ideia Escolhida
   ↓ (output)
Prompt 3: Refinar e Polir
```

### Versionamento Manual
Crie variações do mesmo prompt:

```
Email Profissional v1 - Tom Formal
Email Profissional v2 - Tom Casual
Email Profissional v3 - Tom Urgente
```

---

## 📱 Atalhos de Produtividade

### Favoritos
Marque como favorito:
- Prompts usados diariamente
- Prompts com rating 5
- Templates base

### Pesquisa Rápida
Use palavras-chave específicas:
- "email cliente"
- "post instagram produto"
- "análise dados vendas"

### Histórico
Consulte outputs antigos para:
- Referência de qualidade
- Reutilização de conteúdo
- Aprendizado de padrões

---

**Dica Final**: A chave do sucesso com o Prompter é a **consistência**. 
Use diariamente, rate honestamente, e refine continuamente seus prompts!

---

**Versão**: 1.0.0  
**Última atualização**: 2026-02-06
