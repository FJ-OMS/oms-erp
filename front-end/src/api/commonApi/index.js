/**
 * 渠道模块接口
 */

import areaApi from './areaApi';
import optionApi from './optionApi';
import settingApi from './settingApi';
import loginApi from './loginApi';

export default {
  ...areaApi,
  ...optionApi,
  ...settingApi,
  ...loginApi,
};
