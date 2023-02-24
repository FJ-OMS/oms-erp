/* jshint esversion: 9 */ /**
 * 商品模块接口
 */

import materialApi from './materialApi';
import attributeApi from './attributeApi';
import attributeRelationApi from './attributeRelationApi';
import brandApi from './brandApi';
import categoryApi from './categoryApi';
import managerApi from './managerApi';
import priceApi from './priceApi';
import skuApi from './skuApi';
import skuGroupApi from './skuGroupApi';
import spuSonApi from './spuSonApi';
import installServiceApi from './installServiceApi';
import otherInfoApi from './otherInfoApi';
import auditApi from './auditApi';

export default {
  ...materialApi,
  ...attributeApi,
  ...attributeRelationApi,
  ...brandApi,
  ...categoryApi,
  ...managerApi,
  ...priceApi,
  ...skuApi,
  ...skuGroupApi,
  ...spuSonApi,
  ...installServiceApi,
  ...otherInfoApi,
  ...auditApi,
};
