package org.skyer.goods.infra.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import org.skyer.core.exception.CommonException;

import org.skyer.core.base.BaseConstants;
import org.skyer.core.util.ResponseUtils;

/**
 * Json工具类
 *
 * @author
 * @see ResponseUtils
 */
public class JsonUtils {

    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() {}

    /**
     * 校验Json
     * <p>
     * </p>
     * 如果为标准错误
     * <p>
     * </p>
     * {"failed": true, "meesage": "error message..."}
     * <p>
     * </p>
     * 则抛出CommonException
     * <p>
     * </p>
     * 如果入参不是一个合法的json,则返回null
     *
     * @param json
     * @param objectMapper
     * @return 由Json转化而来的JsonNode或null
     */
    public static JsonNode checkResultStatus(String json, ObjectMapper objectMapper) {
        // 空json不校验
        if (StringUtils.isBlank(json)) {
            return null;
        }
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(json);
        } catch (IOException e) {
            // 源字符串不是json,忽略
            return null;
        }
        return checkResultStatus(jsonNode);
    }

    /**
     * 校验JsonNode
     * <p>
     * </p>
     * 如果为标准错误
     * <p>
     * </p>
     * {"failed": true, "meesage": "error message..."}
     * <p>
     * </p>
     * 则抛出CommonException
     * <p>
     * </p>
     * 如果入参不是一个合法的json,则返回null
     *
     * @param jsonNode
     * @return 通过校验的JsonNode, 如果入参jsonNode为空或者jsonNode中没有数据, 则返回null
     */
    public static JsonNode checkResultStatus(JsonNode jsonNode) {
        // 空json不校验
        if (jsonNode == null) {
            return null;
        }
        if (jsonNode.isNull() || jsonNode.isMissingNode()) {
            return null;
        }
        if (jsonNode.isObject()) {
            JsonNode keyNode = jsonNode.get(BaseConstants.FIELD_FAILED);
            if (keyNode != null && keyNode.isBoolean() && keyNode.asBoolean()) {
                JsonNode messageNode = jsonNode.get(BaseConstants.FIELD_MSG);
                throw new CommonException(messageNode == null ? StringUtils.EMPTY : messageNode.toString());
            }
        }
        return jsonNode;
    }


    /**
     * 校验Json,并解析为指定类型
     *
     * @param json Json
     * @param objectMapper {@link ObjectMapper}
     * @param clazz 指定类型Class
     * @return 解析后的对象
     */
    public static <T> T checkAndParseObject(String json, ObjectMapper objectMapper, Class<T> clazz) {
        JsonNode jsonNode = checkResultStatus(json, objectMapper);
        if (jsonNode == null) {
            return null;
        }
        Assert.isTrue(!jsonNode.isArray(), "this json is an array, please user JsonUtil.checkAndParseList");
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new CommonException(e);
        }
    }

    /**
     * 校验JsonNode,并解析为指定类型
     *
     * @param jsonNode JsonNode
     * @param objectMapper {@link ObjectMapper}
     * @param clazz 指定类型Class
     * @return 解析后的对象
     */
    public static <T> T checkAndParseObject(JsonNode jsonNode, ObjectMapper objectMapper, Class<T> clazz) {
        jsonNode = checkResultStatus(jsonNode);
        if (jsonNode == null) {
            return null;
        }
        Assert.isTrue(!jsonNode.isArray(), "this json is an array, please user JsonUtil.checkAndParseList");
        try {
            return objectMapper.readValue(jsonNode.toString(), clazz);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new CommonException(e);
        }
    }

    /**
     * 校验Json,并解析为指定类型的List
     *
     * @param json Json
     * @param objectMapper {@link ObjectMapper}
     * @param clazz 指定类型Class
     * @return 解析后的List
     */
    public static <T> List<T> checkAndParseList(String json, ObjectMapper objectMapper, Class<T> clazz) {
        JsonNode jsonNode = checkResultStatus(json, objectMapper);
        if (jsonNode == null) {
            return null;
        }
        Assert.isTrue(jsonNode.isArray(), "this json is not an array, please user JsonUtil.checkAndParseObject");
        Iterator<JsonNode> iterator = jsonNode.elements();
        JsonNode child;
        List<T> result = new ArrayList<>();
        try {
            while (iterator.hasNext()) {
                child = iterator.next();
                result.add(objectMapper.readValue(child.toString(), clazz));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new CommonException(e);
        }
        return result;
    }

    /**
     * 校验 JsonNode,并解析为指定类型的List
     *
     * @param jsonNode JsonNode
     * @param objectMapper {@link ObjectMapper}
     * @param clazz 指定类型Class
     * @return 解析后的List
     */
    public static <T> List<T> checkAndParseList(JsonNode jsonNode, ObjectMapper objectMapper, Class<T> clazz) {
        jsonNode = checkResultStatus(jsonNode);
        if (jsonNode == null) {
            return null;
        }
        Assert.isTrue(jsonNode.isArray(), "this json is not an array, please user JsonUtil.checkAndParseObject");
        Iterator<JsonNode> iterator = jsonNode.elements();
        JsonNode child;
        List<T> result = new ArrayList<>();
        try {
            while (iterator.hasNext()) {
                child = iterator.next();
                result.add(objectMapper.readValue(child.toString(), clazz));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new CommonException(e);
        }
        return result;
    }

}
