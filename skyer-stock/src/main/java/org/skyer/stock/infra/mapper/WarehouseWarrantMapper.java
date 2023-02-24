package org.skyer.stock.infra.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.skyer.mybatis.common.BaseMapper;
import org.skyer.stock.api.dto.WarehouseWarrantPageQueryDTO;
import org.skyer.stock.domain.entity.WarehouseWarrant;
import org.skyer.stock.domain.vo.WarehouseWarrantVO;

/**
 * 入库单Mapper
 *
 * @author lixiaoyang 2021-10-25 16:34:17
 */
public interface WarehouseWarrantMapper extends BaseMapper<WarehouseWarrant> {

    /**
     * 查询列表
     *
     * @param dto 查询条件
     * @return {@link WarehouseWarrantVO}集合
     */
    List<WarehouseWarrantVO> selectList(WarehouseWarrantPageQueryDTO dto);


    WarehouseWarrant getOneByFrontRecordTypeAndFrontRecordCode(@Param("frontRecordType") String frontRecordType, @Param("frontRecordCode") String frontRecordCode);
}
