package org.skyer.stock.api.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.skyer.core.base.BaseController;
import org.skyer.core.iam.ResourceLevel;
import org.skyer.core.util.Results;
import org.skyer.starter.keyencrypt.core.Encrypt;
import org.skyer.stock.api.dto.StockChangeListDTO;
import org.skyer.stock.config.StockSwaggerApiConfig;
import org.skyer.stock.domain.service.ChangeLogService;
import org.skyer.stock.domain.vo.StockChangeVO;
import org.skyer.swagger.annotation.Permission;

/**
 * 库存流水表 管理 API
 *
 * @author lixiaoyang 2021-08-03 16:51:06
 */
@Api(tags = StockSwaggerApiConfig.CHANGE_LOG_MANAGE)
@RestController("changeLogController.v1")
@RequestMapping("/v1/change-logs")
public class ChangeLogController extends BaseController {
    @Autowired
    private ChangeLogService changeLogService;

    @ApiOperation(value = "库存变动查询")
    @Permission(level = ResourceLevel.ORGANIZATION)
    @PostMapping("/page")
    public ResponseEntity<StockChangeVO> page(@RequestBody @Encrypt StockChangeListDTO dto) {
        StockChangeVO vo = changeLogService.getStockChangePage(dto);
        return Results.success(vo);
    }
}
