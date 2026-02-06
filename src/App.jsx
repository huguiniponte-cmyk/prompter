import React, { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import {
    Folder,
    Description,
    History as HistoryIcon,
    Settings,
    Plus,
    Search,
    Star,
    Trash2,
    Copy,
    ArrowLeft,
    LayoutGrid,
    ChevronRight,
    AppWindow
} from 'lucide-react';
import { clsx } from 'clsx';
import { twMerge } from 'tailwind-merge';

// Helper for tailwind classes
function cn(...inputs) {
    return twMerge(clsx(inputs));
}

// --- Data Models & Storage ---
const STORAGE_KEY = 'prompter_data';

const initialData = {
    themes: [],
    prompts: [],
    outputs: [],
    settings: {
        appLock: false,
        pin: ''
    }
};

function App() {
    const [data, setData] = useState(() => {
        const stored = localStorage.getItem(STORAGE_KEY);
        return stored ? JSON.parse(stored) : initialData;
    });

    const [activeTab, setActiveTab] = useState('themes'); // themes, prompts, history, settings
    const [selectedThemeId, setSelectedThemeId] = useState(null);
    const [selectedPromptId, setSelectedPromptId] = useState(null);
    const [searchQuery, setSearchQuery] = useState('');

    // Persist data
    useEffect(() => {
        localStorage.setItem(STORAGE_KEY, JSON.stringify(data));
    }, [data]);

    // --- Actions ---
    const addTheme = (theme) => {
        setData(prev => ({ ...prev, themes: [...prev.themes, theme] }));
    };

    const addPrompt = (prompt) => {
        setData(prev => ({ ...prev, prompts: [...prev.prompts, prompt] }));
    };

    const addOutput = (output) => {
        setData(prev => ({ ...prev, outputs: [output, ...prev.outputs] }));
    };

    const toggleFavorite = (id) => {
        setData(prev => ({
            ...prev,
            prompts: prev.prompts.map(p => p.id === id ? { ...p, isFavorite: !p.isFavorite } : p)
        }));
    };

    const deleteItem = (type, id) => {
        setData(prev => ({
            ...prev,
            [type]: prev[type].filter(item => item.id !== id)
        }));
    };

    // --- Sub-components ---

    const SidebarItem = ({ id, icon: Icon, label }) => (
        <button
            onClick={() => { setActiveTab(id); setSelectedThemeId(null); setSelectedPromptId(null); }}
            className={cn(
                "flex items-center gap-3 px-4 py-3 rounded-xl transition-all duration-200 group",
                activeTab === id
                    ? "bg-primary text-white shadow-lg shadow-primary/20"
                    : "text-slate-400 hover:bg-slate-800 hover:text-slate-200"
            )}
        >
            <Icon size={20} className={cn(activeTab === id ? "text-white" : "text-slate-500 group-hover:text-slate-300")} />
            <span className="font-medium">{label}</span>
        </button>
    );

    return (
        <div className="flex h-screen bg-background overflow-hidden p-4 gap-4">
            {/* Background Gradients */}
            <div className="fixed inset-0 z-0 pointer-events-none opacity-20">
                <div className="absolute top-0 right-0 w-[500px] h-[500px] bg-primary/30 rounded-full blur-[120px]" />
                <div className="absolute bottom-0 left-0 w-[500px] h-[500px] bg-accent/20 rounded-full blur-[120px]" />
            </div>

            {/* Sidebar */}
            <aside className="w-64 glass rounded-3xl flex flex-col p-6 z-10">
                <div className="flex items-center gap-3 mb-10 px-2">
                    <div className="p-2 bg-primary rounded-xl">
                        <AppWindow className="text-white" size={24} />
                    </div>
                    <h1 className="text-2xl font-black text-white tracking-tight">Prompter</h1>
                </div>

                <nav className="flex flex-col gap-2 flex-1">
                    <SidebarItem id="themes" icon={LayoutGrid} label="Temas" />
                    <SidebarItem id="prompts" icon={Description} label="Prompts" />
                    <SidebarItem id="history" icon={HistoryIcon} label="Histórico" />
                    <div className="mt-auto">
                        <SidebarItem id="settings" icon={Settings} label="Definições" />
                    </div>
                </nav>
            </aside>

            {/* Main Content Area */}
            <main className="flex-1 glass rounded-3xl overflow-hidden relative z-10 flex flex-col">
                <AnimatePresence mode="wait">
                    {activeTab === 'themes' && (
                        <ThemesView
                            key="themes"
                            themes={data.themes}
                            onAddTheme={addTheme}
                            onSelectTheme={(id) => { setSelectedThemeId(id); setActiveTab('theme-detail'); }}
                            onDelete={(id) => deleteItem('themes', id)}
                        />
                    )}
                    {activeTab === 'theme-detail' && (
                        <ThemeDetailView
                            key="theme-detail"
                            theme={data.themes.find(t => t.id === selectedThemeId)}
                            prompts={data.prompts.filter(p => p.themeId === selectedThemeId)}
                            onBack={() => setActiveTab('themes')}
                            onSelectPrompt={(id) => { setSelectedPromptId(id); setActiveTab('use-prompt'); }}
                        />
                    )}
                    {activeTab === 'prompts' && (
                        <PromptsView
                            key="prompts"
                            prompts={data.prompts}
                            themes={data.themes}
                            onAddPrompt={addPrompt}
                            onToggleFavorite={toggleFavorite}
                            onSelectPrompt={(id) => { setSelectedPromptId(id); setActiveTab('use-prompt'); }}
                            onDelete={(id) => deleteItem('prompts', id)}
                        />
                    )}
                    {activeTab === 'use-prompt' && (
                        <UsePromptView
                            key="use-prompt"
                            prompt={data.prompts.find(p => p.id === selectedPromptId)}
                            onBack={() => setActiveTab('prompts')}
                            onSaveOutput={addOutput}
                        />
                    )}
                    {activeTab === 'history' && (
                        <HistoryView
                            key="history"
                            outputs={data.outputs}
                            onDelete={(id) => deleteItem('outputs', id)}
                        />
                    )}
                    {activeTab === 'settings' && (
                        <div key="settings" className="p-10">
                            <h2 className="text-3xl font-bold mb-6">Definições</h2>
                            <p className="text-slate-400">Em breve mais opções...</p>
                        </div>
                    )}
                </AnimatePresence>
            </main>
        </div>
    );
}

// --- Views & Components ---

function ThemesView({ themes, onAddTheme, onSelectTheme, onDelete }) {
    const [isAdding, setIsAdding] = useState(false);
    const [newTheme, setNewTheme] = useState({ name: '', icon: '📁', color: '#6366f1' });

    return (
        <motion.div
            initial={{ opacity: 0, x: 20 }}
            animate={{ opacity: 1, x: 0 }}
            exit={{ opacity: 0, x: -20 }}
            className="p-8 h-full flex flex-col"
        >
            <header className="flex justify-between items-center mb-8">
                <div>
                    <h2 className="text-4xl font-extrabold text-white">Temas</h2>
                    <p className="text-slate-400 mt-1">Organize os seus prompts por categorias</p>
                </div>
                <button
                    onClick={() => setIsAdding(true)}
                    className="flex items-center gap-2 bg-primary hover:bg-primary/90 text-white px-5 py-3 rounded-2xl transition-all shadow-lg shadow-primary/20"
                >
                    <Plus size={20} />
                    <span className="font-bold">Novo Tema</span>
                </button>
            </header>

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 overflow-y-auto pr-2 scroll-hide">
                {themes.map(theme => (
                    <motion.div
                        layoutId={theme.id}
                        key={theme.id}
                        whileHover={{ scale: 1.02 }}
                        className="p-5 rounded-3xl bg-slate-800/40 border border-white/5 hover:border-primary/50 transition-all cursor-pointer group"
                        onClick={() => onSelectTheme(theme.id)}
                    >
                        <div className="flex items-center justify-between mb-4">
                            <div
                                className="w-12 h-12 rounded-2xl flex items-center justify-center text-2xl shadow-inner"
                                style={{ backgroundColor: theme.color + '20', color: theme.color }}
                            >
                                {theme.icon}
                            </div>
                            <button
                                onClick={(e) => { e.stopPropagation(); onDelete(theme.id); }}
                                className="opacity-0 group-hover:opacity-100 p-2 text-slate-500 hover:text-red-400 transition-all"
                            >
                                <Trash2 size={18} />
                            </button>
                        </div>
                        <h3 className="text-xl font-bold text-white mb-1">{theme.name}</h3>
                        <p className="text-slate-500 text-sm font-medium uppercase tracking-widest">Clique para ver prompts</p>
                    </motion.div>
                ))}

                {themes.length === 0 && (
                    <div className="col-span-full py-20 text-center">
                        <div className="w-20 h-20 bg-slate-800/50 rounded-full flex items-center justify-center mx-auto mb-4 border border-white/5">
                            <Folder size={32} className="text-slate-600" />
                        </div>
                        <p className="text-slate-500 font-medium">Nenhum tema criado ainda</p>
                    </div>
                )}
            </div>

            {isAdding && (
                <div className="fixed inset-0 z-50 flex items-center justify-center bg-background/80 backdrop-blur-sm p-4">
                    <motion.div
                        initial={{ scale: 0.9, opacity: 0 }}
                        animate={{ scale: 1, opacity: 1 }}
                        className="glass w-full max-w-md rounded-[32px] p-8"
                    >
                        <h3 className="text-2xl font-bold mb-6">Criar Tema</h3>
                        <div className="space-y-4">
                            <div>
                                <label className="text-sm font-bold text-slate-400 mb-1 block">Nome</label>
                                <input
                                    autoFocus
                                    className="w-full bg-slate-800/50 border border-white/10 rounded-xl px-4 py-3 outline-none focus:border-primary transition-all"
                                    value={newTheme.name}
                                    onChange={e => setNewTheme(prev => ({ ...prev, name: e.target.value }))}
                                />
                            </div>
                            <div className="flex gap-4">
                                <div className="flex-1">
                                    <label className="text-sm font-bold text-slate-400 mb-1 block">Ícone</label>
                                    <select
                                        className="w-full bg-slate-800/50 border border-white/10 rounded-xl px-4 py-3 outline-none focus:border-primary transition-all appearance-none"
                                        value={newTheme.icon}
                                        onChange={e => setNewTheme(prev => ({ ...prev, icon: e.target.value }))}
                                    >
                                        <option>📁</option><option>💼</option><option>🚀</option><option>💡</option><option>🎨</option><option>🔥</option>
                                    </select>
                                </div>
                                <div>
                                    <label className="text-sm font-bold text-slate-400 mb-1 block">Cor</label>
                                    <input
                                        type="color"
                                        className="h-[50px] w-[50px] rounded-xl overflow-hidden cursor-pointer"
                                        value={newTheme.color}
                                        onChange={e => setNewTheme(prev => ({ ...prev, color: e.target.value }))}
                                    />
                                </div>
                            </div>
                        </div>
                        <div className="flex gap-3 mt-8">
                            <button
                                onClick={() => setIsAdding(false)}
                                className="flex-1 px-4 py-3 rounded-xl font-bold hover:bg-slate-800 transition-all text-slate-400"
                            >
                                Cancelar
                            </button>
                            <button
                                onClick={() => {
                                    onAddTheme({ ...newTheme, id: Date.now().toString() });
                                    setIsAdding(false);
                                    setNewTheme({ name: '', icon: '📁', color: '#6366f1' });
                                }}
                                disabled={!newTheme.name}
                                className="flex-1 bg-primary text-white font-bold py-3 rounded-xl hover:bg-primary/90 disabled:opacity-50"
                            >
                                Criar
                            </button>
                        </div>
                    </motion.div>
                </div>
            )}
        </motion.div>
    );
}

function PromptsView({ prompts, themes, onAddPrompt, onToggleFavorite, onSelectPrompt, onDelete }) {
    const [isAdding, setIsAdding] = useState(false);
    const [newPrompt, setNewPrompt] = useState({ title: '', body: '', themeId: themes[0]?.id || '' });

    return (
        <motion.div
            initial={{ opacity: 0, x: 20 }}
            animate={{ opacity: 1, x: 0 }}
            exit={{ opacity: 0, x: -20 }}
            className="p-8 h-full flex flex-col"
        >
            <header className="flex justify-between items-center mb-8">
                <div>
                    <h2 className="text-4xl font-extrabold text-white">Prompts</h2>
                    <p className="text-slate-400 mt-1">Todos os seus templates salvos</p>
                </div>
                <button
                    onClick={() => setIsAdding(true)}
                    className="flex items-center gap-2 bg-primary hover:bg-primary/90 text-white px-5 py-3 rounded-2xl transition-all shadow-lg shadow-primary/20"
                >
                    <Plus size={20} />
                    <span className="font-bold">Novo Prompt</span>
                </button>
            </header>

            <div className="flex-1 overflow-y-auto pr-2 scroll-hide space-y-4">
                {prompts.map(prompt => {
                    const theme = themes.find(t => t.id === prompt.themeId);
                    return (
                        <div
                            key={prompt.id}
                            onClick={() => onSelectPrompt(prompt.id)}
                            className="p-5 rounded-3xl bg-slate-800/40 border border-white/5 hover:border-white/10 transition-all cursor-pointer group flex items-start gap-4"
                        >
                            <div className="pt-1">
                                <button
                                    onClick={(e) => { e.stopPropagation(); onToggleFavorite(prompt.id); }}
                                    className={cn("transition-all", prompt.isFavorite ? "text-yellow-500" : "text-slate-600 hover:text-slate-400")}
                                >
                                    <Star fill={prompt.isFavorite ? "currentColor" : "none"} size={22} />
                                </button>
                            </div>
                            <div className="flex-1">
                                <div className="flex justify-between items-start mb-1">
                                    <h3 className="text-xl font-bold text-white">{prompt.title}</h3>
                                    <button
                                        onClick={(e) => { e.stopPropagation(); onDelete(prompt.id); }}
                                        className="opacity-0 group-hover:opacity-100 p-2 text-slate-500 hover:text-red-400 transition-all"
                                    >
                                        <Trash2 size={18} />
                                    </button>
                                </div>
                                <div className="flex items-center gap-2 text-sm font-semibold mb-3">
                                    <span className="w-2 h-2 rounded-full" style={{ backgroundColor: theme?.color || '#ccc' }}></span>
                                    <span className="text-slate-400">{theme?.name || 'Sem Tema'}</span>
                                </div>
                                <p className="text-slate-500 line-clamp-2 text-sm">{prompt.body}</p>
                            </div>
                            <div className="self-center p-3 text-slate-600 group-hover:text-primary transition-all">
                                <ChevronRight size={24} />
                            </div>
                        </div>
                    );
                })}
            </div>

            {isAdding && (
                <div className="fixed inset-0 z-50 flex items-center justify-center bg-background/80 backdrop-blur-sm p-4">
                    <motion.div
                        initial={{ scale: 0.9, opacity: 0 }}
                        animate={{ scale: 1, opacity: 1 }}
                        className="glass w-full max-w-2xl rounded-[32px] p-8"
                    >
                        <h3 className="text-2xl font-bold mb-6">Criar Prompt</h3>
                        <div className="space-y-4">
                            <div className="grid grid-cols-2 gap-4">
                                <div>
                                    <label className="text-sm font-bold text-slate-400 mb-1 block">Título</label>
                                    <input
                                        autoFocus
                                        className="w-full bg-slate-800/50 border border-white/10 rounded-xl px-4 py-3 outline-none focus:border-primary transition-all"
                                        value={newPrompt.title}
                                        onChange={e => setNewPrompt(prev => ({ ...prev, title: e.target.value }))}
                                    />
                                </div>
                                <div>
                                    <label className="text-sm font-bold text-slate-400 mb-1 block">Tema</label>
                                    <select
                                        className="w-full bg-slate-800/50 border border-white/10 rounded-xl px-4 py-3 outline-none focus:border-primary transition-all appearance-none"
                                        value={newPrompt.themeId}
                                        onChange={e => setNewPrompt(prev => ({ ...prev, themeId: e.target.value }))}
                                    >
                                        {themes.map(t => <option key={t.id} value={t.id}>{t.name}</option>)}
                                    </select>
                                </div>
                            </div>
                            <div>
                                <label className="text-sm font-bold text-slate-400 mb-1 block">Corpo (use {'{variável}'})</label>
                                <textarea
                                    rows={6}
                                    className="w-full bg-slate-800/50 border border-white/10 rounded-xl px-4 py-3 outline-none focus:border-primary transition-all font-mono text-sm"
                                    value={newPrompt.body}
                                    onChange={e => setNewPrompt(prev => ({ ...prev, body: e.target.value }))}
                                />
                            </div>
                        </div>
                        <div className="flex gap-3 mt-8">
                            <button
                                onClick={() => setIsAdding(false)}
                                className="flex-1 px-4 py-3 rounded-xl font-bold hover:bg-slate-800 transition-all text-slate-400"
                            >
                                Cancelar
                            </button>
                            <button
                                onClick={() => {
                                    onAddPrompt({ ...newPrompt, id: Date.now().toString(), createdAt: Date.now(), isFavorite: false });
                                    setIsAdding(false);
                                }}
                                disabled={!newPrompt.title || !newPrompt.body}
                                className="flex-1 bg-primary text-white font-bold py-3 rounded-xl hover:bg-primary/90 disabled:opacity-50"
                            >
                                Criar
                            </button>
                        </div>
                    </motion.div>
                </div>
            )}
        </motion.div>
    );
}

function UsePromptView({ prompt, onBack, onSaveOutput }) {
    const variables = (prompt.body.match(/\{([a-zA-Z0-9_ ]+)\}/g) || []).map(v => v.slice(1, -1));
    const [values, setValues] = useState({});
    const [result, setResult] = useState(prompt.body);
    const [showResult, setShowResult] = useState(false);

    useEffect(() => {
        let filled = prompt.body;
        variables.forEach(v => {
            const val = values[v] || `{${v}}`;
            filled = filled.replaceAll(`{${v}}`, val);
        });
        setResult(filled);
    }, [values, prompt.body]);

    const handleCopy = () => {
        navigator.clipboard.writeText(result);
        onSaveOutput({ id: Date.now().toString(), outputText: result, createdAt: Date.now(), promptTitle: prompt.title });
        // Show feedback
    };

    return (
        <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="p-10 h-full flex flex-col items-center"
        >
            <div className="w-full max-w-3xl flex flex-col h-full">
                <button onClick={onBack} className="flex items-center gap-2 text-slate-400 hover:text-white mb-8 transition-all w-fit">
                    <ArrowLeft size={20} />
                    <span className="font-bold">Voltar</span>
                </button>

                <h2 className="text-4xl font-black mb-2 text-white">{prompt.title}</h2>
                <p className="text-slate-400 mb-10">Preencha as variáveis e gere o seu prompt final</p>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-8 items-start">
                    <div className="space-y-6">
                        {variables.length === 0 && (
                            <p className="text-slate-500 italic">Este prompt não tem variáveis.</p>
                        )}
                        {variables.map(v => (
                            <div key={v}>
                                <label className="text-sm font-black text-slate-400 uppercase tracking-widest mb-2 block">{v}</label>
                                <input
                                    className="w-full bg-slate-800/40 border border-white/5 rounded-2xl px-5 py-4 outline-none focus:border-primary focus:bg-slate-800/60 transition-all font-medium text-white shadow-inner"
                                    placeholder={`Preencher ${v}...`}
                                    onChange={e => setValues(prev => ({ ...prev, [v]: e.target.value }))}
                                />
                            </div>
                        ))}
                        <button
                            onClick={handleCopy}
                            className="w-full bg-primary text-white font-black py-5 rounded-2xl hover:bg-primary/90 flex items-center justify-center gap-3 shadow-lg shadow-primary/20 transition-all"
                        >
                            <Copy size={20} />
                            COPIAR RESULTADO
                        </button>
                    </div>

                    <div className="glass rounded-[32px] p-8 flex flex-col min-h-[400px]">
                        <div className="flex items-center justify-between mb-4 border-b border-white/5 pb-4">
                            <span className="text-xs font-black text-slate-500 uppercase tracking-[0.2em]">Preview do Prompt</span>
                            <div className="flex gap-2">
                                <div className="w-3 h-3 rounded-full bg-slate-800"></div>
                                <div className="w-3 h-3 rounded-full bg-slate-800"></div>
                                <div className="w-3 h-3 rounded-full bg-slate-800"></div>
                            </div>
                        </div>
                        <div className="flex-1 text-slate-100 font-mono text-sm leading-relaxed whitespace-pre-wrap">
                            {result}
                        </div>
                    </div>
                </div>
            </div>
        </motion.div>
    );
}

function HistoryView({ outputs, onDelete }) {
    return (
        <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="p-10 h-full flex flex-col"
        >
            <h2 className="text-4xl font-extrabold text-white mb-2">Histórico</h2>
            <p className="text-slate-400 mb-10">Últimos prompts copiados</p>

            <div className="flex-1 overflow-y-auto pr-2 scroll-hide space-y-4">
                {outputs.map(out => (
                    <div key={out.id} className="p-6 rounded-[32px] bg-slate-800/30 border border-white/5 flex flex-col gap-3 group relative overflow-hidden">
                        <div className="absolute top-0 right-0 p-4 opacity-0 group-hover:opacity-100 transition-all">
                            <button onClick={() => onDelete(out.id)} className="text-slate-500 hover:text-red-400">
                                <Trash2 size={18} />
                            </button>
                        </div>
                        <div className="flex justify-between items-center pr-10">
                            <span className="text-xs font-black text-primary uppercase tracking-[0.15em]">{out.promptTitle}</span>
                            <span className="text-xs text-slate-500 font-medium">{new Date(out.createdAt).toLocaleString()}</span>
                        </div>
                        <p className="text-slate-300 text-sm line-clamp-3 leading-relaxed">{out.outputText}</p>
                        <button
                            onClick={() => navigator.clipboard.writeText(out.outputText)}
                            className="text-xs font-bold text-slate-400 flex items-center gap-1 hover:text-blue-400 transition-all uppercase tracking-widest"
                        >
                            <Copy size={12} /> Copiar novamente
                        </button>
                    </div>
                ))}

                {outputs.length === 0 && (
                    <div className="py-20 text-center">
                        <p className="text-slate-500">Ainda não há histórico disponível.</p>
                    </div>
                )}
            </div>
        </motion.div>
    );
}

function ThemeDetailView({ theme, prompts, onBack, onSelectPrompt }) {
    return (
        <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="p-10 h-full flex flex-col"
        >
            <button onClick={onBack} className="flex items-center gap-2 text-slate-400 hover:text-white mb-8 transition-all w-fit">
                <ArrowLeft size={20} />
                <span className="font-bold">Todos os Temas</span>
            </button>

            <div className="flex items-center gap-4 mb-2">
                <div
                    className="w-16 h-16 rounded-3xl flex items-center justify-center text-4xl shadow-inner border border-white/5"
                    style={{ backgroundColor: theme?.color + '20', color: theme?.color }}
                >
                    {theme?.icon}
                </div>
                <div>
                    <h2 className="text-4xl font-extrabold text-white">{theme?.name}</h2>
                    <p className="text-slate-400">{prompts.length} prompts salvos</p>
                </div>
            </div>

            <div className="flex-1 overflow-y-auto pr-2 scroll-hide mt-10 space-y-4">
                {prompts.map(prompt => (
                    <div
                        key={prompt.id}
                        onClick={() => onSelectPrompt(prompt.id)}
                        className="p-6 rounded-[32px] bg-slate-800/40 border border-white/5 hover:border-primary/30 transition-all cursor-pointer flex justify-between items-center group"
                    >
                        <div>
                            <h3 className="text-xl font-bold text-white mb-1">{prompt.title}</h3>
                            <p className="text-slate-500 line-clamp-1 text-sm">{prompt.body}</p>
                        </div>
                        <div className="p-3 text-slate-600 group-hover:text-primary transition-all">
                            <ChevronRight size={20} />
                        </div>
                    </div>
                ))}
            </div>
        </motion.div>
    );
}

export default App;
