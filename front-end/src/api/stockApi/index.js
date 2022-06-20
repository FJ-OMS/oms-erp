/*
 * @Author: your name
 * @Date: 2021-08-06 13:55:19
 * @LastEditTime: 2022-03-10 11:16:45
 * @LastEditors: LAPTOP-JM4BAR46
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\stockApi\index.js
 */
/* jshint esversion: 9 */
/**
 * 模块接口
 */

import rulesApi from './rulesApi';
import warehouseApi from './warehouseApi';
import adjustRecordsApi from './adjustRecordsApi';
import moveRecordsApi from './moveRecordsApi';
import goodsStocksApi from './goodsStocksApi';
import warehouseRelationApi from './warehouseRelationApi';
import reserveRecordsApi from './reserveRecordsApi';
import distributeSpaceApi from './distributeSpaceApi';
/** 出入库通知/结果单 */
import noticeAndResultApi from './noticeAndResultApi';
import changeRecordApi from './changeRecordApi';
import initializeApi from './initializeApi';
/** 库存管理-->仓库结构管理 */
import structureApi from './structureApi';
import stockOutApi from './stockOutApi';
import abnormalApi from './abnormalApi';
/** 寻源管理 */
import sourcingApi from './sourcingApi';
/** 出库 */
import deliveryApi from './deliveryApi';
/** 入库 */
import entryApi from './entryApi';

/** 调拨管理 */
import transferApi from './transferApi';

export default {
  ...rulesApi,
  ...warehouseApi,
  ...adjustRecordsApi,
  ...moveRecordsApi,
  ...goodsStocksApi,
  ...warehouseRelationApi,
  ...reserveRecordsApi,
  ...distributeSpaceApi,
  ...noticeAndResultApi,
  ...changeRecordApi,
  ...initializeApi,
  ...structureApi,
  ...stockOutApi,
  ...abnormalApi,
  ...sourcingApi,
  ...deliveryApi,
  ...entryApi,
  ...transferApi,
};
