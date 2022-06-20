package org.skyer.tags.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.skyer.core.swagger.SkyerRouteData;
import org.skyer.swagger.annotation.SkyerExtraData;
import org.skyer.swagger.swagger.extra.ExtraData;
import org.skyer.swagger.swagger.extra.ExtraDataManager;

/**
 * 服务基本信息
 *
 * @author gaokuo.dai@hand-china.com 2018年7月20日下午4:49:12
 */
@SkyerExtraData
public class TagsExtraDataManager implements ExtraDataManager {

    @Autowired
    private Environment environment;

    @Override
    public ExtraData getData() {
        SkyerRouteData routeData = new SkyerRouteData();
        routeData.setName(environment.getProperty("skyer.service.current.name", "tags"));
        routeData.setPath(environment.getProperty("skyer.service.current.path", "/tags/**"));
        routeData.setServiceId(
                environment.getProperty("skyer.service.current.service-name", "skyer-tags"));
        routeData.setPackages("org.skyer.tags.api");
        extraData.put(ExtraData.ZUUL_ROUTE_DATA, routeData);
        return extraData;
    }

}
