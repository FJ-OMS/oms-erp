package org.skyer.channel.domain.service.saga;

import org.skyer.channel.api.dto.ChannelSaleOrderDTO;
import org.skyer.channel.api.dto.QueryShopListDTO;
import org.skyer.channel.api.dto.SaveShopDTO;
import org.skyer.channel.api.dto.ShopDTO;
import org.skyer.channel.domain.vo.ChannelSaleOrderVO;
import org.skyer.channel.domain.vo.ShopListVO;
import org.skyer.core.domain.Page;

import java.util.List;

public interface ShopService {

    ShopDTO doSaveShop(SaveShopDTO saveShopDTO);

    Page<ShopListVO> queryShop(QueryShopListDTO dto);

    String creationCode(Long tenantId);

    List<ChannelSaleOrderVO> queryChannelOrderSaleReport(ChannelSaleOrderDTO dto);
}
