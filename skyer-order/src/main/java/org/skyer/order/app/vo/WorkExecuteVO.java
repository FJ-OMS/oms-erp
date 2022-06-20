package org.skyer.order.app.vo;

import lombok.Data;

/**
 * @description
 * @Author wusc
 * @create 2022/1/19 3:24 下午
 */
@Data
public class WorkExecuteVO {
    /**
     * 工作流运行中code
     */
    private String code;

    /**
     * 消息
     */
    private String message;

    /**
     * 结果
     */
    private String result;
}
