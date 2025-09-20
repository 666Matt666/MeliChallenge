<template>
  <div class="item-management">
    <h1>Gestión de Items</h1>
    <hr class="separator" />

    <div class="item-form-card">
      <h2>{{ isEditing ? 'Editar Item' : 'Agregar Item' }}</h2>
      <form @submit.prevent="saveItem">
        <div class="form-group">
          <label for="title">Título:</label>
          <input id="title" v-model="currentItem.title" placeholder="Título del item" required />
        </div>
        <div class="form-group">
          <label for="price">Precio:</label>
          <input id="price" type="number" v-model="currentItem.price" placeholder="0.00" required />
        </div>
        <div class="form-actions">
          <button type="submit" class="btn-primary">{{ isEditing ? 'Actualizar' : 'Guardar' }}</button>
          <button type="button" @click="resetForm" class="btn-secondary">Cancelar</button>
        </div>
      </form>
    </div>

    <div class="item-list-card">
      <h2>Lista de Items</h2>
      <ul v-if="items.length">
        <li v-for="item in items" :key="item.id" class="item-item">
          <div class="item-info">
            <span class="item-title">{{ item.title }}</span>
            <div class="price-container">
              <span
                :class="{ 'original-price': item.discountedPrice < item.price }"
              >
                {{ formatCurrency(item.price) }}
              </span>
              <span v-if="item.discountedPrice < item.price" class="discounted-price">
                {{ formatCurrency(item.discountedPrice) }}
              </span>
            </div>
          </div>
          <div class="item-actions">
            <div class="discount-apply-group">
              <input
                type="number"
                v-model.number="item.discountInput"
                placeholder="%"
                min="0"
                max="100"
                class="discount-input"
              />
              <button @click="applyDiscountToItem(item)" class="btn-apply-discount">Aplicar</button>
            </div>
            <button @click="editItem(item)" class="btn-icon btn-edit" title="Editar">✏️</button>
            <button @click="deleteItem(item.id)" class="btn-icon btn-delete" title="Eliminar">🗑️</button>
          </div>
        </li>
      </ul>
      <p v-else>No hay items para mostrar.</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const items = ref([]);
const isEditing = ref(false);
const currentItem = ref({
  id: null,
  title: '',
  price: null,
});

const API_URL = '/api/items';

async function fetchItems() {
  try {
    const response = await axios.get(API_URL);
    // Añadimos la propiedad `discountInput` a cada item para el v-model del input
    items.value = response.data.map(item => ({
      ...item,
      discountInput: null
    }));
  } catch (error) {
    console.error("Error al obtener items:", error);
  }
}

async function saveItem() {
  try {
    if (isEditing.value) {
      await axios.put(`${API_URL}/${currentItem.value.id}`, currentItem.value);
    } else {
      await axios.post(API_URL, currentItem.value);
    }
    resetForm();
    await fetchItems();
  } catch (error) {
    console.error("Error al guardar el item:", error);
  }
}

function editItem(item) {
  isEditing.value = true;
  currentItem.value = { ...item };
}

async function deleteItem(id) {
  if (confirm('¿Estás seguro de que quieres eliminar este item?')) {
    try {
      await axios.delete(`${API_URL}/${id}`);
      await fetchItems();
    } catch (error) {
      console.error("Error al eliminar el item:", error);
    }
  }
}

function resetForm() {
  isEditing.value = false;
  currentItem.value = { id: null, title: '', price: null };
}

async function applyDiscountToItem(item) {
  const discount = item.discountInput;
  if (discount == null || discount < 0 || discount > 100) {
    alert('Por favor, ingrese un descuento válido entre 0 y 100.');
    return;
  }

  try {
    const response = await axios.get(`${API_URL}/${item.id}?discount=${discount}`);
    const updatedItem = response.data;

    // Buscamos el item en la lista y lo actualizamos para reflejar el descuento
    const index = items.value.findIndex(i => i.id === updatedItem.id);
    if (index !== -1) {
      items.value[index] = { ...updatedItem, discountInput: item.discountInput };
    }
  } catch (error) {
    console.error(`Error al aplicar descuento al item ${item.id}:`, error);
  }
}

function formatCurrency(value) {
    if (value == null) return '';
    return new Intl.NumberFormat('es-AR', { style: 'currency', currency: 'ARS' }).format(value);
}

onMounted(fetchItems);
</script>

<style scoped>
.item-management {
  max-width: 800px;
  margin: 2rem auto;
  padding: 0 1rem;
}
.separator {
  border: none;
  border-top: 1px solid #e0e0e0;
  margin: 2rem 0;
}
.item-form-card, .item-list-card, .discount-card {
  background: #fff;
  border-radius: 12px;
  padding: 2rem;
  margin-bottom: 2rem;
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
}

.item-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border-bottom: 1px solid #eee;
}
.item-item:last-child {
  border-bottom: none;
}

.item-info {
  display: flex;
  flex-direction: column;
}

.price-container {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  margin-top: 0.25rem;
}

.original-price {
  text-decoration: line-through;
  color: #999;
  font-size: 0.85rem;
}

.item-title {
  font-weight: 600;
}

.item-price {
  color: #777;
  font-size: 0.9rem;
}

.item-actions {
  display: flex;
  gap: 0.5rem;
  align-items: center;
}

.discount-apply-group {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-right: 0.5rem;
}

.discount-input {
  width: 45px;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  text-align: center;
}

.btn-apply-discount {
  padding: 0.5rem 0.75rem;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>
