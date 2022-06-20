package org.skyer.goods.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.skyer.goods.api.dto.BrandPageReqDTO;
import org.skyer.goods.domain.entity.Brand;
import org.skyer.goods.domain.vo.BrandPageResVO;
import org.skyer.mybatis.common.BaseMapper;

import java.util.List;

/**
 * @description
 * @Author wusc
 * @create 2021/6/24 4:34 下午
 */
public interface BrandMapper extends BaseMapper<Brand> {

     List<BrandPageResVO> getBrandVOList(@Param("param") BrandPageReqDTO brandPageReqDTO);
}
