/**
 * 渠道模块接口
 */

import shopOnlineApi from './shopOnlineApi';
import shopPhysicalApi from './shopPhysicalApi';
import dealerApi from './dealerApi';
import warehouseApi from './warehouseApi';
import salesOrganizationApi from './salesOrganizationApi';
import shopManageApi from './shopManageApi';
import companyApi from './companyApi';

export default {
  ...shopOnlineApi,
  ...shopPhysicalApi,
  ...dealerApi,
  ...warehouseApi,
  ...salesOrganizationApi,
  ...shopManageApi,
  ...companyApi,
};
