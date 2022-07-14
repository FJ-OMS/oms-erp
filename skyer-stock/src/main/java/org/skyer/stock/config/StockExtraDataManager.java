package org.skyer.stock.config;


import org.skyer.common.SkyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.skyer.core.swagger.SkyerRouteData;
import org.skyer.swagger.annotation.SkyerExtraData;
import org.skyer.swagger.swagger.extra.ExtraData;
import org.skyer.swagger.swagger.extra.ExtraDataManager;

/**
 * 服务基本信息
 *
 * @author
 */
@SkyerExtraData
public class StockExtraDataManager implements ExtraDataManager {

    @Autowired
    private Environment environment;

    @Override
    public ExtraData getData() {
        SkyerRouteData routeData = new SkyerRouteData();
        routeData.setName(environment.getProperty("skyer.service.current.name", "stock"));
        routeData.setPath(environment.getProperty("skyer.service.current.path", "/stock/**"));
        routeData.setServiceId(
                environment.getProperty("skyer.service.current.service-name", "skyer-stock"));
        routeData.setPackages("org.skyer.platform.api,org.skyer.plugin.platform");
        extraData.put(ExtraData.ZUUL_ROUTE_DATA, routeData);
        return extraData;
    }

}
