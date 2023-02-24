package org.skyer.order.api.controller.v1;

import org.skyer.core.util.Results;
import org.skyer.core.base.BaseController;
import org.skyer.order.api.dto.ProcessDefineDTO;
import org.skyer.order.api.dto.ProcessDefinePageDTO;
import org.skyer.order.api.vo.ProcessDefineDetailsVO;
import org.skyer.order.api.vo.ProcessDefinePageVO;
import org.skyer.order.app.service.ProcessDefineService;
import org.skyer.order.domain.entity.ProcessDefine;
import org.skyer.order.domain.repository.ProcessDefineRepository;
import org.skyer.starter.keyencrypt.core.Encrypt;
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

import java.io.UnsupportedEncodingException;

/**
 *  管理 API
 *
 * @author linzhuanghuang@ebc.com 2022-01-14 17:53:17
 */
@RestController("processDefineController.v1")
@RequestMapping("/v1/process-defines")
public class ProcessDefineController extends BaseController {

    @Autowired
    private ProcessDefineRepository processDefineRepository;
    @Autowired
    private ProcessDefineService processDefineService;


    @ApiOperation(value = "流程列表")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping
    public ResponseEntity<Page<ProcessDefinePageVO>> list(ProcessDefinePageDTO processDefine, PageRequest pageRequest) {
        Page<ProcessDefinePageVO> list = processDefineRepository.processPage(pageRequest, processDefine);
        return Results.success(list);
    }

    @ApiOperation(value = "流程详情")
    @Permission(level = ResourceLevel.SITE)
    @GetMapping("/{id}")
    public ResponseEntity<ProcessDefineDetailsVO> detail(@PathVariable @Encrypt Long id) {
        ProcessDefineDetailsVO processDefine = processDefineRepository.queryProcessDetails(id);
        return Results.success(processDefine);
    }

    @ApiOperation(value = "创建流程定义")
    @Permission(level = ResourceLevel.SITE)
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ProcessDefineDTO processDefine) throws Exception {
        processDefineService.saveProcessDefine(processDefine);
        return Results.success();
    }

    @ApiOperation(value = "修改流程")
    @Permission(level = ResourceLevel.SITE)
    @PutMapping
    public ResponseEntity<String> update(@RequestBody ProcessDefineDTO processDefine) {
        processDefineRepository.updateProcessDefine(processDefine);
        return Results.success("操作成功");
    }

    @ApiOperation(value = "删除流程")
    @Permission(level = ResourceLevel.SITE)
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestBody ProcessDefine processDefine) {
        processDefineRepository.deleteProcess(processDefine);
        return Results.success();
    }

    @ApiOperation(value = "流程部署")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/deploy-process/{id}")
    public ResponseEntity<String> deployProcess(@PathVariable @Encrypt Long id) {
        processDefineService.updateDeploy(id);
        return Results.success();
    }



}
