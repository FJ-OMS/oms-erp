package org.skyer.goods.infra.constant;

/**
 * @description
 * @Author wusc
 * @create 2021/6/25 3:27 下午
 */
public  class ObjectConstant {

    public static class Status {
        /**
         * 启用
         */
        public static final Integer OPEN = 1;

        /**
         * 警用
         */
        public static final Integer CLOSE = 0;
    }


    public static class DeleteFlag {
        /**
         * 未删除
         */
        public static final Integer NO = 0;

        /**
         * 删除
         */
        public static final Integer YES = 1;
    }

    public static class AttributeType {
        /**
         * 属性关联分类
         */
        public static final Integer CATEGORY_TYPE = 0;

        /**
         * 属性关联商品
         */
        public static final Integer SPU_TYPE = 1;
    }


    public static class AttributeValueType {
        /**
         * 单选
         */
        public static final Integer SINGLE = 1;

        /**
         * 多选
         */
        public static final Integer MULTIPLE = 2;

        /**
         * 文本
         */
        public static final Integer TEXT = 3;

        /**
         * 下拉
         */
        public static final Integer PULL_DOWN = 4;
    }

    public static class CodeRule {

        public static final String ATTRIBUTE_CODE = "HPFM.COMPANY";

        public static final String BRAND_CODE = "HPFM.COMPANY";

        public static final String CATEGORY_CODE = "HPFM.COMPANY";

        public static final String GOODS_CODE = "HPFM.COMPANY";

        public static final String SKU_CODE = "HPFM.COMPANY";
    }

    public static class LevelValue {
        public static final String ATTRIBUTE_LEVEL = "GLOBAL";

        public static final String BRAND_LEVEL = "GLOBAL";

        public static final String CATEGORY_LEVEL = "GLOBAL";

        public static final String GOODS_LEVEL = "GLOBAL";

        public static final String SKU_LEVEL = "GLOBAL";
    }
    public static Integer FORM_TYPE_TEXT= 3;

    public static class EsKey {
        public static final String SPU_INDEX = "goods_spu_index_01";

        public static final String SKU_INDEX = "goods_sku_index_01";
    }


}
