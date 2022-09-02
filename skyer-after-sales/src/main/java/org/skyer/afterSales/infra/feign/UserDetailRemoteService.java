package org.skyer.afterSales.infra.feign;

import org.skyer.common.SkyerService;
import org.skyer.afterSales.infra.feign.impl.UserDetailRemoteServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户信息远程 Feign 调用
 *
 * @author qingsheng.chen@hand-china.com
 */
@FeignClient(value = SkyerService.Oauth.NAME, fallback = UserDetailRemoteServiceImpl.class, path = "/oauth/api/user")
public interface UserDetailRemoteService {
    /**
     * 存储用户附加信息
     *
     * @param accessToken 用户授权token
     * @param dataHierarchyCode 数据层级编码
     * @param dataHierarchyValue 数据层级值
     * @param dataHierarchyMeaning 数据层级值展示值
     * @param childrenDataHierarchyCodes 子数据层级编码
     * @return 存储用户附加信息
     */
    @PostMapping("/addition-info")
    ResponseEntity storeUserAdditionInfo(@RequestParam("access_token") String accessToken,
                    @RequestParam("dataHierarchyCode") String dataHierarchyCode,
                    @RequestParam("dataHierarchyValue") String dataHierarchyValue,
                    @RequestParam("dataHierarchyMeaning") String dataHierarchyMeaning,
                    @RequestParam(value = "childrenDataHierarchyCodes",
                                    required = false) List<String> childrenDataHierarchyCodes);
}
