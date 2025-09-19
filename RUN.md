# Instrucciones de Ejecución

Este documento proporciona los pasos necesarios para compilar y ejecutar la aplicación Full-Stack (Spring Boot + Vue.js) en un entorno de desarrollo local.

## 0. Obtención del Código Fuente

Para obtener el código fuente del proyecto, clona el repositorio desde GitHub y navega hasta el directorio recién creado:

```bash
git clone https://github.com/666Matt666/MeliChallenge.git
cd MeliChallenge
```

## 1. Prerrequisitos

Asegúrate de tener instalado el siguiente software en tu sistema:

-   **Java Development Kit (JDK)**: Versión 17 o superior.
-   **Apache Maven**: Para gestionar y construir el proyecto de backend.
-   **Node.js**: Versión 16 o superior.
-   **npm** (o yarn/pnpm): Gestor de paquetes de Node.js, generalmente se instala con Node.js.

## 2. Ejecución del Backend (Servidor Spring Boot)

El backend es el servidor que expone la API REST.

1.  Abre una nueva terminal o línea de comandos.
2.  Navega hasta el directorio del backend:
    ```bash
    cd backend
    ```
3.  Ejecuta el siguiente comando de Maven para iniciar la aplicación:
    ```bash
    mvn spring-boot:run
    ```
4.  El servidor se iniciará y estará escuchando en `http://localhost:8080`. Verás el logo de Spring Boot y los logs de inicio en la terminal. **Deja esta terminal abierta.**

## 3. Ejecución del Frontend (Cliente Vue.js)

El frontend es la interfaz de usuario que se consume en el navegador.

1.  Abre una **segunda terminal** (no cierres la del backend).
2.  Navega hasta el directorio del frontend:
    ```bash
    cd frontend
    ```
3.  Si es la primera vez que ejecutas el proyecto, instala las dependencias de Node.js:
    ```bash
    npm install
    ```
4.  Inicia el servidor de desarrollo de Vite:
    ```bash
    npm run dev
    ```
5.  El servidor de desarrollo se iniciará y estará disponible en `http://localhost:8081`.

## 4. Acceder a la Aplicación

Una vez que ambos servidores (backend y frontend) estén en ejecución:

1.  Abre tu navegador web.
2.  Ve a la siguiente URL: **http://localhost:8081/**

Deberías ver la página de "Gestión de Usuarios", que te permitirá agregar, editar y eliminar usuarios.

## 5. Detener la Aplicación

Para detener la aplicación, simplemente cierra las dos terminales que abriste, o presiona `Ctrl + C` en cada una de ellas.