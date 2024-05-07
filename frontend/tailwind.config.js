/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  daisyui: {
    themes: [
      {
        mytheme: {
          "primary": "#b5cc18",
          "primary-content": "#070707",
          "base-100": "#070707",
          "base-200": "#202020",
          "base-content": "#f5f5f5",
          "error": "#ff0000",
        },
      },
    ],
  },
  plugins: [
    require("daisyui"), 
    require('tailwind-scrollbar')({ nocompatible: true }),
  ],
}

