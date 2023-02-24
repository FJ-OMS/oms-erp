package org.skyer.afterSales.infra.feign.impl;

import org.skyer.core.exception.CommonException;
import org.skyer.afterSales.infra.feign.UserDetailRemoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户信息远程调用失败回调
 *
 * @author qingsheng.chen@hand-china.com
 */
@Component
public class UserDetailRemoteServiceImpl implements UserDetailRemoteService {

    @Override
    public ResponseEntity storeUserAdditionInfo(String accessToken, String dataHierarchyCode, String dataHierarchyValue,
                    String dataHierarchyMeaning, List<String> childrenDataHierarchyCodes) {
        throw new CommonException(
                        "Error store user addition info[" + dataHierarchyCode + ":" + dataHierarchyValue + "].");
    }
}
