# ScrollLoad 表格的滚动加载

## 使用示例

```js

import ScrollLoad from 'scrollLoad';

const DEFAULT_PAGE=1;
export default {
  data() {
    return {
      tableData:[],
      ScrollLoad:null
    }
  },
  mounted() {

    this.ScrollLoad = new ScrollLoad(this.loadFun,{el:this.$refs.elTable.bodyWrapper, page:DEFAULT_PAGE});

  },
  methods(){
    resetSearch(){
      this.ScrollLoad.init();
    },
    loadFun(){
      const CURRENT_PAGE = this.ScrollLoad.page();
      ajax({page:CURRENT_PAGE}).then(res=>{
        this.ScrollLoad.maxPage(res.totalPages); //最大页码，控制滚动加载
        this.ScrollLoad.page(true); //下一页
        if (CURRENT_PAGE === 0) {
          this.tableData = res.list || [];
        } else {
          this.tableData = this.tableData.concat(res.list || []);
        }
      })
    },
  }
}
```
