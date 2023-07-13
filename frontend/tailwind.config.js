/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './pages/**/*.{html,js}',
    './components/**/*.{html,js}',
    './app/**/*.{html,js}',
  ],
  theme: {
    extend: {
      colors: {
        transparent: 'transparent',
        current: 'currentColor',
        midnight: '#121063',
        deep_blue: '#121082',
      },
    },
  },
  plugins: [],
  safelist: ['bg-midnight', 'bg-deep_blue'],
}

