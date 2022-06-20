package org.skyer.goods.infra.repository.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.skyer.core.domain.Page;
import org.skyer.core.redis.RedisHelper;
import org.skyer.goods.api.dto.CategoryQueryDTO;
import org.skyer.goods.api.dto.LevelCategoryDTO;
import org.skyer.goods.domain.entity.Category;
import org.skyer.goods.domain.repository.CategoryRepository;
import org.skyer.goods.domain.vo.CategoryVO;
import org.skyer.goods.infra.constant.RedisKey;
import org.skyer.goods.infra.mapper.CategoryMapper;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.util.Sqls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品分类表 资源库实现
 *
 * @author xurongfa 2021-12-24 09:12:57
 */
@Component
public class CategoryRepositoryImpl extends BaseRepositoryImpl<Category> implements CategoryRepository {

    private static final Logger logger = LoggerFactory.getLogger(CategoryRepositoryImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisHelper redisHelper;

    @Override
    public int updateByPrimaryKeySelective(Category record) {
        redisHelper.delKey(RedisKey.CATEGORY_ALL);
        return super.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Category record) {
        redisHelper.delKey(RedisKey.CATEGORY_ALL);
        return super.updateByPrimaryKey(record);
    }

    @Override
    public int updateOptional(Category record, String... optionals) {
        redisHelper.delKey(RedisKey.CATEGORY_ALL);
        return super.updateOptional(record, optionals);
    }

    @Override
    public int insert(Category record) {
        redisHelper.delKey(RedisKey.CATEGORY_ALL);
        return super.insert(record);
    }

    @Override
    public int insertSelective(Category record) {
        redisHelper.delKey(RedisKey.CATEGORY_ALL);
        return super.insertSelective(record);
    }

    @Override
    public int insertOptional(Category record, String... optionals) {
        redisHelper.delKey(RedisKey.CATEGORY_ALL);
        return super.insertOptional(record, optionals);
    }

    @Override
    public int delete(Category record) {
        redisHelper.delKey(RedisKey.CATEGORY_ALL);
        return super.delete(record);
    }

    @Override
    public int deleteByPrimaryKey(Object key) {
        redisHelper.delKey(RedisKey.CATEGORY_ALL);
        return super.deleteByPrimaryKey(key);
    }

    /**
     * 获取全部分类
     * @return 全部分类
     */
    @Override
    public List<Category> getCategoryAll(){
        String categoryAllStr = redisHelper.strGet(RedisKey.CATEGORY_ALL);
        if (ObjectUtils.isEmpty(categoryAllStr)){
            List<Category> categoryList = categoryMapper.selectAll();
            redisHelper.strSet(RedisKey.CATEGORY_ALL, JSON.toJSONString(categoryList));
            logger.info("=============商品分类:数据库获取==============");
            return categoryList;
        }
        logger.info("=============商品分类:缓存获取==============");
        return JSON.parseArray(categoryAllStr, Category.class);
    }


    /**
     * 查询商品分类列表
     * @param categoryQueryDTO 通用查询对象
     * @return 商品分类
     */
    @Override
    public List<Category> selectList(CategoryQueryDTO categoryQueryDTO){
        return categoryMapper.selectList(categoryQueryDTO);
    }
    @Override
    public Page<CategoryVO> pageIncludedAttr(PageRequest pageRequest, CategoryQueryDTO category){
        return PageHelper.doPageAndSort(pageRequest,()->categoryMapper.listIncludedAttr(category));
    }

    @Override
    public String getCategoryNameById(Long categoryId) {
        List<LevelCategoryDTO> levelCategoryDTOList = Lists.newArrayList();
        Category category = this.selectByPrimaryKey(categoryId);

        if (Objects.isNull(category)) {
            return "";
        }
        LevelCategoryDTO levelCategoryDTO = new LevelCategoryDTO();
        levelCategoryDTO.setId(category.getId());
        levelCategoryDTO.setName(category.getName());
        levelCategoryDTO.setLevel(category.getLevel());
        levelCategoryDTOList.add(levelCategoryDTO);
        setFrontLevel(levelCategoryDTOList,category.getParentId());

        if (CollUtil.isEmpty(levelCategoryDTOList)){
            return "";
        }
        return levelCategoryDTOList.stream().sorted(Comparator.comparing(LevelCategoryDTO::getLevel)).map(LevelCategoryDTO::getName).collect(Collectors.joining("/"));
    }

    /**
     * 拼接上层分类
     * @param
     */
    private void setFrontLevel(List<LevelCategoryDTO> list, Long parentId) {
        if (parentId==0){
            return;
        }
        //查询上层分类
        Category parentCategory = this.selectByPrimaryKey(parentId);
        if (Objects.isNull(parentCategory)){
            return;
        }
        if (parentCategory.getLevel()>=1){
            LevelCategoryDTO levelCategoryDTO = new LevelCategoryDTO();
            levelCategoryDTO.setId(parentId);
            levelCategoryDTO.setLevel(parentCategory.getLevel());
            levelCategoryDTO.setName(parentCategory.getName());
            list.add(levelCategoryDTO);
            setFrontLevel(list,parentCategory.getParentId());
        }
    }

    @Override
    public List<Long> getLaterCategoryId(List<Long> categoryIdList) {

        List<Category> vos = this.selectByCondition(Condition.builder(Category.class)
                .andWhere(Sqls.custom()
                        .andIn(Category.FIELD_ID, categoryIdList))
                .build());
        if (CollUtil.isEmpty(vos)){
            return Collections.emptyList();
        }

        for (int i = 0; i < vos.size(); i++) {
            getLaterCategory(vos, vos.get(i).getId());
        }
        List<Long> allCategoryIdList = vos.stream().map(Category::getId).collect(Collectors.toList());
        allCategoryIdList.addAll(categoryIdList);
        return  allCategoryIdList.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public void getLaterCategory(List<Category> children, Long parentId) {
        List<Category> sonCategory = getSonCategory(parentId);
        if (CollectionUtils.isEmpty(sonCategory)){
            return;
        }
        children.addAll(sonCategory);
        for (int i = 0; i < sonCategory.size(); i++) {
            getLaterCategory(sonCategory, sonCategory.get(i).getId());
        }

    }

    @Override
    public List<Category> getSonCategory(Long parentId) {
        return categoryMapper.selectByCondition(Condition.builder(Category.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(Category.FIELD_PARENT_ID, parentId)
                        .andEqualTo(Category.FIELD_STATUS_FLAG, 1))
                .build());
    }


}
