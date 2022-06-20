<template>
  <div id="workspace" class="app-container">
    <div class="header border">
      <div class="aside-l"><span class="text">渠道仓</span></div>
      <div class="aside-r flex">
        <el-button
          v-for="item in list.trench"
          :id="'trench' + item.id"
          :key="item.id"
          :data-id="item.id"
          size="medium"
          >{{ item.title }}</el-button
        >
      </div>
    </div>
    <div class="main border">
      <div class="aside-l">
        <div draggable @dragstart="onDragstart($event, 'virtual', 'success')">
          <el-button size="medium" type="success">中转虚仓</el-button>
        </div>
        <div draggable @dragstart="onDragstart($event, 'virtual', 'primary')">
          <el-button size="medium" type="primary">数量虚仓</el-button>
        </div>
        <div draggable @dragstart="onDragstart($event, 'virtual', 'warning')">
          <el-button size="medium" type="warning">比例虚仓</el-button>
        </div>
        <div draggable @dragstart="onDragstart($event, 'entity', 'danger')">
          <el-button size="medium" type="danger">实仓</el-button>
        </div>
      </div>
      <div class="aside-r">
        <div id="virtual" ref="virtual" class="virtual">
          <div
            v-for="(item, index) in list.virtual"
            :id="'virtual' + item.id"
            :key="item.id"
            class="virtual-item"
            :style="{ left: item.x + 'px', top: item.y + 'px' }"
            @dblclick="edit(1, item, index)"
          >
            <el-button size="medium" :type="item.type">
              {{ item.title ? item.title : '未命名虚仓' }}
            </el-button>
          </div>
        </div>
        <div ref="entity" class="entity">
          <div
            v-for="(item, index) in list.entity"
            :id="'entity' + item.id"
            :key="item.id"
            class="entity-item"
            :style="{ left: item.x + 'px', top: item.y + 'px' }"
            @dblclick="edit(2, item, index)"
          >
            <el-button size="medium" type="danger">
              {{ item.title ? item.title : '未命名虚仓' }}
            </el-button>
          </div>
        </div>
      </div>
    </div>
    <div class="footer border">
      <div class="aside-l"><span class="text">逻辑仓</span></div>
      <div class="aside-r flex">
        <el-button
          v-for="item in list.logic"
          :id="'logic' + item.id"
          :key="item.id"
          size="medium"
          >{{ item.title }}</el-button
        >
      </div>
    </div>
    <div
      v-show="drag.show"
      style="position: fixed; z-index: 9999"
      :style="{ left: drag.x + 'px', top: drag.y + 'px' }"
    >
      <el-button :type="drag.color" size="medium" class="hideButton">{{ drag.title }}</el-button>
    </div>

    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :title="title"
      :visible.sync="dialogVisible"
      width="400px"
    >
      <div>
        <el-input v-model="form.title" :maxlength="MAX_LENGTH.DEFAULT" placeholder="请输入" />
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="del">删除该节点</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :title="title"
      :visible.sync="dialogVisibleLine"
      width="400px"
    >
      <div>
        <el-input
          v-model="labelData.labelName"
          :maxlength="MAX_LENGTH.DEFAULT"
          placeholder="请输入"
        />
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delLine">删除该连线</el-button>
        <el-button type="primary" @click="confirmLine">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      list: {
        // 渠道
        trench: [],
        // 逻辑仓
        logic: [],
        // 虚仓
        virtual: [],
        // 实仓
        entity: [],
      },
      /**
       * relations：连线数据
       * source：起点
       * target：终点
       * anchor：[[起点锚],[终点锚]] [[sourceX,sourceY],[targetX,targetY]]
       * 备注：Top = [0.5, 0] Bottom = [0.5, 1] Left = [0, 0.5] Right: [1, 0.5]
       * 相关文档：https://docs.jsplumbtoolkit.com/toolkit/current/articles/anchors.html
       */
      relations: [],
      /** 隐藏拖动框数据 */
      drag: {
        type: '',
        show: false,
        title: '',
        x: 0,
        y: 0,
        width: 0,
        height: 0,
        color: '',
      },
      minX: 0,
      minY: 0,
      maxX: 0,
      maxY: 0,
      dialogVisible: false,
      dialogVisibleLine: false,
      title: '',
      form: {},
      labelData: {
        labelName: '',
        labelIndex: -1,
        connect: {},
      },
    };
  },
  created() {
    const data = {
      trench: [
        { id: 0, title: '渠道仓1', name: 'trench0', drag: false },
        { id: 1, title: '渠道仓2', name: 'trench1', drag: false },
      ],
      logic: [{ id: 7, title: '逻辑仓', name: 'logic7', drag: false }],
      virtual: [
        { id: 4, title: '中转虚仓', name: 'virtual4', drag: true, x: 50, y: 22, type: 'success' },
        {
          id: 5,
          title: '数量虚仓',
          name: 'virtual5',
          drag: true,
          x: 300,
          y: 22,
          type: 'primary',
        },
        {
          id: 8,
          title: '比例虚仓',
          name: 'virtual8',
          drag: true,
          x: 620,
          y: 22,
          type: 'warning',
        },
      ],
      entity: [
        { id: 6, title: '实仓', name: 'entity6', drag: true, x: 0, y: 0 },
        { id: 7, title: '实仓', name: 'entity7', drag: true, x: 220, y: 0 },
      ],
    };
    this.relations = [
      {
        source: 'trench0',
        target: 'virtual4',
        anchor: [
          [0.5, 1],
          [0.5, 0],
        ],
        label: { id: 'label1', name: '40%' },
      },
      {
        source: 'trench0',
        target: 'virtual5',
        anchor: [
          [0.5, 1],
          [0.5, 0],
        ],
        label: { id: 'label2', name: '40%' },
      },
      {
        source: 'trench1',
        target: 'virtual5',
        anchor: [
          [0.5, 1],
          [0.5, 0],
        ],
        label: { id: 'label3', name: '60%' },
      },
    ];
    this.list = data;
  },
  mounted() {
    window.addEventListener('mousemove', this.handleKeymove);
    window.addEventListener('mouseup', this.handleKeyup);
    window.addEventListener('resize', () => {
      // 重绘线条和端点
      this.jsPlumb.repaintEverything();
    });
    // jsPlumb默认配置
    const jsplumbSetting = {
      Container: 'workspace', // 选择器id
      EndpointStyle: { radius: 4, fill: '#acd' }, // 端点样式
      PaintStyle: { stroke: '#ccc', strokeWidth: 2 }, // 绘画样式，默认8px线宽  #456
      HoverPaintStyle: { stroke: '#1E90FF' }, // 默认悬停样式  默认为null
      EndpointHoverStyle: { fill: '#409EFF', radius: 6 }, // 端点悬停样式
      ConnectionOverlays: [
        //   // 连线上的默认样式  这里是箭头
        //   ['Arrow', { location: 0.9, width: 8, length: 8, paintStyle: { stroke: '#00688B', fill: '#00688B' }}],
        // [
        //   'Label',
        //   {
        //     // location: 0.1,
        //     id: 'label',
        //     cssClass: 'aLabel', // hover时label的样式名
        //     events: {
        //       tap: function() {},
        //     },
        //     visible: true,
        //   },
        // ],
      ],
      Connector: ['Straight', { gap: 1 }], // 要使用的默认连接器的类型：折线，流程等
      // ConnectionsDetachable: false,
      // isTarget: true, // 是否可以放置（连线终点）
      maxConnections: -1, // 设置连接点最多可以连接几条线
      DragOptions: { cursor: 'pointer', zIndex: 2000 },
      RenderMode: 'svg', // 默认渲染模式
      Scope: 'jsPlumb_DefaultScope', // 范围，具有相同scope的点才可连接
      reattach: true,
    };
    // 导入默认配置
    this.jsPlumb = this.$jsPlumb;
    this.jsPlumb.importDefaults(jsplumbSetting);
    this.$nextTick(() => {
      this.init();
    });
  },
  destroyed() {
    window.removeEventListener('mousemove', this.handleKeymove);
    window.removeEventListener('mouseup', this.handleKeyup);
    // 删除所有的端点
    this.jsPlumb.deleteEveryEndpoint();
  },
  methods: {
    /**
     * 编辑仓储
     * item：仓储数据
     * index：数据下标（用于修改/删除数组）
     */
    edit(status, item, index) {
      this.title = '编辑仓储';
      this.form = item;
      this.form.status = status;
      this.form.index = index;
      this.dialogVisible = true;
    },
    del() {
      this.$confirm('即将删除该节点，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          const { status, index } = this.form;
          // 删除虚仓
          if (status === 1) this.list.virtual.splice(index, 1);
          // 删除实仓
          if (status === 2) this.list.entity.splice(index, 1);
          /** 删除节点 */
          this.jsPlumb.remove(this.form.name);
          this.dialogVisible = false;
        })
        .catch(() => {});
    },
    // 编辑连接器
    editLine() {},
    /** 删除连接器 */
    delLine() {
      this.relations.splice(this.labelData.labelIndex, 1);
      this.jsPlumb.deleteConnection(this.labelData.connect);
      this.dialogVisibleLine = false;
      this.msgSuccess('删除成功');
    },
    /** 保存连线数据 */
    confirmLine() {},
    onDragstart(to, from, color) {
      this.drag.show = true;
      this.minX = this.$refs[from].getBoundingClientRect().left;
      this.minY = this.$refs[from].getBoundingClientRect().top;
      this.maxX = this.minX + this.$refs[from].clientWidth - to.target.clientWidth;
      this.maxY = this.minY + this.$refs[from].clientHeight - to.target.clientHeight;
      this.drag.type = from;
      this.drag.title = to.target.innerText;
      this.drag.width = to.target.clientWidth;
      this.drag.height = to.target.clientHeight;
      this.drag.color = color;
    },
    handleKeymove(e) {
      // 元素跟随鼠标
      this.drag.x = e.x - 99.5;
      this.drag.y = e.y - 18;
    },
    handleKeyup(e) {
      if (this.drag.show) {
        this.drag.show = false;
        if (this.drag.x < this.minX || this.drag.y < this.minY) return;
        if (this.drag.x > this.maxX || this.drag.y > this.maxY) return;
        const x = e.x - this.drag.width / 2 - this.minX;
        const y = e.y - this.drag.height / 2 - this.minY;
        // 获取新增仓库的最后一个数据的id + 1
        const id = this.list[this.drag.type][this.list[this.drag.type].length - 1].id + 1;
        // console.log(id);
        // 生成数据
        const obj = {
          id: id,
          title: this.drag.title,
          name: this.drag.type + id,
          x: x,
          y: y,
          type: this.drag.color,
        };
        // 添加拖拽数据
        this.list[this.drag.type].push(obj);
        this.$nextTick(() => {
          // 虚仓添加左右端点
          if (this.drag.type === 'virtual') {
            this.addEndpoint(obj.name, 'Left');
            this.addEndpoint(obj.name, 'Right');
          }
          this.addEndpoint(obj.name, 'Top');
          this.addEndpoint(obj.name, 'Bottom');

          /** 使元素具有拖拽功能 */
          this.jsPlumb.draggable(obj.name, {
            // 父元素的id
            containment: this.drag.type,
            /** 元素拖拽结束回调 */
            stop: el => {
              /** 保存元素坐标 */
              obj.x = el.pos[0];
              obj.y = el.pos[1];
            },
          });
        });
      }
    },
    /**
     * 注册jsPlumb
     *
     */
    init() {
      this.redraw();
    },
    /** 绘制数据 */
    redraw() {
      this.jsPlumb.ready(() => {
        // 导入默认配置
        // this.jsPlumb.importDefaults();
        // 会使整个jsPlumb立即重绘。
        // this.jsPlumb.setSuspendDrawing(false, true);
        for (const key in this.list) {
          this.list[key].forEach((item, index) => {
            if (item.drag) {
              /** 使元素具有拖拽功能 */
              this.jsPlumb.draggable(key + item.id, {
                containment: key,
                /** 元素拖拽结束回调 */
                stop: el => {
                  /** 保存元素坐标 */
                  item.x = el.pos[0];
                  item.y = el.pos[1];
                },
              });
            }
            // /** 渲染拖拽端点 */
            if (key !== 'trench') {
              this.addEndpoint(key + item.id, 'Top');
            }
            if (key !== 'logic') {
              this.addEndpoint(key + item.id, 'Bottom');
            }
            // 虚仓渲染左右端点
            if (key === 'virtual') {
              this.addEndpoint(key + item.id, 'Left');
              this.addEndpoint(key + item.id, 'Right');
            }
          });
        }
        this.jsPlumb.unbind('connection'); // 取消连接事件
        /** 渲染连线 */
        for (let i = 0; i < this.relations.length; i++) {
          const line = this.relations[i];
          this.jsPlumb.connect({
            // uuids: [line.source, line.target],
            // 起点
            source: line.source,
            // 终点
            target: line.target,
            anchor: line.anchor,
            // overlays: [['Label', { label: line.label.name, id: line.label.id }]],
          });
        }
        // 一般来说拖动创建的链接，可以再次拖动，让链接断开。如果不想触发这种行为，可以设置。
        this.jsPlumb.importDefaults({
          ConnectionsDetachable: false,
        });
      });

      // 端点连线前
      this.jsPlumb.bind('beforeDrop', info => {
        console.log('端点连线前');
        // 判断是否数据重复
        const boolean = this.relations.some(
          item =>
            info.sourceId + info.targetId === item.source + item.target ||
            info.sourceId + info.targetId === item.target + item.source,
        );
        if (boolean) return false;
        if (info.sourceId === info.targetId) return false;
        const source = info.sourceId.replace(/\d/gi, '');
        const target = info.targetId.replace(/\d/gi, '');
        // 判断是否同级，同级不连接, 虚仓可以同级连接
        if (source !== 'virtual' && target !== 'virtual' && source === target) return false;
        // 添加数据
        // 起点锚x坐标
        const sourceX = info.connection.endpoints[0].anchor.anchors[0].x;
        // 起点锚y坐标
        const sourceY = info.connection.endpoints[0].anchor.anchors[0].y;
        // 终点锚x坐标
        const targetX = info.dropEndpoint.anchor.anchors[0].x;
        // 终点锚y坐标
        const targetY = info.dropEndpoint.anchor.anchors[0].y;
        const data = {
          source: info.sourceId,
          target: info.targetId,
          anchor: [
            [sourceX, sourceY],
            [targetX, targetY],
          ],
        };
        this.relations.push(data);
        console.log('连接成功');
        return true;
      });
      // 改变线的连接节点
      this.jsPlumb.bind('connectionMoved', info => {
        console.log('改变线的连接节点');
      });
      // 连线
      this.jsPlumb.bind('connection', info => {
        console.log('连线');
        console.log(info);
      });
      // 右键
      this.jsPlumb.bind('contextmenu', info => {
        console.log('右键');
      });
      // 删除连线
      this.jsPlumb.bind('connectionDetached', info => {
        console.log('删除连线');
      });
      // 删除连线事件
      this.jsPlumb.bind('click', (conn, originalEvent) => {
        const index = this.relations.findIndex((value, keys, arr) => {
          return value.source + value.target === conn.sourceId + conn.targetId;
        });
        if (index !== -1) {
          // 获取修改连接索引
          this.labelData.labelIndex = index;
          this.labelData.connect = conn;
          // console.log(this.relations[index].label.name);
          this.labelData.labelName = this.relations[index].label.name;
        }
        this.title = '编辑连线';
        this.dialogVisibleLine = true;
        // this.$confirm('是否删除该连接？', '提示', {
        //   confirmButtonText: '确定',
        //   cancelButtonText: '取消',
        //   type: 'warning',
        // })
        //   .then(() => {
        //     this.$message({
        //       type: 'success',
        //       message: '删除成功!',
        //     });
        //     // 获取删除数据的索引
        //     const index = this.relations.findIndex((value, keys, arr) => {
        //       return value.source + value.target === conn.sourceId + conn.targetId;
        //     });
        //     if (index !== -1) {
        //       // 删除连线
        //       this.relations.splice(index, 1);
        //       this.jsPlumb.deleteConnection(conn);
        //     }
        //   })
        //   .catch(() => {});
      });
    },
    /**
     * 添加仓库端点
     * id:元素id,
     * palce:添加位置
     */
    addEndpoint(id, place) {
      // 如果你将isSource和isTarget设置成true，那么久可以用户在拖动时，自动创建链接。
      const common = {
        isSource: true,
        isTarget: true,
        connector: ['Straight'],
        maxConnections: -1,
        uuid: id,
      };
      this.jsPlumb.addEndpoint(
        id,
        {
          anchors: [place],
        },
        common,
      );
      this.jsPlumb.importDefaults({
        ConnectionsDetachable: false,
      });
    },
  },
};
</script>

