package org.skyer.order;

import okhttp3.Headers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyer.order.app.service.OrderEsService;
import org.skyer.order.domain.entity.Header;
import org.skyer.order.domain.entity.Item;
import org.skyer.order.domain.entity.ItemLine;
import org.skyer.order.domain.repository.HeaderRepository;
import org.skyer.order.domain.repository.ItemLineRepository;
import org.skyer.order.domain.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description
 * @Author wusc
 * @create 2022/2/9 10:04 上午
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class OrderEsTest {
    @Autowired
    private OrderEsService orderEsService;
    @Autowired
    private HeaderRepository headerRepository;
    @Autowired
    private ItemLineRepository itemLineRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void EsOrderTest() {
        List<String> collect = headerRepository.selectAll().stream().filter(x -> Objects.nonNull(x.getInnerOrderNo())).map(Header::getInnerOrderNo).collect(Collectors.toList());

        orderEsService.batchInsertOrderToEs(collect);
    }

    @Test
    public void updateItemLine() {
//        List<ItemLine> lines = itemLineRepository.selectAll();
//        List<ItemLine> collect = lines.stream().map(item -> {
//            Header header = headerRepository.queryHeaderByInnerOrderNo(item.getInnerOrderNo());
//            item.setStoreId(header.getStoreId());
//            return item;
//        }).collect(Collectors.toList());
//        itemLineRepository.batchUpdateByPrimaryKeySelective(collect);
        List<Item> lines = itemRepository.selectAll();
        List<Item> collect = lines.stream().map(item -> {
            Header header = headerRepository.queryHeaderByInnerOrderNo(item.getInnerOrderNo());
            item.setStoreId(header.getStoreId());
            return item;
        }).collect(Collectors.toList());
        itemRepository.batchUpdateByPrimaryKeySelective(collect);
    }
}
