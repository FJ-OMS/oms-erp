package org.skyer.tags.api.controller.v1;

import io.swagger.annotations.Api;
import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.tags.api.dto.*;
import org.skyer.tags.app.service.LabelService;
import org.skyer.tags.config.TagsSwaggerApiConfig;
import org.skyer.tags.domain.entity.Label;
import org.skyer.tags.domain.repository.LabelRepository;
import org.skyer.tags.domain.vo.LabelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.skyer.core.domain.Page;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.mybatis.pagehelper.annotation.SortDefault;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.pagehelper.domain.Sort;
import org.skyer.swagger.annotation.Permission;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 *  管理 API
 *
 * @author xurongfa 2022-01-22 11:33:07
 */
@Api(tags = TagsSwaggerApiConfig.ORDER_LABEL_MANAGE)
@RestController("labelController.v1")
@RequestMapping("/v1/labels")
public class LabelController extends BaseController {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private LabelService labelService;


    @ApiOperation(value = "标签列表不分页")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/labelList")
    public ResponseEntity<List<LabelVO>> labelList(LabelListQueryDTO queryDTO) {
        List<LabelVO> list= labelService.labelList(queryDTO);
        return Results.success(list);
    }

    @ApiOperation(value = "列表分页")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping
    public ResponseEntity<Page<LabelVO>> list(LabelQueryDTO labelQueryDTO, @ApiIgnore @SortDefault(value = Label.FIELD_ID,
            direction = Sort.Direction.DESC) PageRequest pageRequest) {

        Page<LabelVO> list = labelService.page(pageRequest, labelQueryDTO);
        return Results.success(list);
    }

    @ApiOperation(value = "明细")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/{id}")
    public ResponseEntity<Label> detail(@PathVariable Long id) {
        Label label = labelRepository.selectByPrimaryKey(id);
        return Results.success(label);
    }

    @ApiOperation(value = "创建")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PostMapping
    public ResponseEntity<Label> create(@Valid @RequestBody LabelSaveDTO labelSaveDTO) {
        Label label = labelService.save(labelSaveDTO);
        return Results.success(label);
    }

    @ApiOperation(value = "修改")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PutMapping
    public ResponseEntity<Label> update(@Valid @RequestBody LabelSaveDTO labelSaveDTO) {
        Label label = labelService.save(labelSaveDTO);
        return Results.success(label);
    }

    @ApiOperation(value = "删除")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody Label label) {
        labelRepository.deleteByPrimaryKey(label);
        return Results.success();
    }

    @ApiOperation(value = "批量更新状态")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @PutMapping("/batch/update/status")
    public ResponseEntity<Void> batchUpdateByStatus(@Valid @RequestBody BatchUpdateStatusDTO batchUpdateStatusDTO){

        labelService.batchUpdateByStatus(batchUpdateStatusDTO.getIds(), batchUpdateStatusDTO.getStatus());
        return Results.success();
    }

    @ApiOperation(value = "批量删除")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @DeleteMapping("/batch/remove")
    public ResponseEntity<Void> batchRemove(@Valid @RequestBody IdsDTO idsDTO){

        labelService.batchRemoveByIds(idsDTO.getIds());
        return Results.success();
    }

}
