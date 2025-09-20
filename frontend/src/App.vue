<template>
  <main id="app">
    <h1 class="main-title">Panel de Administración</h1>
    <ItemManagement />
    <hr class="main-separator" />
    <UserManagement />
  </main>
</template>

<script setup>
import UserManagement from './components/UserManagement.vue';
import ItemManagement from './components/ItemManagement.vue';
</script>

<style>
body {
  background-color: #f0f2f5;
  margin: 0;
  color: #2c3e50;
}
.main-title {
  text-align: center;
  color: #2c3e50;
  margin-bottom: 2rem;
  font-weight: 300;
  font-size: 2.5rem;
}
.main-separator {
  border: none;
  border-top: 2px solid #42b983;
  margin: 4rem auto;
  width: 50%;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>