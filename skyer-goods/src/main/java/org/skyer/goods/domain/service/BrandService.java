package org.skyer.goods.domain.service;


import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.BrandPageReqDTO;
import org.skyer.goods.api.dto.BrandReqDTO;
import org.skyer.goods.domain.vo.AttributePageResVO;
import org.skyer.goods.domain.vo.BrandPageResVO;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wusc
 */
public interface BrandService {

    /**
     * 品牌分页查询
     * @param brandPageReqDTO
     * @return
     */
    Page<BrandPageResVO> pageBrand(BrandPageReqDTO brandPageReqDTO);

    /**
     * 查询列表
     * @param brandPageReqDTO
     * @return
     */
    List<BrandPageResVO> listBrand(BrandPageReqDTO brandPageReqDTO);
    /**
     * 保存或者更新品牌
     * @param brandReqDTO
     */
    void saveOrUpdateBrand(BrandReqDTO brandReqDTO);

    /**
     * 删除属性
     * @param ids
     */
    void deleteBrand(List<Long> ids);

    /**
     * 更新品牌状态
     * @param ids
     * @param status
     */
    void updateBrandStatus(List<Long> ids,Integer status);
}