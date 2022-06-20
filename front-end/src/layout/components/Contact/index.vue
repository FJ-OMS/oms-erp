<template>
  <ul class="bj-contact">
    <li
      v-for="(item, index) in contactData"
      :key="index"
      class="box"
      @mouseover="mouseOver(item)"
      @mouseleave="mouseLeave(item)"
    >
      <BjKeyImage :url="item.url" style="width: 24px;" />
      <p>{{ item.text }}</p>
      <BjKeyImage v-if="index === 0" class="content wx" :url="require('@/assets/image/wx.png')" />
      <div v-if="index === 1" class="content phone">159 5912 2386</div>
    </li>
  </ul>
</template>

<script>
export default {
  data() {
    return {
      contactData: [
        {
          url: require('@/assets/image/contact-wx.png'),
          name: 'wx',
          text: '微信咨询',
        },
        {
          url: require('@/assets/image/contact-phone.png'),
          name: 'phone',
          text: '电话咨询',
        },
      ],
    };
  },
  methods: {
    // 移入
    mouseOver(item) {
      item.url = require(`@/assets/image/contact-${item.name}-white.png`);
    },
    // 移出
    mouseLeave(item) {
      item.url = require(`@/assets/image/contact-${item.name}.png`);
    },
  },
};
</script>

<style lang="scss" scoped>
.bj-contact {
  position: fixed;
  left: 0;
  top: 70%;
  z-index: 2002;
  list-style: none;
  background-color: white;
  margin: 0;
  padding: 0;
  box-shadow: 0 4px 10px 0 rgba(225, 225, 225, 0.5);
  .box {
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 60px;
    height: 60px;
    border-radius: 4px;
    border-radius: 0;
    cursor: pointer;
    transition: all 0.3s;
    &:hover {
      background-color: $main-dark;
    }
    &:hover p {
      color: white;
    }
    p {
      margin: 0;
      font-size: 12px;
      margin-top: 6px;
      color: $main-dark;
    }
    .content {
      position: absolute;
      z-index: 9;
      left: 60px;
      width: 0;
    }
    .wx {
      height: 90px;
    }
    .phone {
      background-color: $main-dark;
      height: 60px;
      color: white;
      text-align: center;
      overflow: hidden;
      line-height: 60px;
      transition: all 0.2s;
    }
    &:hover .wx {
      width: 90px;
      background: none;
    }
    &:hover .phone {
      width: 150px;
    }
  }
}
</style>
