package org.skyer.channel.domain.service;

import org.skyer.channel.api.dto.PhysicalShopDTO;
import org.skyer.channel.api.dto.PhysicalShopShowDTO;
import org.skyer.channel.api.dto.ShopDeleteDTO;
import org.skyer.channel.domain.vo.PhysicalShopDetailVO;
import org.skyer.channel.domain.vo.PhysicalShopVO;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.pagehelper.domain.PageRequest;

public interface PhysicalShopService {

    Boolean save(PhysicalShopDTO dto);

    Boolean delete(Long id, Long objectVersionNumber);

    Boolean deletes(ShopDeleteDTO shopDeleteDTO);

    Boolean operation(Long id, Boolean flag, Long objectVersionNumber);

    Page<PhysicalShopVO> getPage(PhysicalShopShowDTO physicalShopShowDTO, PageRequest pageRequest);

    PhysicalShopDetailVO getDetails(Long id);

    void deleteCache(Long id);
}
