package org.skyer.channel.domain.service;

import org.skyer.channel.api.dto.*;
import org.skyer.channel.domain.vo.ChannelSaleOrderVO;
import org.skyer.channel.domain.vo.OnlineShopDetailVO;
import org.skyer.channel.domain.vo.OnlineShopSettingVO;
import org.skyer.channel.domain.vo.OnlineShopVO;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OnlineShopService {

    Boolean save(OnlineShopDTO onlineShopDTO);

    Boolean setting(OnlineShopSettingDTO onlineShopSettingDTO);

    Boolean delete(Long id, Long objectVersionNumber);

    Boolean operation(Long id, Boolean flag, Long objectVersionNumber);

    Page<OnlineShopVO> getPage(OnlineShopShowDTO onlineShopShowDTO, PageRequest pageRequest);

    /**
     * 根据主键获取全部详情
     *
     * @author lixiaoyang
     * @date 2021/6/28 17:20
     */
    OnlineShopDetailVO getDetails(Long id);


    OnlineShopDetailVO getDetailsByCode(String code);

    /**
     * 根据主键获取设置信息
     *
     * @author lixiaoyang
     * @date 2021/6/28 17:21
     */
    OnlineShopSettingVO getSetting(Long id);

    void deleteCache(Long id);

    Boolean deletes(ShopDeleteDTO shopDeleteDTO);


}
