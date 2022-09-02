package org.skyer.afterSales.config;


import org.skyer.core.swagger.SkyerRouteData;
import org.skyer.swagger.annotation.SkyerExtraData;
import org.skyer.swagger.swagger.extra.ExtraData;
import org.skyer.swagger.swagger.extra.ExtraDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * 服务基本信息
 *
 * @author gaokuo.dai@hand-china.com 2018年7月20日下午4:49:12
 */
@SkyerExtraData
public class AfterSalesExtraDataManager implements ExtraDataManager {

    @Autowired
    private Environment environment;

    @Override
    public ExtraData getData() {
        SkyerRouteData routeData = new SkyerRouteData();
        routeData.setName(environment.getProperty("skyer.service.current.name", "safs"));
        routeData.setPath(environment.getProperty("skyer.service.current.path", "/safs/**"));
        routeData.setServiceId(
                environment.getProperty("skyer.service.current.service-name", "skyer-after-sales"));
        routeData.setPackages("org.skyer.afterSales.api");
        extraData.put(ExtraData.ZUUL_ROUTE_DATA, routeData);
        return extraData;
    }

}
