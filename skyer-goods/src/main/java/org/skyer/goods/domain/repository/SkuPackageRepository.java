package org.skyer.goods.domain.repository;

import org.skyer.goods.domain.vo.RpcGoodsResVO;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.goods.domain.entity.SkuPackage;

import java.util.List;

/**
 * 资源库
 *
 * @author zhanglongbing@ebc.com 2022-03-03 10:45:30
 */
public interface SkuPackageRepository extends BaseRepository<SkuPackage> {


    List<SkuPackage> querySkuPackageByMainId(Long packageId);
}
