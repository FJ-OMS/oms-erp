# 规范

多行查询 /this\.cancel\(\{\n._\n._\)/

- 全局 element 组件已经默认 size="mini"
- 组件名称/文件 大写开头
- 声明变量必须加上 let 关键字.不要再使用 var
- const 常量 ，大写 如：`PAGE_LIST`
- 禁用、启用、删除等操作，需要弹窗确认
  ```js
    /** 禁用 */
    onStatus(flag, row) {
      const TEXT = STATUS_ENABLED_NUM.bjGet(flag);
      this.$confirm(`您正在 ${TEXT} ${row.brandName}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.operationApi({
            ids: [row.id],
            status: flag,
          });
        })
        .catch(() => {});
    },
    /** 删除操作 */
    onDelete(row) {
      this.$confirm(`您正在删除 ${row.brandName}，是否继续？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.batchDeleteApi({ ids: [row.id] });
        })
        .catch(() => {});
    },
  ```
- 列表的状态列，用`el-tag`展示
  ```html
  <el-tag v-else-if="col.prop === 'status'" :type="scope.row.status ? 'success' : 'info'"
    >{{ STATUS_ENABLED_NUM.bjGet(scope.row.status) }}</el-tag
  >
  ```
  - 新增/编辑的内容较少，使用`BjDrawerForm`弹窗展示，较多内容使用新页面
  - （新增/编辑等）页面的保存确认等按钮，放在右上角，可吸顶
  ```html
  <BjDivider right-fixed
    >基本信息
    <!--空标题使用<span></span>-->
    <template #right>
      <el-button @click="handleClose">返 回</el-button>
      <el-button type="primary" @click="onPass">确 认</el-button>
    </template>
  </BjDivider>
  ```
- 路由命名
  以父级路由为前缀，依次向后增长
  当前路由名称应与文件路径基本一致
  列表页面路由以 Index 结尾
  新增页面路由以 Add 结尾
  编辑页面路由以 Edit 结尾
  详情页面路由以 View 结尾
  配置页面路由以 Config 结尾
  路由长度默认为四级，前三级为模块菜单，第四级为页面和功能

# 通用样式

- `bj-label-colon` 表单 label 文字加冒号
- `full-width` 宽度 100%
- `tips` 提示文字，小号，灰色
- `link` 模拟链接，蓝色，可点击

# 组件

## 列表页面的公共混入

`import pageMiXin from '@/utils/page-mixin.js';`

## BjPage

路径 src\components\BjPage\index.vue
路径 src\components\BjPage\mixin.js

## 详情页面的公共混入

`import viewMiXin from '@/utils/view-mixin.js';`

## BjDialogList

## ScrollLoad

列表滚动加载 packages\ScrollLoad\README.md

## ScrollSelect

##

- BjImportFile 导入
- BjDialogList 通用弹窗选择：弹出一个列表，可以搜索
- BjDivider 分割线
- BjDrawerForm 右侧弹窗 【新建、编辑】类使用
- BjSelectLov 下拉选择
- BjCheckboxLov 复选
- BjRadioLov 单选
- BjRouteTabs tab 页的路由跳转头部
- BjInputMoney 金额 input
- ScrollSelect 支持 滚动加载、api 搜索 的下拉选择框，
- BjPagingTable 带分页的表格
- BjSelectProvinces dialog 弹窗选择中国的省/市
- BjInputNumber 数量 input 正整数
- BjSelectChinaArea 下拉选择中国的省/市/区 单独 all type="city" type="area"
- BjUpload 带缩略图的上传组件
- BjDialogTable

## 其它组件

1.  日期组件 `el-date-picker` 需要设置

- class="full-width => 宽度 100%
- value-format="yyyy-MM-dd"

2.  下拉选择 尽量使用 `BjSelectLov` 组件
    单选 尽量使用 `BjRadioLov` 组件
    复选 尽量使用 `BjCheckboxLov` 组件

3.  `input` 需要设置

- :maxlength="MAX_LENGTH.DEFAULT"
- clearable

4. `el-dialog` 需要设置

- :close-on-click-modal="false"
- :close-on-press-escape="false"

# 依赖 Node-sass 安装

不使用 Node-sass 会出现图标乱码
Node-sass 版本高于 5.0.0，与会构建失败
使用 原镜像 https://registry.npmjs.org/ 无法下载 5.0.0 以下的 Node-sass 版本

[来源知乎](https://zhuanlan.zhihu.com/p/147005226)

```npm
npm config set registry http://registry.npm.taobao.org/

npm config set disturl https://npm.taobao.org/mirrors/node/

npm config set sass_binary_site https://npm.taobao.org/mirrors/node-sass/

npm cache clean -f

npm install node-sass@1.14.1
```
