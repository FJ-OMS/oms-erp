package org.skyer.goods.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.skyer.goods.api.dto.GroupPageReqDTO;
import org.skyer.goods.domain.entity.SkuGroup;
import org.skyer.goods.domain.vo.GroupPageResVO;
import org.skyer.mybatis.common.BaseMapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * Sku组合表Mapper
 *
 * @author wushaochuan 2021-08-13 09:55:04
 */
public interface SkuGroupMapper extends BaseMapper<SkuGroup> {

    List<GroupPageResVO> queryGroupPageList(@Param("param") GroupPageReqDTO groupPageReqDTO,@Param("tenantId")Long tenantId);



}
