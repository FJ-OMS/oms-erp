/*
 * @Author: your name
 * @Date: 2021-07-13 14:49:11
 * @LastEditTime: 2021-10-14 13:56:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\settingApi\index.js
 */
/**
 * 模块接口
 */

import strategyApi from './strategyApi';
import schedulerApi from './schedulerApi';
import fileApi from './fileApi';
import instance from './instanceApi';
import indexes from './indexes';
import reportApi from './reportApi';
import stockProcessApi from './stockProcessApi';
import logistics from './logistics';
import orderProcessApi from './orderProcessApi';

export default {
  ...strategyApi,
  ...schedulerApi,
  ...fileApi,
  ...instance,
  ...indexes,
  ...reportApi,
  ...stockProcessApi,
  ...logistics,
  ...orderProcessApi,
};
