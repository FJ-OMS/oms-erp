/*
 * @Author: your name
 * @Date: 2021-10-26 10:11:25
 * @LastEditTime: 2021-10-26 10:16:32
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\stockApi\structureApi.js
 */
/**
 *
 * rule-controller : Rule Controller
 *
 */

 import { generate } from '@/utils/request';
 const config = {
   prefix: '/stock/v1/warehouse/relation',
   items: [
     // 租户配置比率关系图(租户级)
     { key: 'getAllwarehouseSetting', url: '/setting/all', method: 'POST' },
   ],
 };

 export default {
   ...generate(config),
 };
