<template>
  <main id="app">
    <UserManagement />
  </main>
</template>

<script setup>
import UserManagement from './components/UserManagement.vue';
</script>

<style>
body {
  background-color: #f0f2f5;
  margin: 0;
  color: #2c3e50;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>