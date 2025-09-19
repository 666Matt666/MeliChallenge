import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 8081, // Puerto para el servidor de desarrollo de Vue
    proxy: {
      // Redirige las peticiones de /api al backend de Spring Boot
      '/api': {
        target: 'http://localhost:8080', // Tu backend
        changeOrigin: true,
      },
      // Redirige las peticiones de imágenes
      '/images': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      }
    }
  }
})