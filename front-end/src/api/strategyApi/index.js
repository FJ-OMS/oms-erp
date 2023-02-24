/*
 * @Author: your name
 * @Date: 2021-10-28 20:58:01
 * @LastEditTime: 2021-11-11 17:07:55
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\strategyApi\index.js
 */
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

 import marketingAuditApi from './marketingAuditApi';
 import sourceApi from './sourceApi';

 export default {
   ...marketingAuditApi,
   ...sourceApi,
 };
