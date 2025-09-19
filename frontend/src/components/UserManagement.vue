<template>
  <div class="user-management">
    <h1>Gestión de Usuarios</h1>

    <div class="user-form-card">
      <h2>{{ isEditing ? 'Editar Usuario' : 'Agregar Usuario' }}</h2>
      <form @submit.prevent="saveUser">
        <div class="form-group">
          <label for="name">Nombre:</label>
          <input id="name" v-model="currentUser.name" placeholder="Nombre completo" required />
        </div>
        <div class="form-group">
          <label for="email">Email:</label>
          <input id="email" type="email" v-model="currentUser.email" placeholder="correo@ejemplo.com" required />
        </div>
        <div class="form-actions">
          <button type="submit" class="btn-primary">{{ isEditing ? 'Actualizar' : 'Guardar' }}</button>
          <button type="button" @click="resetForm" class="btn-secondary">Cancelar</button>
        </div>
      </form>
    </div>

    <div class="user-list-card">
      <h2>Lista de Usuarios</h2>
      <ul v-if="users.length">
        <li v-for="user in users" :key="user.id" class="user-item">
          <div class="user-info">
            <span class="user-name">{{ user.name }}</span>
            <span class="user-email">{{ user.email }}</span>
          </div>
          <div class="user-actions">
            <button @click="editUser(user)" class="btn-icon btn-edit">✏️</button>
            <button @click="deleteUser(user.id)" class="btn-icon btn-delete">🗑️</button>
          </div>
        </li>
      </ul>
      <p v-else>No hay usuarios para mostrar.</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const users = ref([]);
const isEditing = ref(false);
const currentUser = ref({
  id: null,
  name: '',
  email: ''
});

const API_URL = '/api/users';

async function fetchUsers() {
  try {
    const response = await axios.get(API_URL);
    users.value = response.data;
  } catch (error) {
    console.error("Error al obtener usuarios:", error);
  }
}

async function saveUser() {
  try {
    if (isEditing.value) {
      // Actualizar usuario
      await axios.put(`${API_URL}/${currentUser.value.id}`, currentUser.value);
    } else {
      // Crear nuevo usuario
      await axios.post(API_URL, currentUser.value);
    }
    resetForm();
    await fetchUsers();
  } catch (error) {
    console.error("Error al guardar el usuario:", error);
  }
}

function editUser(user) {
  isEditing.value = true;
  currentUser.value = { ...user };
}

async function deleteUser(id) {
  if (confirm('¿Estás seguro de que quieres eliminar este usuario?')) {
    try {
      await axios.delete(`${API_URL}/${id}`);
      await fetchUsers();
    } catch (error) {
      console.error("Error al eliminar el usuario:", error);
    }
  }
}

function resetForm() {
  isEditing.value = false;
  currentUser.value = { id: null, name: '', email: '' };
}

onMounted(fetchUsers);
</script>

<style scoped>
.user-management {
  max-width: 800px;
  margin: 2rem auto;
  padding: 0 1rem;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

h1 {
  text-align: center;
  color: #2c3e50;
  margin-bottom: 2rem;
}

.user-form-card, .user-list-card {
  background: #fff;
  border-radius: 12px;
  padding: 2rem;
  margin-bottom: 2rem;
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
}

h2 {
  margin-top: 0;
  border-bottom: 1px solid #eee;
  padding-bottom: 0.5rem;
  margin-bottom: 1.5rem;
  color: #34495e;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 600;
  color: #555;
}

.form-group input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  box-sizing: border-box;
}

.form-actions {
  display: flex;
  gap: 1rem;
  margin-top: 1.5rem;
}

button {
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 600;
  transition: background-color 0.2s;
}

.btn-primary {
  background-color: #42b983;
  color: white;
}
.btn-primary:hover {
  background-color: #36a374;
}

.btn-secondary {
  background-color: #f0f2f5;
  color: #333;
}
.btn-secondary:hover {
  background-color: #e0e2e5;
}

.user-list-card ul {
  list-style: none;
  padding: 0;
}

.user-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border-bottom: 1px solid #eee;
}
.user-item:last-child {
  border-bottom: none;
}

.user-info {
  display: flex;
  flex-direction: column;
}

.user-name {
  font-weight: 600;
}

.user-email {
  color: #777;
  font-size: 0.9rem;
}

.user-actions {
  display: flex;
  gap: 0.5rem;
}

.btn-icon {
  background: none;
  border: none;
  font-size: 1.2rem;
  padding: 0.5rem;
  border-radius: 50%;
}
.btn-icon:hover {
  background-color: #f0f2f5;
}
</style>