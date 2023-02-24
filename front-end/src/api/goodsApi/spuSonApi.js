/**
 * goods-spu-son : 子母商品管理
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '/sgoo/v1/spu-son-mums',
  items: [
    /**
     * 新增
     */
    { key: 'addSpuSonNum', url: '', method: 'POST' },
    /**
     * 删除
     */
    { key: 'deleteSpuSonNum', url: '', method: 'DELETE' },

    /**
     * 分页获取
     */
    { key: 'getSpuSonNum', url: '', method: 'GET' },
    /**
     * 修改
     */
    { key: 'updateSpuSonNum', url: '', method: 'PUT' },
    /**
     * 根据Id获取
     */
    { key: 'detailSpuSonNum', url: '/', method: 'GET' },
  ],
};

export default {
  ...generate(config),
};
