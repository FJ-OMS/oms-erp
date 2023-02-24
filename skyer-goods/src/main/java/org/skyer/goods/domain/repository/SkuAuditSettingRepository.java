package org.skyer.goods.domain.repository;

import org.skyer.mybatis.base.BaseRepository;
import org.skyer.goods.domain.entity.SkuAuditSetting;

/**
 * 商品SKU审核资源库
 *
 * @author zhanglongbing@ebc.com 2021-12-27 10:09:56
 */
public interface SkuAuditSettingRepository extends BaseRepository<SkuAuditSetting> {

    /**
     * 查询默认的流程配置
     * @return
     */
    SkuAuditSetting selectDefault();

    /**
     * 更新默认的流程配置
     * @param skuAuditSetting
     * @return
     */
    int updateDefault(SkuAuditSetting skuAuditSetting);
}
