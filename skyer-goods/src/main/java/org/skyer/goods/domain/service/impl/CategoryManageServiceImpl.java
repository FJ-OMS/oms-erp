package org.skyer.goods.domain.service.impl;

import org.skyer.core.domain.Page;
import org.skyer.core.exception.CommonException;
import org.skyer.goods.api.dto.CategoryQueryDTO;
import org.skyer.goods.api.dto.CategorySaveDTO;
import org.skyer.goods.api.dto.CategoryTreeQueryDTO;
import org.skyer.goods.api.dto.CategoryUpdateByStatusFlagDTO;
import org.skyer.goods.domain.entity.Category;
import org.skyer.goods.domain.entity.CategoryAttribute;
import org.skyer.goods.domain.entity.CategoryAttributeValue;
import org.skyer.goods.domain.entity.Spu;
import org.skyer.goods.domain.repository.CategoryAttributeRepository;
import org.skyer.goods.domain.repository.CategoryAttributeValueRepository;
import org.skyer.goods.domain.repository.CategoryRepository;
import org.skyer.goods.domain.repository.SpuRepository;
import org.skyer.goods.domain.service.CategoryManageService;
import org.skyer.goods.domain.vo.CategoryTreeVO;
import org.skyer.goods.domain.vo.CategoryVO;
import org.skyer.goods.infra.convertor.CategoryConvertor;
import org.skyer.goods.infra.util.ListUtils;
import org.skyer.mybatis.domian.Condition;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.mybatis.util.Sqls;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CategoryManageServiceImpl implements CategoryManageService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryAttributeRepository categoryAttributeRepository;
    @Autowired
    private CategoryAttributeValueRepository categoryAttributeValueRepository;
    @Autowired
    private SpuRepository spuRepository;

    @Autowired
    private CategoryConvertor categoryConvertor;

    /**
     * 根据分类id 删除属性及属性值
     * @param categoryId 分类id
     */
    public void deleteAttributeAndValue(Long categoryId){
        //删除属性值 必须先删除属性值
        categoryAttributeValueRepository.deleteByCategoryId(categoryId);

        //删除属性
        CategoryAttribute categoryAttribute = new CategoryAttribute();
        categoryAttribute.setCategoryId(categoryId);
        List<CategoryAttribute> categoryAttributeList =
                categoryAttributeRepository.select(CategoryAttribute.FIELD_CATEGORY_ID, categoryId);
        categoryAttributeRepository.batchDelete(categoryAttributeList);

    }

    /**
     * 更新或添加分类，包括 属性和属性值
     * @param categorySaveDTO 分类信息
     */
    @Transactional
    public void save(CategorySaveDTO categorySaveDTO){
        //DTO转换为实体对象
        Category category = categoryConvertor.CategorySaveDTOTOCategory(categorySaveDTO);

        // 校验所选父级分类，并重置分类级别
        Long pid = category.getParentId();
        if (pid != null && pid != 0L){
            if (pid.equals(categorySaveDTO.getId())){
                // 父类不能为自己
                throw new CommonException("父级不能是自己");
            }
            Category pCategory = categoryRepository.selectByPrimaryKey(pid);
            if (ObjectUtils.isEmpty(pCategory)){
                // 所选父类不存在，或已被删除
                throw new CommonException("所选父级不存在或已被删除");
            }
            if (pCategory.getSubFlag()) {
                // 所选父类已经是叶子分类
                throw new CommonException("所选父级是叶子分类，叶子分类不允许有下级");
            }
            if (!pCategory.getStatusFlag()){
                // 所选父类已被禁用
                throw new CommonException("所选父级已被禁用");
            }

            category.setLevel(pCategory.getLevel() + 1);
        }else{
            category.setLevel(1);
        }

        //重置一级类目父id为0
        if (pid == null){
            category.setParentId(0L);
        }
        /* 校验当前分类数据 */
        //校验属性名称或属性编码是否有重复
        List<CategoryAttribute> attributeList = category.getAttributeList();
        if (!ObjectUtils.isEmpty(attributeList)){
            //过滤出重复的属性名称
            List<String> repeatNameList = ListUtils.getRepeatList(
                    attributeList, CategoryAttribute::getName);
            if (!ObjectUtils.isEmpty(repeatNameList)){
                throw new CommonException(
                        MessageFormat.format("存在重复的属性名称({0})",
                                String.join(",", repeatNameList)));
            }
            //过滤出重复的属性编码
            List<String> repeatCodeList = ListUtils.getRepeatList(attributeList, CategoryAttribute::getCode);
            if (!ObjectUtils.isEmpty(repeatCodeList)){
                throw new CommonException(
                        MessageFormat.format("存在重复的属性编码({0})",
                                String.join(",", repeatCodeList)));
            }
        }

        //校验属性值
        List<String> repeatInfo = attributeList.stream().map(x -> {
            List<CategoryAttributeValue> attributeValueList = x.getAttributeValueList();
            //校验非文本框类型，是否有属性值
            if (x.getFormType() != 3 && ObjectUtils.isEmpty(attributeValueList)) {
                return x.getName() + ":非文本框至少需要输入一个属性值";
            }
            //校验文本框类型，是否没有属性值
            if (x.getFormType() == 3 && !ObjectUtils.isEmpty(attributeValueList)){
                return x.getName() + ":文本框不应该有属性值";
            }

            //校验是否重复
            if (!ObjectUtils.isEmpty(attributeValueList)) {
                //过滤出重复的值
                List<String> repeatValueList = ListUtils.getRepeatList(
                        attributeValueList, CategoryAttributeValue::getValue);
                if (!ObjectUtils.isEmpty(repeatValueList)) {
                    //重复属性值，带有属性名称
                    return x.getName() + ":属性值重复(" + String.join(",", repeatValueList) + ")";
                }
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        if (!ObjectUtils.isEmpty(repeatInfo)){
            throw new CommonException(
                    MessageFormat.format("分类属性值有误。({0})",
                            String.join(",", repeatInfo)));
        }

        //校验名称
        int nameCount = categoryRepository.selectCountByCondition(Condition.builder(Category.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(Category.FIELD_PARENT_ID, category.getParentId())
                        .andEqualTo(Category.FIELD_NAME, category.getName())
                        .andNotEqualTo(Category.FIELD_ID, category.getId(), true))
                .build());
        if (nameCount > 0){
            throw new CommonException(
                    MessageFormat.format("所选父级分类下已有该分类名称({0})",category.getName()));
        }
        //校验编码
        if (!ObjectUtils.isEmpty(category.getCode())) {
            int codeCount = categoryRepository.selectCountByCondition(Condition.builder(Category.class)
                    .andWhere(Sqls.custom()
                            .andEqualTo(Category.FIELD_CODE, category.getCode())
                            .andNotEqualTo(Category.FIELD_ID, category.getId(), true))
                    .build());
            if (codeCount > 0) {
                throw new CommonException("分类编码已存在");
            }
        }
        /*修改时的校验*/
        if (!ObjectUtils.isEmpty(category.getId())){
            // 删除 父类信息避免被修改
            category.setParentId(null);
            //校验 修改为叶子分类时，是否有下级
            if (category.getSubFlag()){
                int subCount = categoryRepository.selectCountByCondition(Condition.builder(Category.class)
                        .andWhere(Sqls.custom().andEqualTo(Category.FIELD_PARENT_ID, category.getId()))
                        .build());
                if (subCount > 0){
                    throw new CommonException("已有下级，不允许修改为叶子分类");
                }
            }
            //校验 修改为普通分类时，是否已有商品
            if (!category.getSubFlag()){
                int spuCount = spuRepository.selectCountByCondition(Condition.builder(Spu.class)
                        .andWhere(Sqls.custom().andEqualTo(Spu.CATEGORY_ID, category.getId()))
                        .build());
                if (spuCount > 0){
                    throw new CommonException("已有商品，不允许修改为普通分类");
                }
            }
        }

        /*处理分类*/
        if (ObjectUtils.isEmpty(category.getId())){
            // id为空直接插入，插入之后带出id
            categoryRepository.insertSelective(category);
        }else{
            categoryRepository.updateByPrimaryKeySelective(category);
            //删除原有属性及属性值
            this.deleteAttributeAndValue(category.getId());
        }

        /*处理分类属性*/
        //赋予分类id
        attributeList.forEach(x->x.setCategoryId(category.getId()));
        //批量插入属性值
        List<CategoryAttribute> attributeListNew = categoryAttributeRepository.batchInsertSelective(attributeList);

        /*处理属性值*/
        //收集属性值集合，并赋予属性值 属性id
        List<CategoryAttributeValue> valueList = attributeListNew.stream().flatMap(
                x -> {
                    List<CategoryAttributeValue> attributeValueList = x.getAttributeValueList();
                    if (!ObjectUtils.isEmpty(attributeValueList)) {
                        //赋予属性id
                        return attributeValueList.stream().peek(y -> y.setAttributeId(x.getId()));
                    }
                    return null;
                }
        ).filter(Objects::nonNull).collect(Collectors.toList());

        if (!ObjectUtils.isEmpty(valueList)){
            // 批量插入分类属性值
            categoryAttributeValueRepository.batchInsertSelective(valueList);
        }
    }

    /**
     * 修改商品分类状态
     */
    public void updateByStatusFlag(CategoryUpdateByStatusFlagDTO categoryUpdateByStatusFlagDTO){
        Category category = new Category();
        BeanUtils.copyProperties(categoryUpdateByStatusFlagDTO, category);
        categoryRepository.updateByPrimaryKeySelective(category);
    }
    /**
     * 查询分类是否有下级，包括分类及商品
     * @param category 分类对象
     * @return true有下级
     */
    public boolean hasCategorySub(Category category){
        if (category.getSubFlag()){
            //叶子节点，获取是否有商品
            List<Spu> spuList = spuRepository.select(Spu.CATEGORY_ID, category.getId());
            return !ObjectUtils.isEmpty(spuList);
        }else {
            //查询是否存在下级
            List<Category> categoryList = categoryRepository.select(Category.FIELD_PARENT_ID, category.getId());
            return !ObjectUtils.isEmpty(categoryList);
        }
    }

    /**
     * 批量删除分类
     * @param ids 删除的分类id集合
     */
    @Override
    @Transactional
    public void batchDelete(List<Long> ids) {
        ids.forEach(id->{
            Category category = categoryRepository.selectByPrimaryKey(id);
            if (ObjectUtils.isEmpty(category)) return;
            if (hasCategorySub(category)){
                throw new CommonException(MessageFormat.format("分类({0})已有下级分类或商品，不允许删除", category.getName()));
            }
            categoryRepository.deleteByPrimaryKey(category);
            //删除属性及属性值
            this.deleteAttributeAndValue(category.getId());
        });
    }

    /**
     * 判断分类树子孙是否有叶子分类。且只保留叶子分类或含有叶子分类的孩子
     * @param categoryTreeVO 分类树
     * @return 子孙是否有叶子分类
     */
    private boolean filterNotSubNode(CategoryTreeVO categoryTreeVO){
        if (categoryTreeVO.getSubFlag()){
            return true;
        }
        List<CategoryTreeVO> children = categoryTreeVO.getChildren();
        if (!ObjectUtils.isEmpty(children)){
            List<CategoryTreeVO> categoryTreeVOList = children.stream().filter(this::filterNotSubNode).collect(Collectors.toList());
            categoryTreeVO.setChildren(categoryTreeVOList);
            return !ObjectUtils.isEmpty(categoryTreeVOList);
        }
        return false;
    }

    @Override
    public List<CategoryTreeVO> getCategoryTree(CategoryTreeQueryDTO queryDTO) {
        CategoryQueryDTO categoryQueryDTO = categoryConvertor.CategoryTreeQueryDTOToCategoryQueryDTO(queryDTO);
        List<Category> categoryList = categoryRepository.selectList(categoryQueryDTO);

        //对象映射为树对象
        List<CategoryTreeVO> categoryTreeVOList = categoryList.stream().map(x->{
            CategoryTreeVO categoryTreeVO = new CategoryTreeVO();
            BeanUtils.copyProperties(x, categoryTreeVO);
            return categoryTreeVO;
        }).collect(Collectors.toList());

        //分组 父id
        Map<Long, List<CategoryTreeVO>> groupMap = categoryTreeVOList.stream()
                .collect(Collectors.groupingBy(CategoryTreeVO::getParentId));

        categoryTreeVOList.forEach(x-> x.setChildren(groupMap.get(x.getId())));
        Stream<CategoryTreeVO> categoryTreeVOStream = categoryTreeVOList.stream()
                .filter(x -> x.getParentId() == 0) //过滤0一级节点
                .peek(x -> x.setParentId(null));//设置一级节点 父节点为null

        if (ObjectUtils.nullSafeEquals(true, queryDTO.getRemoveNotSubFlag()) ){
            //过滤没有叶子节点的普通节点
            categoryTreeVOStream = categoryTreeVOStream.filter(this::filterNotSubNode);
        }

        return categoryTreeVOStream.collect(Collectors.toList());
    }

    /**
     * 查询单级分类，包含属性
     * @param categoryQueryDTO 分类查询条件对象
     * @return 分页数据
     */
    public Page<CategoryVO> pageIncludedAttr(CategoryQueryDTO categoryQueryDTO){

        PageRequest pageRequest = new PageRequest();
        BeanUtils.copyProperties(categoryQueryDTO, pageRequest);

        categoryQueryDTO.setSort(null); //避免前端传递列表排序（pageRequest内的sort），与通用查询条件冲突

        Page<CategoryVO> categoryVOPage = categoryRepository.pageIncludedAttr(pageRequest, categoryQueryDTO);

        //处理返回值，父级为0时，设置为null
        List<CategoryVO> categoryList = categoryVOPage.getContent();
        List<CategoryVO> collect = categoryList.stream().peek(x -> {

            if (x.getParentId() == 0) {
                x.setParentId(null);
            }
        }).collect(Collectors.toList());

        categoryVOPage.setContent(collect);

        return categoryVOPage;
    }
}