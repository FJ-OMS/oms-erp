package org.skyer.channel.infra.feign;

import org.skyer.channel.infra.feign.fallback.UserClientImpl;
import org.skyer.common.SkyerService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 * 用户信息获取
 * </p>
 *
 * @author  zhanglb
 */
@FeignClient(value = SkyerService.Iam.NAME, fallback = UserClientImpl.class, path = "/skyer/v1")
public interface UserClient {

    /**
     * 查询组织ids是否有关联用户
     * @param companyIds 公司ids
     * @return Boolean
     */
    @PostMapping("/company/user")
    ResponseEntity companyUser(@RequestBody List<Long> companyIds);


}
