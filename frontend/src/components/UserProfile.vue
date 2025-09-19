<template>
  <div class="user-profile" v-if="user">
    <div class="profile-header">
      <img :src="user.pictures[0].url" alt="Profile Picture" class="profile-picture">
      <div class="profile-info">
        <h1>{{ user.title }}</h1>
        <p class="subtitle">{{ user.subtitle }}</p>
      </div>
    </div>
    <div class="profile-body">
      <h2>Acerca de mí</h2>
      <p>{{ user.description }}</p>
      <h3>Detalles</h3>
      <ul>
        <li v-for="attr in user.attributes" :key="attr.id">
          <strong>{{ attr.name }}:</strong> {{ attr.value_name }}
        </li>
      </ul>
    </div>
  </div>
  <div v-else>
    <p>Cargando perfil de usuario...</p>
  </div>
</template>

<script setup>
defineProps({
  user: {
    type: Object,
    required: true
  }
});
</script>

<style scoped>
.user-profile {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
  max-width: 800px;
  margin: 2rem auto;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 8px 24px rgba(0,0,0,0.1);
  background-color: #ffffff;
  text-align: left;
}

.profile-header {
  display: flex;
  align-items: center;
  margin-bottom: 2rem;
  padding-bottom: 2rem;
  border-bottom: 1px solid #e0e0e0;
}

.profile-picture {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 2rem;
  border: 4px solid #f0f2f5;
}

.profile-info h1 {
  margin: 0;
  font-size: 2.25rem;
  color: #2c3e50;
}

.profile-info .subtitle {
  margin: 0.25rem 0 0;
  font-size: 1.2rem;
  color: #555;
  font-weight: 300;
}

.profile-body p {
  line-height: 1.7;
  color: #333;
}

.profile-body ul {
  list-style: none;
  padding: 0;
  margin-top: 1rem;
}

.profile-body li {
  background-color: #f9f9f9;
  padding: 0.8rem 1rem;
  border-radius: 6px;
  margin-bottom: 0.5rem;
  border-left: 3px solid #42b983;
}
</style>