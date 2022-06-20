/**
 * CnareaAdministrativeDivision Manage : 中国行政区划分管理
 *
 */

import { generate } from '@/utils/request';
const config = {
  prefix: '',
  items: [
    /**
     * 获取值集值
     * @lovCode
     * SCHL.CHANNEL_TYPE     渠道类型
     * SCHL.SHOP_TYPE       店铺类型
     * SCHL.INVOICE_TYPE       发票种类
     */
    { key: 'getLovsValue', url: '/spfm/v1/lovs/value', method: 'GET', loadingHide: true },
    /**
     * 商品分类管理-获取全部分类
     */
    {
      key: 'getAllCategory',
      url: '/sgoo/v1/categorys/tree',
      method: 'POST',
      loadingHide: true,
    },
    /**
     * 商品分页
     */
    { key: 'getPageGoods', url: '/sgoo/v1/goods/manager/page', method: 'post' },
    /**
     * 品牌分页
     */
    { key: 'getPageBrand', url: '/sgoo/v1/goods/brand/page', method: 'post', loadingHide: true },
    /**
     * 仓库
     */
    {
      key: 'getListWarehouses',
      url: '/stock/v1/warehouses/list-warehouse',
      method: 'POST',
      loadingHide: true,
    },
    {
      key: 'getListStorage',
      url: '/stock/v1/storage/area',
      method: 'GET',
      loadingHide: true,
    },
    /**
     * 素材分页获取
     */
    {
      key: 'getPageMaterial',
      url: '/sgoo/v1/goods/material/list',
      method: 'GET',
      loadingHide: true,
    },
    /**
     * 查询全部属性
     */
    { key: 'getListAttribute', url: '/sgoo/v1/goods/attribute/list', method: 'POST' },

    /**
     * 执行器分页
     */
    { key: 'getExecutorList', url: '/ssdr/v1/executors', method: 'GET' },

    /**
     * 登录用户 - 查询可访问的租户列表
     */
    {
      key: 'getTenants',
      url: '/iam/skyer/v1/users/self-tenants',
      method: 'GET',
    },
    /**
     * 并发可执行列表
     */
    { key: 'getExecutableList', url: '/ssdr/v1/executables', method: 'GET' },
    /**
     * 并发可执行列表
     */
    { key: 'getInstallServices', url: '/sgoo/v1/install-services', method: 'GET' },

    /**
     * 标签列表 ?systemLabelFlag=true
     */
    { key: 'getTags', url: '/tags/v1/labels/labelList', method: 'GET', loadingHide: true },

    /**
     * 售后原因列表  type 0 仅退款；1 退货退款
     */
    { key: 'getAfterSalesReasons', url: '/sord/v1/after-sales-reasons/list', method: 'GET' },
  ],
};

export default {
  ...generate(config),
};
