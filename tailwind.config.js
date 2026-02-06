/** @type {import('tailwindcss').Config} */
export default {
    content: [
        "./index.html",
        "./src/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            colors: {
                background: '#020617', // Slate 950
                surface: '#0f172a',    // Slate 900
                card: '#1e293b',       // Slate 800
                primary: '#6366f1',    // Indigo 500
                accent: '#f43f5e',     // Rose 500
            },
            fontFamily: {
                sans: ['Inter', 'sans-serif'],
            }
        },
    },
    plugins: [],
}
