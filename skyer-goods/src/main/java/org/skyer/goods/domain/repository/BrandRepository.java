package org.skyer.goods.domain.repository;


import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.BrandPageReqDTO;
import org.skyer.goods.domain.entity.Brand;
import org.skyer.goods.domain.vo.AttributePageResVO;
import org.skyer.goods.domain.vo.BrandPageResVO;
import org.skyer.mybatis.base.BaseRepository;

import java.util.List;

/**
* <p>
*  商品品牌表Mapper
* </p>
*
* @author wusc
* @since 2021-06-24
*/


public interface BrandRepository extends BaseRepository<Brand> {

    /**
     * 分页查询品牌
     * @param
     * @return
     */
    Page<BrandPageResVO> pageBrand(BrandPageReqDTO brandPageReqDTO);


    /**
     * 列表查询商品
     * @param brandPageReqDTO
     * @return
     */
    List<BrandPageResVO> listBrand(BrandPageReqDTO brandPageReqDTO);
    /**
     * 通过名称品牌
     * @param
     * @return
     */
    Brand getBrandByName(String name,Long id,Long tenantId);

    /**
     * 通过名称品牌
     * @param
     * @return
     */
    Brand getBrandByCode(String code,Long id);
}