/*
 * @Author: your name
 * @Date: 2021-08-06 13:55:19
 * @LastEditTime: 2021-09-08 17:10:17
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\stockApi\index.js
 */
/* jshint esversion: 9 */
/**
 * 模块接口
 */

import companyApi from './companyApi';
import businessApi from './businessApi';
import mappingApi from './mappingApi';
import relationApi from './relationApi';
import orderApi from './orderApi';
import claimApi from './claimApi';
import freightApi from './freightApi';
import abnormalApi from './abnormalApi';

export default {
  ...companyApi,
  ...businessApi,
  ...mappingApi,
  ...relationApi,
  ...orderApi,
  ...claimApi,
  ...freightApi,
  ...abnormalApi,
};
