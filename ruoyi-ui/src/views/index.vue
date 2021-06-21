<template>
  <div class="app-container home">
    <baidu-map class="bm-view" center="太谷县" ak="GRWnhWB3sn1ySFxRwkhXyxLc">
      <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation>
    </baidu-map>
  </div>
</template>

<script>
import BaiduMap from 'vue-baidu-map/components/map/Map.vue'
import BmNavigation from 'vue-baidu-map/components/controls/Navigation'

export default {
  name: "index",
  components: {
    BaiduMap, BmNavigation
  },
  data() {
    return {

    };
  },
  methods: {

  },
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
}

.bm-view {
  width: 100%;
  height: 600px;
}
</style>

