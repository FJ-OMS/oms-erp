package org.skyer.goods.domain.service.impl;

import org.skyer.goods.domain.service.GoodsAttributeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.skyer.core.cache.ProcessCacheValue;
import org.skyer.core.domain.Page;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
* <p>
*  商品属性表服务实现类
* </p>
* @author wusc
* @since 2021-06-24
*/
@Service
public class GoodsAttributeServiceImpl implements GoodsAttributeService {

private static final Logger logger = LoggerFactory.getLogger(GoodsAttributeServiceImpl.class);
}