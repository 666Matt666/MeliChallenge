# Documento de Diseño - Aplicación de Gestión de Usuarios

## 1. Introducción

Este documento describe el diseño y la arquitectura de una aplicación web Full-Stack simple para la gestión de usuarios. La aplicación consta de un backend construido con Spring Boot y un frontend construido con Vue.js.

El objetivo principal es proporcionar una interfaz para realizar operaciones CRUD (Crear, Leer, Actualizar, Borrar) sobre una lista de usuarios.

## 2. Arquitectura General

La aplicación sigue una arquitectura cliente-servidor desacoplada:

-   **Backend (Servidor):** Una API RESTful desarrollada en Java con el framework Spring Boot. Se encarga de la lógica de negocio, la gestión de datos y la seguridad.
-   **Frontend (Cliente):** Una Single-Page Application (SPA) desarrollada con Vue.js (usando la Composition API y `<script setup>`) y construida con Vite. Consume la API del backend para mostrar y manipular los datos.

Durante el desarrollo, el frontend se sirve a través del servidor de desarrollo de Vite, que actúa como un proxy para las peticiones al backend, evitando así problemas de CORS.

## 3. Componentes Principales

### 3.1. Backend (Spring Boot)

El backend es responsable de la lógica de la aplicación y la persistencia de los datos.

#### 3.1.1. API Endpoints

El backend expone los siguientes endpoints principales bajo el prefijo `/api`:

**Autenticación (`/auth`)**

-   `POST /auth/register`: Registra un nuevo usuario.
-   `POST /auth/login`: Autentifica un usuario y devuelve un token JWT.

**Usuarios (`/api/users`)**

-   `GET /api/users`: Obtiene la lista completa de usuarios.
-   `POST /api/users`: Crea un nuevo usuario. La contraseña se encripta antes de guardarla.
-   `PUT /api/users/{id}`: Actualiza un usuario existente (funcionalidad parcialmente implementada).
-   `DELETE /api/users/{id}`: Elimina un usuario (funcionalidad parcialmente implementada).

#### 3.1.2. Modelo de Datos

El modelo principal es `User`, que contiene los siguientes campos:
-   `id`: Identificador único (String).
-   `username`: Nombre de usuario (String).
-   `email`: Correo electrónico (String).
-   `password`: Contraseña (String, se almacena encriptada).

Para las respuestas de la API, se utiliza un `UserDTO` que omite la contraseña para no exponerla en el frontend.

#### 3.1.3. Persistencia

Para simplificar y facilitar la ejecución del desafío técnico, la persistencia no se realiza en una base de datos tradicional. En su lugar, se utiliza un repositorio en memoria (`UserRepository.java`) que sigue este comportamiento:

1.  **Inicialización:** Al arrancar la aplicación, el repositorio lee una lista de usuarios desde el archivo `src/main/resources/data/users.json`.
2.  **Operaciones en Memoria:** Todas las operaciones CRUD se realizan sobre la lista de usuarios en memoria.
3.  **Persistencia en Archivo:** Después de cada operación de escritura (crear, actualizar, borrar), la lista completa de usuarios en memoria se escribe de nuevo en el archivo `users.json`.

> **Nota:** Este enfoque es únicamente para fines de demostración y no es adecuado para un entorno de producción debido a problemas de concurrencia y rendimiento.

#### 3.1.4. Seguridad

La seguridad se gestiona con Spring Security. Inicialmente, todos los endpoints de `/api/users` estaban protegidos. Para facilitar el desarrollo y las pruebas del CRUD, se ha configurado temporalmente para permitir el acceso público a ` /api/users/**`.

La encriptación de contraseñas se realiza utilizando `BCryptPasswordEncoder`.

### 3.2. Frontend (Vue.js)

El frontend es una SPA que proporciona la interfaz de usuario para interactuar con la API del backend.

#### 3.2.1. Estructura de Componentes

-   **`App.vue`:** El componente raíz que renderiza el componente principal de la aplicación.
-   **`UserManagement.vue`:** El componente principal que contiene toda la lógica y la interfaz para:
    -   Mostrar la lista de usuarios.
    -   Un formulario para agregar y editar usuarios.
    -   Botones para eliminar usuarios.

#### 3.2.2. Gestión de Estado

El estado se gestiona localmente dentro del componente `UserManagement.vue` utilizando refs de la Composition API de Vue (`ref`).
-   `users`: Un array que almacena la lista de usuarios obtenida del backend.
-   `currentUser`: Un objeto que se vincula al formulario para crear o editar un usuario.
-   `isEditing`: Un booleano que controla si el formulario está en modo "Agregar" o "Editar".

#### 3.2.3. Comunicación con el Backend

-   **Axios:** Se utiliza la librería `axios` para realizar las peticiones HTTP a la API del backend.
-   **Proxy de Vite:** El archivo `vite.config.js` configura un proxy que redirige todas las peticiones que comienzan con `/api` al servidor de backend en `http://localhost:8080`. Esto permite que el frontend haga peticiones a su propio servidor de desarrollo (`localhost:8081`) sin incurrir en problemas de CORS.

## 4. Flujo de Datos (Ejemplo: Agregar un Usuario)

1.  El usuario rellena los campos del formulario "Agregar Usuario" en el frontend.
2.  Al hacer clic en "Guardar", la función `saveUser` en `UserManagement.vue` es invocada.
3.  Se realiza una petición `POST` a `/api/users` con los datos del nuevo usuario usando `axios`.
4.  El proxy de Vite redirige la petición a `http://localhost:8080/api/users`.
5.  El `UserController` en el backend recibe la petición y llama al `UserService`.
6.  El `UserService` encripta la contraseña y pasa el objeto `User` al `UserRepository`.
7.  El `UserRepository` añade el nuevo usuario a la lista en memoria y la persiste en el archivo `users.json`.
8.  El backend responde con el usuario recién creado (en formato DTO).
9.  El frontend, al recibir la respuesta exitosa, resetea el formulario y vuelve a solicitar la lista completa de usuarios (`fetchUsers`) para actualizar la vista.

## 5. Configuración y Ejecución

1.  **Backend:** Ejecutar la aplicación Spring Boot. El servidor se iniciará en el puerto `8080`.
2.  **Frontend:** Navegar al directorio `frontend` y ejecutar `npm install` seguido de `npm run dev`. El servidor de desarrollo se iniciará en el puerto `8081`.
3.  Abrir `http://localhost:8081` en el navegador.

## 6. Posibles Mejoras

-   **Persistencia:** Reemplazar el repositorio basado en archivos JSON por una base de datos real (ej. H2, PostgreSQL) usando Spring Data JPA.
-   **Seguridad:** Implementar completamente el flujo de autenticación con JWT en el frontend, almacenando el token y enviándolo en las cabeceras de las peticiones a endpoints protegidos.
-   **Gestión de Errores:** Mejorar el manejo de errores en el frontend para mostrar mensajes claros al usuario cuando una petición a la API falla.
-   **Validación:** Añadir validaciones tanto en el frontend (para una UX más fluida) como en el backend (para garantizar la integridad de los datos).
-   **UI/UX:** Mejorar el diseño visual, añadir feedback de carga (spinners) y confirmaciones de éxito.
-   **Pruebas:** Escribir pruebas unitarias y de integración para el backend y el frontend.

---