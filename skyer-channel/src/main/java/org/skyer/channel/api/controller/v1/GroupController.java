package org.skyer.channel.api.controller.v1;

import org.skyer.channel.domain.service.GroupService;
import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.channel.domain.entity.Group;
import org.skyer.channel.domain.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.skyer.mybatis.helper.SecurityTokenHelper;

import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.mybatis.pagehelper.annotation.SortDefault;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.pagehelper.domain.Sort;
import org.skyer.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 *  集团组织 API
 *
 * @author zhanglongbing@ebc.com 2021-12-21
 */
@RestController("groupController.v1")
@RequestMapping("/v1/groups")
public class GroupController extends BaseController {

    @Autowired
    private GroupService groupService;


    @ApiOperation(value = "获取默认集团")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @GetMapping("/default")
    public ResponseEntity<Group> getDefaultGroups() {
        Group group = groupService.getDefaultGroups();
        return Results.success(group);
    }

    @ApiOperation(value = "修改默认集团信息")
    @Permission(level = ResourceLevel.ORGANIZATION,permissionLogin = true)
    @PostMapping("/update")
    public ResponseEntity<Group> updateDefaultGroups(@RequestBody Group group) {
        groupService.updateDefaultGroups(group);
        return Results.success(group);
    }


}
