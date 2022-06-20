package org.skyer.goods.domain.service;

import org.skyer.core.domain.Page;
import org.skyer.goods.api.dto.MaterialDTO;
import org.skyer.goods.api.dto.MaterialListDTO;
import org.skyer.goods.domain.entity.Material;
import org.skyer.goods.domain.vo.MaterialVO;
import org.skyer.mybatis.base.BaseRepository;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

import java.util.List;

/**
 * <p>
 * 商品素材
 * </p>
 *
 * @author chenzz
 * @since 2021-06-23
 */
public interface MaterialService extends BaseRepository<Material> {

    /**
     * 分页数据
     *
     * @param pageRequest
     * @param materialListDTO
     * @return
     */
    Page<MaterialVO> pageList(PageRequest pageRequest, MaterialListDTO materialListDTO);

    /**
     * 添加
     *
     * @param materialDTO
     * @return
     */
    Boolean add(MaterialDTO materialDTO);

    /**
     * 修改
     *
     * @param materialDTO
     * @return
     */
    Boolean edit(MaterialDTO materialDTO);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    /**
     * 详情
     *
     * @param id
     * @return
     */
    MaterialVO getDetail(Long id);

    Boolean deleteByIds(List<Long> ids);
}
