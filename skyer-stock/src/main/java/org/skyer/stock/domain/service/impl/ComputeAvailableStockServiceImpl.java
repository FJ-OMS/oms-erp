package org.skyer.stock.domain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import org.skyer.stock.api.dto.GoodsStockDTO;
import org.skyer.stock.domain.entity.GoodsStockDetail;
import org.skyer.stock.domain.repository.GoodsStockDetailRepository;
import org.skyer.stock.domain.service.ComputeAvailableStockService;
import org.skyer.stock.domain.service.StockEsService;

/**
 * 可用库存计算服务实现
 *
 * @author lixiaoyang
 * @date 2021/9/7 11:20
 */
@Service
public class ComputeAvailableStockServiceImpl implements ComputeAvailableStockService {
    private static final Logger logger = LoggerFactory.getLogger(ComputeAvailableStockServiceImpl.class);
    @Autowired
    private StockEsService stockEsService;
    @Autowired
    private GoodsStockDetailRepository goodsStockDetailRepository;


    @Override
    public void asyncEs() {
        List<GoodsStockDetail> goodsStockDetails = goodsStockDetailRepository.selectAll();
        if (ObjectUtils.isEmpty(goodsStockDetails)){
            return;
        }
        Long warehouseId = goodsStockDetails.get(0).getWarehouseId();
        List<GoodsStockDTO> dtoList = new ArrayList<>();
        for (GoodsStockDetail goodsStockDetail : goodsStockDetails) {
            GoodsStockDTO goodsStockDTO = new GoodsStockDTO();
            BeanUtils.copyProperties(goodsStockDetail,goodsStockDTO);
            dtoList.add(goodsStockDTO);
        }
        stockEsService.createOrUpdateStockEs(dtoList, warehouseId);
    }
}
