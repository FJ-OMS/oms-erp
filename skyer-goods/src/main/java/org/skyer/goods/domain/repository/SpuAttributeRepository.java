package org.skyer.goods.domain.repository;

import java.util.List;

import org.skyer.goods.api.dto.AttrListRequestDTO;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.goods.domain.entity.SpuAttribute;

/**
 * 属性表资源库
 *
 * @author linzhuanghuang@ebc.com 2021-12-28 16:40:03
 */
public interface SpuAttributeRepository extends BaseRepository<SpuAttribute> {


	/**
	 * 保存属性
	 * @param attrs
	 * @param spuId
	 */
	void saveAttr(List<AttrListRequestDTO> attrs, Long spuId);

	/**
	 * 批量删除属性和属性值
	 * @param ids
	 */
	void deleteAttribute(List<Long> ids);

}
