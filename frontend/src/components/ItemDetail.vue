<template>
  <div class="container" v-if="item">
    <div class="left">
      <img :src="item.images[0]" class="big" alt="product" />
      <div class="thumbs">
        <img v-for="(img,idx) in item.images" :key="idx" :src="img" class="thumb" />
      </div>
    </div>

    <div class="right">
      <h1 class="title">{{ item.title }}</h1>
      <div class="price-section">
        <div class="price">${{ formatPrice(item.discountedPrice) }}</div>
        <div v-if="item.discountedPrice < item.price" class="orig">Antes ${{ formatPrice(item.price) }}</div>
      </div>
      <div class="seller">Vendido por <strong>{{ item.seller }}</strong></div>
      <div class="stock">Stock: {{ item.stock }}</div>
      <div class="desc">
        <h3>Descripción</h3>
        <p>{{ item.description }}</p>
      </div>
      <div class="payments">
        <h4>Medios de pago</h4>
        <ul>
          <li>Tarjeta de crédito</li>
          <li>MercadoPago</li>
          <li>Pago en cuotas</li>
        </ul>
      </div>
      <button class="buy">Comprar</button>
    </div>
  </div>

  <div v-else>Loading...</div>
</template>

<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';

export default {
  name: 'ItemDetail',
  props: { id: { type: String, required: true } },
  setup(props) {
    const item = ref(null);
    const load = async () => {
      try {
        // optional discount query param for testing
        const res = await axios.get(`/api/items/${props.id}?discount=10`);
        item.value = res.data;
      } catch(e) { console.error(e); }
    };
    onMounted(load);
    const formatPrice = p => Number(p).toLocaleString();
    return { item, formatPrice };
  }
}
</script>

<style scoped>
.container{ display:flex; gap:24px; margin:24px; font-family: Arial; }
.left{ flex: 1; }
.right{ flex: 1; background: #fff; padding: 20px; border-radius: 6px; }
.big{ width:100%; border-radius:6px; box-shadow:0 1px 6px rgba(0,0,0,0.1); }
.thumbs{ display:flex; gap:8px; margin-top:8px; }
.thumb{ width:70px; height:70px; object-fit:cover; border-radius:4px; }
.title{ font-size:22px; color:#222; }
.price-section{ margin-top:12px; display:flex; gap:12px; align-items:baseline; }
.price{ font-size:28px; color:#3483fa; font-weight:700; }
.orig{ text-decoration:line-through; color:#888; font-size:14px; }
.buy{ margin-top:18px; background:#ffd300; border:none; padding:12px 20px; font-weight:700; border-radius:6px; cursor:pointer; }
@media (max-width:800px){ .container{ flex-direction:column; } }
</style>