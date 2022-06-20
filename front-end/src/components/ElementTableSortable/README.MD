# ElementTableColumnSortable

## 作用

对 element 表格列的以下自定义配置 进行用户级保存

1. 拖拽排序
2. 拖拽宽度
3. 固定左/右

## 使用

### npm

```
npm i element-table-sortable
```

### main.js 引用

```js
import ElementTableSortable from 'element-table-sortable';
Vue.use(ElementTableSortable);
```

### temp.vue

```html
<el-button @click="SortableTable.show()">打开设置面板</el-button>

<el-table
  @header-dragend="SortableTable.handleHeaderDragend"
  @header-contextmenu="SortableTable.handleHeaderContextmenu"
>
  <template v-for="(col, index) in dropCols">
    <el-table-column
      :key="`col_${index}_${col.prop}_${col.width || 'auto'}`"
      :prop="col.prop"
      :label="col.label"
      :width="col.width || 'auto'"
      :fixed="col.fixed || false"
      class-name="drag-element"
    >
      <template slot-scope="scope">
        <template v-if="col.prop === 'action'">
          <el-button>查看</el-button>
        </template>
        <span v-else>{{ scope.row[col.prop] }}</span>
      </template>
    </el-table-column>
  </template>
</el-table>
```

```js
export default {
  data() {
    return {
      /*** 拖拽自定义表头 */
      SortableTable: {
        show: () => {},
        handleHeaderDragend: () => {},
        handleHeaderContextmenu: () => {},
      },
      defaultLabels: [
        { label: 'aaaa', prop: 'a', isCheck: true, disabled: false },
        { label: 'bbb', prop: 'b', isCheck: true, disabled: false },
        { label: '操作', prop: 'action', isCheck: true, disabled: true, width: 129 },
      ],
      dropCols: [],
    };
  },
  mounted() {
    /** 拖拽自定义表头 */
    this.SortableTable = this.$initElementTableSortable({
      el: '.el-table__header-wrapper tr', //拖拽handle的父容器
      // dragElement: String, //非必填，默认为".draggable"，拖拽识别handle
      defaultLabels: this.defaultLabels,
      // startIndex: Number, //非必填，默认为0，计算拖拽的起始index；如果表格第1列是不拖拽的selection列，startIndex=1
      sortableTableCode: this.$options.name, //一个自定义CODE，用于记录配置json的key
      // setRemoteApi: Promise, // 非必填，入参为{code:sortableTableCode,tabulationTitleDTOS:newLabelsArray},newLabelsArray是defaultLabels重排后，各数组内对象加上{sort:index}的数组
      // getRemoteApi: Promise,// 非必填，返回类defaultLabels的数据
      handleColumn: this.handleColumn, //返回处理后的表配置
    });
  },
  methods: {
    handleColumn(data) {
      this.dropCols = data;
    },
  },
};
```