<style lang="scss" scoped>
$btnW: 200px;
#workspace {
  position: relative;
}
.app-container {
  height: calc(100vh - 84px);
}
.border {
  border: 1px solid #ebeef5;
  box-sizing: border-box;
}
.header {
  display: flex;
  height: 100px;
  border-bottom: 0;
}
.main {
  display: flex;
  height: calc(100% - 200px);
}
.footer {
  display: flex;
  height: 100px;
  border-top: 0;
  margin: 0;
}
.flex {
  display: flex;
  align-items: center;
  justify-content: center;
}
.aside-l {
  display: table;
  width: 200px;
  height: 100%;
  text-align: center;
  border-right: 1px solid #ebeef5;
  moz-user-select: -moz-none;
  -moz-user-select: none;
  -o-user-select: none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  -ms-user-select: none;
  user-select: none;
  .text {
    display: table-cell;
    vertical-align: middle;
  }
  .el-button {
    width: $btnW;
  }
}
.aside-r {
  width: calc(100% - 200px);
  overflow: auto;
  .dragBox {
    box-sizing: border-box;
  }
  .virtual {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 200px;
    border-bottom: 1px solid #ebeef5;
    .virtual-item {
      position: absolute;
      .el-button {
        width: 200px;
        position: relative;
        z-index: -1;
      }
    }
  }
  .entity {
    position: relative;
    height: 200px;
    border-bottom: 1px solid #ebeef5;
    .entity-item {
      position: absolute;
      .el-button {
        width: 200px;
        position: relative;
        z-index: -1;
      }
    }
  }
  .logic {
    position: relative;
    height: 100px;
    border-bottom: 1px solid #ebeef5;
  }
  .el-button {
    width: $btnW;
  }
  .el-button + .el-button {
    margin-left: 30px;
  }
}
.hideButton {
  width: $btnW;
}
::v-deep .jtk-connector {
  // border:1px solid #ccc;
}
</style>
