## 说明

这是 业务中心 - 商品 模块 的组件，仅在 此模块 使用。

### GoodsAttribute.vue  属性组件
  编辑属性时，添加`isEdit`   
  ` <GoodsAttribute isEdit @confirm="handleConfirm"  />`
  ```js
  methods:{
    handleConfirm(data){
      console.log(data)
    }
  }
  ```