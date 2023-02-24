package org.skyer.order.infra.remote.api;

import org.skyer.boot.api.customize.commons.vo.MethodMetaData;

import java.util.List;

/**
 * 客制化业务方法调用
 *
 * @author bojiangzhou 2019/07/04
 */
public interface RemoteCustomizeService {

    /**
     * 获取服务的方法
     *
     * @param serviceName  服务编码
     * @param packageNames 包名
     * @return List<MethodMetaDataDTO>
     */
    List<MethodMetaData> fetchServiceMethods(String serviceName, String packageNames);

    /**
     * 应用客制化规则
     *
     * @param serviceNames 服务名
     * @param ruleCodes    规则规则
     */
    void applyCustomizeRule(List<String> serviceNames, List<String> ruleCodes);

    /**
     * 应用客制化规则
     *
     * @param serviceName 服务名
     * @param ruleCodes   规则规则
     */
    void applyCustomizeRule(String serviceName, List<String> ruleCodes);

}
