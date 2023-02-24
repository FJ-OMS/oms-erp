/*
 * 商品中心
 */

import EmptyRouter from '@/layout/EmptyRouter.vue';
import EmptyRouterAlive from '@/layout/EmptyRouterAlive.vue';
// import Test0Empty from '@/views/test/empty.vue';
export default {
  path: 'goods',
  name: 'BusinessGoods',
  component: EmptyRouterAlive,
  meta: {
    title: '商品管理',
  },
  children: [
    /**  **** 商品管理 ****  */
    {
      path: 'spu',
      name: 'BusinessGoodsSpu',
      component: EmptyRouter,
      redirect: 'spu/ordinary',
      meta: {
        title: '商品管理',
      },
      children: [
        {
          path: 'ordinary',
          name: 'BusinessGoodsSpuOrdinaryIndex',
          component: () => import('@/views/center-business/goods/spu/ordinary'),
          meta: {
            title: '普通商品管理',
          },
          permissionType: 'tab',
        },
        {
          path: 'ordinary/view',
          name: 'BusinessGoodsSpuOrdinaryView',
          component: () => import('@/views/center-business/goods/spu/ordinary/view'),
          meta: {
            title: '普通商品详情',
          },
          hidden: true,
        },
        {
          path: 'ordinary/add',
          name: 'BusinessGoodsSpuOrdinaryAdd',
          component: () => import('@/views/center-business/goods/spu/ordinary/add'),
          meta: {
            title: '添加普通商品',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: 'ordinary/edit',
          name: 'BusinessGoodsSpuOrdinaryEdit',
          component: () => import('@/views/center-business/goods/spu/ordinary/edit'),
          meta: {
            title: '编辑普通商品',
          },
          hidden: true,
        },
        /** 按钮权限start */
        {
          path: '',
          name: 'BusinessGoodsSpuOrdinary_ImportData',
          meta: { title: '普通商品-导入数据' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsSpuOrdinary_DownloadTemplate',
          meta: { title: '普通商品-下载模板' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsSpuOrdinary_Delete',
          meta: { title: '普通商品-删除商品' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsSpuOrdinary_Status',
          meta: { title: '普通商品-启用/禁用' },
          hidden: true,
        },
        /** 按钮权限end */
        {
          path: 'package',
          name: 'BusinessGoodsSpuPackageIndex',
          component: () => import('@/views/center-business/goods/spu/package'),
          meta: {
            title: '套餐商品管理',
          },
          permissionType: 'tab',
        },
        {
          path: 'package/view',
          name: 'BusinessGoodsSpuPackageView',
          component: () => import('@/views/center-business/goods/spu/package/view'),
          meta: {
            title: '套餐商品详情',
          },
          hidden: true,
        },
        {
          path: 'package/add',
          name: 'BusinessGoodsSpuPackageAdd',
          component: () => import('@/views/center-business/goods/spu/package/add'),
          meta: {
            title: '添加套餐商品',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: 'package/edit',
          name: 'BusinessGoodsSpuPackageEdit',
          component: () => import('@/views/center-business/goods/spu/package/edit'),
          meta: {
            title: '编辑套餐商品',
          },
          hidden: true,
        },
        /** 按钮权限start */
        {
          path: '',
          name: 'BusinessGoodsSpuPackage_ImportData',
          meta: { title: '套餐商品-导入数据' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsSpuPackage_DownloadTemplate',
          meta: { title: '套餐商品-下载模板' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsSpuPackage_Delete',
          meta: { title: '套餐商品-删除商品' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsSpuPackage_Status',
          meta: { title: '套餐商品-启用/禁用' },
          hidden: true,
        },
        /** 按钮权限end */
      ],
    },
    /**  **** SKU管理 ****  */
    {
      path: 'sku',
      name: 'BusinessGoodsSku',
      component: EmptyRouter,
      redirect: 'sku/ordinary',
      meta: {
        title: 'SKU管理',
      },
      children: [
        {
          path: 'ordinary',
          name: 'BusinessGoodsSkuOrdinaryIndex',
          component: () => import('@/views/center-business/goods/sku/ordinary'),
          meta: {
            title: '普通SKU列表',
          },
          permissionType: 'tab',
        },
        {
          path: 'ordinary/add',
          name: 'BusinessGoodsSkuOrdinaryAdd',
          component: () => import('@/views/center-business/goods/sku/ordinary/add'),
          meta: {
            title: '添加普通SKU',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: 'ordinary/edit',
          name: 'BusinessGoodsSkuOrdinaryEdit',
          component: () => import('@/views/center-business/goods/sku/ordinary/edit'),
          meta: {
            title: '编辑普通SKU',
          },
          hidden: true,
        },
        {
          path: 'ordinary/view',
          name: 'BusinessGoodsSkuOrdinaryView',
          component: () => import('@/views/center-business/goods/sku/ordinary/view'),
          meta: {
            title: '查看普通SKU',
          },
          hidden: true,
        },
        /** 按钮权限start */
        {
          path: '',
          name: 'BusinessGoodsSkuOrdinary_ImportData',
          meta: { title: '普通SKU-导入数据' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsSkuOrdinary_DownloadTemplate',
          meta: { title: '普通SKU-下载模板' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsSkuOrdinary_Delete',
          meta: { title: '普通SKU-删除SKU' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsSkuOrdinary_Status',
          meta: { title: '普通SKU-启用/禁用' },
          hidden: true,
        },

        {
          path: '',
          name: 'BusinessGoodsSkuOrdinary_Audit',
          meta: { title: '普通SKU-提交' },
          hidden: true,
        },
        /** 按钮权限end */
        {
          path: 'package',
          name: 'BusinessGoodsSkuPackageIndex',
          component: () => import('@/views/center-business/goods/sku/package'),
          meta: {
            title: '套餐SKU列表',
          },
          permissionType: 'tab',
        },
        {
          path: 'package/add',
          name: 'BusinessGoodsSkuPackageAdd',
          component: () => import('@/views/center-business/goods/sku/package/add'),
          meta: {
            title: '添加套餐SKU',
            isCache: true,
          },
          hidden: true,
        },
        {
          path: 'package/edit',
          name: 'BusinessGoodsSkuPackageEdit',
          component: () => import('@/views/center-business/goods/sku/package/edit'),
          meta: {
            title: '编辑套餐SKU',
          },
          hidden: true,
        },
        {
          path: 'package/view',
          name: 'BusinessGoodsSkuPackageView',
          component: () => import('@/views/center-business/goods/sku/package/view'),
          meta: {
            title: '查看套餐SKU',
          },
          hidden: true,
        },
        /** 按钮权限start */
        {
          path: '',
          name: 'BusinessGoodsSkuPackage_ImportData',
          meta: { title: '套餐SKU-导入数据' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsSkuPackage_DownloadTemplate',
          meta: { title: '套餐SKU-下载模板' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsSkuPackage_Delete',
          meta: { title: '套餐SKU-删除SKU' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsSkuPackage_Status',
          meta: { title: '套餐SKU-启用/禁用' },
          hidden: true,
        },

        {
          path: '',
          name: 'BusinessGoodsSkuPackage_Audit',
          meta: { title: '套餐SKU-提交' },
          hidden: true,
        },
        /** 按钮权限end */
        {
          path: 'draft',
          name: 'BusinessGoodsSkuDraftIndex',
          component: () => import('@/views/center-business/goods/sku/draft/index'),
          meta: {
            title: '草稿SKU列表',
          },
          permissionType: 'tab',
        },
        {
          path: '',
          name: 'BusinessGoodsSkuDraft_Audit',
          meta: { title: '草稿SKU-提交' },
          hidden: true,
        },
      ],
    },
    /** 分类 */
    {
      path: 'classify',
      name: 'BusinessGoodsClassify',
      component: EmptyRouter,
      redirect: 'classify/index',
      meta: {
        title: '分类管理',
      },
      children: [
        {
          path: 'index',
          name: 'BusinessGoodsClassifyIndex',
          component: () => import('@/views/center-business/goods/classify'),
          meta: {
            title: '分类管理',
          },
        },

        /** 按钮权限start */
        {
          path: '',
          name: 'BusinessGoodsClassify_Add',
          meta: { title: '添加分类', isCache: true },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsClassify_Edit',
          meta: { title: '编辑分类' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsClassify_ImportData',
          meta: { title: '导入数据' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsClassify_DownloadTemplate',
          meta: { title: '下载模板' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsClassify_Delete',
          meta: { title: '删除分类' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsClassify_Status',
          meta: { title: '启用/禁用' },
          hidden: true,
        },

        /** 按钮权限end */
      ],
    },
    /** 品牌 */
    {
      path: 'brand',
      name: 'BusinessGoodsBrand',
      component: EmptyRouter,
      redirect: 'brand/index',
      meta: {
        title: '品牌管理',
      },
      children: [
        {
          path: 'index',
          name: 'BusinessGoodsBrandIndex',
          component: () => import('@/views/center-business/goods/brand'),
          meta: {
            title: '品牌管理',
          },
        },
        /** 按钮权限start */
        {
          path: '',
          name: 'BusinessGoodsBrandIndex_Add',
          meta: { title: '创建品牌', isCache: true },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsBrandIndex_Edit',
          meta: { title: '编辑品牌' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsBrandIndex_Delete',
          meta: { title: '删除品牌' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsBrandIndex_Status',
          meta: { title: '启用/禁用' },
          hidden: true,
        },
        /** 按钮权限end */
      ],
    },
    /**  **** 素材管理 ****  */
    {
      path: 'material',
      name: 'BusinessGoodsMaterial',
      component: EmptyRouter,
      redirect: 'material/index',
      meta: {
        title: '素材管理',
      },
      children: [
        {
          path: 'index',
          name: 'BusinessGoodsMaterialIndex',
          component: () => import('@/views/center-business/goods/material'),
          meta: {
            title: '素材管理',
          },
        },
        /** 按钮权限start */
        {
          path: '',
          name: 'BusinessGoodsMaterialIndex_Add',
          meta: { title: '创建素材' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsMaterialIndex_Edit',
          meta: { title: '编辑素材' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsMaterialIndex_Delete',
          meta: { title: '删除素材' },
          hidden: true,
        },
        /** 按钮权限end */
      ],
    },
    /**  **** 价格项管理 **** */
    {
      path: 'price/item',
      name: 'BusinessGoodsPriceItem',
      component: EmptyRouter,
      redirect: 'price/item/index',
      meta: {
        title: '价格项管理',
      },
      children: [
        {
          path: 'index',
          name: 'BusinessGoodsPriceItemIndex',
          component: () => import('@/views/center-business/goods/price/item'),
          meta: {
            title: '价格项管理',
          },
        },

        /** 按钮权限start */
        // {
        //   path: '',
        //   name: 'BusinessGoodsPriceItemIndex_Add',
        //   meta: { title: '创建价格项' },
        //   hidden: true,
        // },
        {
          path: '',
          name: 'BusinessGoodsPriceItemIndex_Edit',
          meta: { title: '编辑价格项' },
          hidden: true,
        },
        // {
        //   path: '',
        //   name: 'BusinessGoodsPriceItemIndex_Delete',
        //   meta: { title: '删除价格项' },
        //   hidden: true,
        // },
        {
          path: '',
          name: 'BusinessGoodsPriceItemIndex_Status',
          meta: { title: '启用/禁用' },
          hidden: true,
        },
        /** 按钮权限end */
      ],
    },

    /**  **** 虚拟商品 ****  */
    // {
    //   path: 'virtual',
    //   name: 'BusinessGoodsVirtual',
    //   component: EmptyRouter,
    //   redirect: 'virtual/sku-group',
    //   meta: {
    //     title: '虚拟商品管理',
    //   },
    //   children: [
    //     // 组合商品
    //     {
    //       path: 'sku-group',
    //       name: 'BusinessGoodsVirtualSkuGroupList',
    //       component: () => import('@/views/center-business/goods/virtual/sku-group/index'),
    //       meta: {
    //         title: '组合商品',
    //       },
    //       permissionType: 'tab',
    //       hidden: true,
    //     },
    //     {
    //       path: 'add',
    //       name: 'BusinessGoodsVirtualSkuGroupAdd',
    //       component: () => import('@/views/center-business/goods/virtual/sku-group/add'),
    //       meta: {
    //         title: '添加组合商品',
    //       },
    //       hidden: true,
    //     },
    //     {
    //       path: 'edit',
    //       name: 'BusinessGoodsVirtualSkuGroupEdit',
    //       component: () => import('@/views/center-business/goods/virtual/sku-group/edit'),
    //       meta: {
    //         title: '编辑组合商品',
    //       },
    //       hidden: true,
    //     },
    //     {
    //       path: 'view',
    //       name: 'BusinessGoodsVirtualSkuGroupView',
    //       component: () => import('@/views/center-business/goods/virtual/sku-group/view'),
    //       meta: {
    //         title: '组合商品详情',
    //       },
    //       hidden: true,
    //     },
    //     /** 按钮权限start */
    //     {
    //       path: '',
    //       name: 'BusinessGoodsVirtualSkuGroup_Delete',
    //       meta: { title: '删除组合商品' },
    //       hidden: true,
    //     },
    //     // 子母件商品
    //     {
    //       path: 'spu-son',
    //       name: 'BusinessGoodsVirtualSpuSonList',
    //       component: () => import('@/views/center-business/goods/virtual/spu-son/index'),
    //       meta: {
    //         title: '子母件商品',
    //       },
    //       permissionType: 'tab',
    //       hidden: true,
    //     },
    //     {
    //       path: 'spu-son/add',
    //       name: 'BusinessGoodsVirtualSpuSonAdd',
    //       component: () => import('@/views/center-business/goods/virtual/spu-son/add'),
    //       meta: {
    //         title: '添加子母件商品',
    //       },
    //       hidden: true,
    //     },
    //     {
    //       path: 'spu-son/edit',
    //       name: 'BusinessGoodsVirtualSpuSonEdit',
    //       component: () => import('@/views/center-business/goods/virtual/spu-son/edit'),
    //       meta: {
    //         title: '编辑子母件商品',
    //       },
    //       hidden: true,
    //     },
    //     {
    //       path: 'spu-son/view',
    //       name: 'BusinessGoodsVirtualSpuSonView',
    //       component: () => import('@/views/center-business/goods/virtual/spu-son/view'),
    //       meta: {
    //         title: '子母件商品详情',
    //       },
    //       hidden: true,
    //     },
    //     /** 按钮权限start */
    //     {
    //       path: '',
    //       name: 'BusinessGoodsVirtualSpuSon_Delete',
    //       meta: { title: '删除子母件商品' },
    //       hidden: true,
    //     },
    //     // 安装服务
    //     {
    //       path: 'install-service',
    //       name: 'BusinessGoodsVirtualInstallServiceList',
    //       component: () => import('@/views/center-business/goods/virtual/install-service/index'),
    //       meta: {
    //         title: '安装服务',
    //       },
    //       permissionType: 'tab',
    //       hidden: true,
    //     },
    //     {
    //       path: 'install-service/add',
    //       name: 'BusinessGoodsVirtualInstallServiceAdd',
    //       component: () => import('@/views/center-business/goods/virtual/install-service/add'),
    //       meta: {
    //         title: '添加安装服务',
    //       },
    //       hidden: true,
    //     },
    //     {
    //       path: 'install-service/edit',
    //       name: 'BusinessGoodsVirtualInstallServiceEdit',
    //       component: () => import('@/views/center-business/goods/virtual/install-service/edit'),
    //       meta: {
    //         title: '编辑安装服务',
    //       },
    //       hidden: true,
    //     },
    //     {
    //       path: 'install-service/view',
    //       name: 'BusinessGoodsVirtualInstallServiceView',
    //       component: () => import('@/views/center-business/goods/virtual/install-service/view'),
    //       meta: {
    //         title: '安装服务详情',
    //       },
    //       hidden: true,
    //     },
    //     /** 按钮权限start */
    //     {
    //       path: '',
    //       name: 'BusinessGoodsVirtualInstallService_Delete',
    //       meta: { title: '删除安装服务' },
    //       hidden: true,
    //     },
    //   ],
    // },
    /**  **** 审核商品 ****  */
    {
      path: 'audit',
      name: 'BusinessGoodsAudit',
      component: EmptyRouter,
      redirect: 'audit/sku/ordinary',
      meta: {
        title: '商品审核',
      },
      children: [
        {
          path: 'sku/ordinary',
          name: 'BusinessGoodsAuditSkuOrdinaryIndex',
          component: () => import('@/views/center-business/goods/audit/sku/ordinary'),
          meta: {
            title: '商品SKU审核',
          },
          permissionType: 'tab',
          hidden: true,
        },

        {
          path: 'sku/ordinary/view',
          name: 'BusinessGoodsAuditSkuOrdinaryView',
          component: () => import('@/views/center-business/goods/audit/sku/ordinary/view'),
          meta: {
            title: '查看商品SKU审核详情',
          },
          hidden: true,
        },
        {
          path: 'sku/ordinary/audit',
          name: 'BusinessGoodsAuditSkuOrdinaryAudit',
          component: () => import('@/views/center-business/goods/audit/sku/ordinary/audit'),
          meta: {
            title: '审核商品SKU审核详情',
          },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsAuditSkuOrdinary_Pass',
          meta: { title: '商品SKU审核-通过' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsAuditSkuOrdinary_reject',
          meta: { title: '商品SKU审核-驳回' },
          hidden: true,
        },
        {
          path: 'sku/package',
          name: 'BusinessGoodsAuditSkuPackageIndex',
          component: () => import('@/views/center-business/goods/audit/sku/package'),
          meta: {
            title: '套餐SKU审核',
          },
          permissionType: 'tab',
          hidden: true,
        },
        {
          path: 'sku/package/view',
          name: 'BusinessGoodsAuditSkuPackageView',
          component: () => import('@/views/center-business/goods/audit/sku/package/view'),
          meta: {
            title: '查看套餐SKU审核详情',
          },
          hidden: true,
        },
        {
          path: 'sku/package/audit',
          name: 'BusinessGoodsAuditSkuPackageAudit',
          component: () => import('@/views/center-business/goods/audit/sku/package/audit'),
          meta: {
            title: '审核套餐SKU审核详情',
          },
          hidden: true,
        },

        {
          path: '',
          name: 'BusinessGoodsAuditSkuPackage_Pass',
          meta: { title: '套餐SKU审核-通过' },
          hidden: true,
        },
        {
          path: '',
          name: 'BusinessGoodsAuditSkuPackage_reject',
          meta: { title: '套餐SKU审核-驳回' },
          hidden: true,
        },
      ],
    },
  ],
};
