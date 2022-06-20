<template>
  <div class="Bj-endpoint" />
</template>
<script>
export default {
  props: {
    list: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  data() {
    return {};
  },
  mounted() {
    console.log(this.data);
    this.setEntpoint();
  },
  methods: {
    setEntpoint() {
        for (const key in this.list) {
            this.list[key].forEach(item => {
                const dom = document.getElementById(item.name);
                console.log(dom);
                if (dom) {
                    console.log(dom.offsetWidth);
                    console.log(dom.offsetHeight);
                }
            });
        }
    },
  },
};
</script>
<style lang="scss" scoped></style>
