# BjLogTable 日志展示表格组件

## 使用方法

### 直接传入数据展示

```html
<BjLogTable :table-data="info.logList" />
```

### 从接口获取数据

```html
<BjLogTable :table-api="api" :default-params="defaultParams" />
```

```js
 data(){
   api:commonApi.getLogPage, //必填
   defaultParams:{} //接口的默认参数，非必填
 }
```
