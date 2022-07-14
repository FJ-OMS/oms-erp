package org.skyer.order.config;


import org.skyer.core.swagger.SkyerRouteData;
import org.skyer.swagger.annotation.SkyerExtraData;
import org.skyer.swagger.swagger.extra.ExtraData;
import org.skyer.swagger.swagger.extra.ExtraDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * 服务基本信息
 *
 * @author
 */
@SkyerExtraData
public class OrderExtraDataManager implements ExtraDataManager {

    @Autowired
    private Environment environment;

    @Override
    public ExtraData getData() {
        SkyerRouteData routeData = new SkyerRouteData();
        routeData.setName(environment.getProperty("skyer.service.current.name", "sord"));
        routeData.setPath(environment.getProperty("skyer.service.current.path", "/sord/**"));
        routeData.setServiceId(
                environment.getProperty("skyer.service.current.service-name", "skyer-order"));
        routeData.setPackages("org.skyer.order.api,org.skyer.plugin.order");
        extraData.put(ExtraData.ZUUL_ROUTE_DATA, routeData);
        return extraData;
    }

}
