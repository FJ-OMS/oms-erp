/*
 * @Author: your name
 * @Date: 2021-07-13 14:49:11
 * @LastEditTime: 2021-07-22 17:08:03
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\api\settingApi\index.js
 */
/**
 * 模块接口
 */

import noticeApi from './noticeApi';
import emailApi from './emailApi';
import smsApi from './smsApi';
import templateApi from './templateApi';
import sendApi from './sendApi';

export default {
  ...noticeApi,
  ...emailApi,
  ...smsApi,
  ...templateApi,
  ...sendApi,
};
