/*
 */
/**
 * 模块接口
 */

import codeRuleApi from './codeRuleApi';
import countryApi from './countryApi';
import afterSaleApi from './afterSaleApi';
import lovApi from './lovApi';
export default {
  ...codeRuleApi,
  ...countryApi,
  ...afterSaleApi,
  ...lovApi,
};
