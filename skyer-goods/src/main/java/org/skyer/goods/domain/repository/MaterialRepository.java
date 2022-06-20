package org.skyer.goods.domain.repository;

import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.MaterialListDTO;
import org.skyer.goods.domain.entity.Material;
import org.skyer.goods.domain.vo.MaterialVO;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

/**
 * <p>
 * 商品素材
 * </p>
 *
 * @author chenzz
 * @since 2021-06-23
 */
public interface MaterialRepository extends BaseRepository<Material> {
    /**
     * 分页查询经销商
     *
     * @param pageRequest
     * @param dealerListDTO
     * @return
     */
    Page<MaterialVO> pageList(PageRequest pageRequest, MaterialListDTO dealerListDTO);

	/**
	 * 通过名称查询素材
	 * @param materialName
	 * @return
	 */
	Integer getSizeByName(String materialName);

	/**
	 * 通过编码查询素材
	 * @param code
	 * @return
	 */
	Integer getSizeByCode(String code);
}
