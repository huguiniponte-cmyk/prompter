# Prompter Website

Landing page para download da app Prompter.

## Deploy no Vercel

### Opção 1: Via Dashboard (Mais Fácil)

1. Acesse [vercel.com](https://vercel.com)
2. Faça login com GitHub
3. Click "Add New" → "Project"
4. Importe o repositório Prompter
5. Configure:
   - **Root Directory**: `website`
   - **Framework Preset**: Other
6. Click "Deploy"

### Opção 2: Via CLI

```bash
# 1. Instalar Vercel CLI
npm i -g vercel

# 2. Login
vercel login

# 3. Deploy
cd website
vercel

# 4. Deploy para produção
vercel --prod
```

## Atualizar Link de Download

Após fazer upload do APK para GitHub Releases, edite `index.html`:

```html
<!-- Linha 141 -->
<a href="https://github.com/SEU-USUARIO/prompter/releases/latest/download/app-debug.apk">
```

Substitua `SEU-USUARIO` pelo seu username do GitHub.

## Personalizar

### Cores
Edite o gradiente no `<style>`:
```css
background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
```

### Conteúdo
Edite o HTML diretamente em `index.html`.

## URL Final

Após deploy, seu site estará em:
```
https://prompter.vercel.app
```

ou domínio customizado que configurar.
