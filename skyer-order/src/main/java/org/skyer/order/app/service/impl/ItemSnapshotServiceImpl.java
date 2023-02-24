package org.skyer.order.app.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;
import org.skyer.order.app.service.ItemSnapshotService;
import org.skyer.order.domain.entity.Header;
import org.skyer.order.domain.entity.ItemSnapshot;
import org.skyer.order.domain.repository.ItemSnapshotRepository;
import org.skyer.order.infra.convertor.ItemSnapshotConvertor;
import org.skyer.order.infra.feign.GoodsSkuRemoteService;
import org.skyer.order.infra.feign.vo.RpcGoodsResVO;
import org.skyer.order.infra.util.CollectorsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 内部订单商品明细快照应用服务默认实现
 *
 * @author wushaochuan 2022-01-18 14:34:54
 */
@Service
@Slf4j
public class ItemSnapshotServiceImpl implements ItemSnapshotService {
    @Autowired
    private GoodsSkuRemoteService goodsSkuRemoteService;
    @Autowired
    private ItemSnapshotConvertor itemSnapshotConvertor;
    @Autowired
    private ItemSnapshotRepository itemSnapshotRepository;
    @Override
    public List<String> checkItemSnapshot(Header header,List<String> skuCodeList, Map<String, ItemSnapshot> skuSnapshotMap) {
        List<String> errorSkuCodeList = Lists.newArrayList();
        if (skuSnapshotMap == null) {
            //获取商品中心商品并生成快照
            List<ItemSnapshot> itemSnapshots = saveSnapshot(header, skuCodeList,errorSkuCodeList);
            if (CollectionUtils.isEmpty(itemSnapshots)){
                return skuCodeList;
            }
            skuSnapshotMap = itemSnapshots.stream().collect(Collectors.toMap(ItemSnapshot::getSkuCode, Function.identity(),(o1,o2) ->o1));
        }
        //没有维护价格项时候，需要调用商品中心查看是否有价格项
        List<ItemSnapshot> notPriceSnapShot = skuSnapshotMap.values().stream().filter(x -> {
            if (x.getCostPrice() == null || BigDecimal.ZERO.equals(x.getCostPrice())) {
                return true;
            }
            if (x.getSalePrice() == null || BigDecimal.ZERO.equals(x.getSalePrice())) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        List<String> noPriceSkuCodeList = notPriceSnapShot.parallelStream().map(ItemSnapshot::getSkuCode).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(noPriceSkuCodeList)){
            return errorSkuCodeList;
        }
        errorSkuCodeList.addAll(updateSnapshot(header, noPriceSkuCodeList, notPriceSnapShot));
        return errorSkuCodeList.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> updateSnapshot(Header header,List<String> skuCodeList,List<ItemSnapshot> notPriceSnapShot) {
        List<String> errorSkuList = Lists.newArrayList();
        List<ItemSnapshot> itemSnapshotList = getItemSnapshots(header, skuCodeList);
        Map<String, ItemSnapshot> itemSnapshotMap = itemSnapshotList.stream().collect(Collectors.toMap(ItemSnapshot::getSkuCode, Function.identity(), (o1, o2) -> o1));
        notPriceSnapShot.forEach(x->{
            ItemSnapshot fignItemSnapshot = itemSnapshotMap.get(x.getSkuCode());
            if (fignItemSnapshot == null){
                errorSkuList.add(x.getSkuCode());
                return;
            }
            if (fignItemSnapshot.getCostPrice() == null || BigDecimal.ZERO.equals(fignItemSnapshot.getCostPrice())) {
                errorSkuList.add(x.getSkuCode());
            }
            if (fignItemSnapshot.getSalePrice() == null || BigDecimal.ZERO.equals(fignItemSnapshot.getSalePrice())) {
                errorSkuList.add(x.getSkuCode());
            }
            x.setCostPrice(fignItemSnapshot.getCostPrice());
            x.setSalePrice(fignItemSnapshot.getSalePrice());
        });
        itemSnapshotRepository.batchUpdateByPrimaryKeySelective(notPriceSnapShot);
        return errorSkuList;
    }

    @Override
    public List<ItemSnapshot> saveSnapshot(Header header, List<String> skuCodeList,List<String> errorSkuCodeList) {
        List<ItemSnapshot> itemSnapshotList = getItemSnapshots(header, skuCodeList);
        if (itemSnapshotList == null) return Collections.emptyList();
        //判断是否已经生成快照生成的话就跳过 --平摊商品行会更新这个快照
        List<ItemSnapshot> oldItemSnapshotList = itemSnapshotRepository.querySnapShotList(header.getInnerOrderNo(), skuCodeList);
        if (CollectionUtil.isNotEmpty(oldItemSnapshotList)){
            return oldItemSnapshotList;
        }
        itemSnapshotRepository.batchInsert(itemSnapshotList.stream().map(itemSnapshot -> {itemSnapshot.setId(null); return itemSnapshot;}).collect(Collectors.toList()));
        //需要校验获取的skuList和feign的数量是否一致 不一致加入到errorSku
        List<String> fignSkuCodeList = itemSnapshotList.parallelStream().map(ItemSnapshot::getSkuCode).collect(Collectors.toList());
        errorSkuCodeList.addAll(skuCodeList.stream().filter(x -> !fignSkuCodeList.contains(x)).collect(Collectors.toList()));
        return itemSnapshotList;
    }

    @Nullable
    private List<ItemSnapshot> getItemSnapshots(Header header, List<String> skuCodeList) {
        ResponseEntity<List<RpcGoodsResVO>> goodsListResponse = goodsSkuRemoteService.queryOrderRpcList(skuCodeList.stream().distinct().collect(Collectors.toList()));
        log.info("调用商品中心接口订单号为:{},入参为:{},出参为:{}",header.getInnerOrderNo(),skuCodeList.stream().distinct().collect(Collectors.toList()), JSONObject.toJSONString(goodsListResponse));
        if (goodsListResponse == null) {
            return null;
        }
        List<RpcGoodsResVO> rpcGoodsResVoList = goodsListResponse.getBody();
        if (CollectionUtils.isEmpty(rpcGoodsResVoList)){
            return null;
        }

        return rpcGoodsResVoList.parallelStream().map(rpcGoodsResVO -> itemSnapshotConvertor.skuToItemSnapshot(header, rpcGoodsResVO)).collect(Collectors.toList());
    }


}
