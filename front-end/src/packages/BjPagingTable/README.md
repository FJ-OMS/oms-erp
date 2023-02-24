# BjPagingTable 简单的分页表格

## 使用方法

```js
   <BjPagingTable
      ref="BjPagingTable"
      :auto-fetch="false"
      :api="tableApi"
      :default-params="{code:'hhhhhhhhh'}"
    >
      <template v-slot:tableColumn>
        <el-table-column type="index" width="55" align="center" label="序号" />
        <el-table-column prop="categoryName" label="商品分类" />
      </template>
    </BjPagingTable>

```

```js
 data(){
   tableApi:commonApi.getLogPage, //必填
   defaultParams:{} //接口的默认参数，非必填
 }
```
