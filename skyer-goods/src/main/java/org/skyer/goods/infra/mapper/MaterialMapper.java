package org.skyer.goods.infra.mapper;

import org.apache.ibatis.annotations.Param;
import org.skyer.goods.api.dto.MaterialListDTO;
import org.skyer.goods.domain.entity.Material;
import org.skyer.goods.domain.vo.MaterialVO;
import org.skyer.mybatis.common.BaseMapper;

import java.util.List;

/**
 * <p>
 * 商品素材
 * </p>
 *
 * @author chenzz
 * @since 2021-06-23
 */
public interface MaterialMapper extends BaseMapper<Material> {
    List<MaterialVO> getListPage(@Param("materialDTO") MaterialListDTO materialDTO);
}
