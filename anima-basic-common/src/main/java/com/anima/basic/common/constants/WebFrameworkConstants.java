package com.anima.basic.common.constants;

/**
 * WEB框架常量定义
 *
 * @author hww
 */
public interface WebFrameworkConstants {

    /**
     * 系统名称
     */
    String SYSTEM_NAME = "anima";

    /**
     * http jwt验证令牌
     */
    String AUTH_TOKEN = SYSTEM_NAME + "_AUTH_TOKEN";

    /**
     * APPID参数名称
     */
    String CODE_APP_ID = SYSTEM_NAME + "_APP_ID";

    /**
     * TIMESTAMP参数名称
     */
    String CODE_TIMESTAMP = SYSTEM_NAME + "_TIMESTAMP";

    /**
     * SIGNATURE参数名称
     */
    String CODE_SIGNATURE = SYSTEM_NAME + "_SIGNATURE";

    /**
     * TERMINAL_ID参数名称
     */
    String CODE_TERMINAL_IP = SYSTEM_NAME + "_TERMINAL_ID";

    /**
     * TERMINAL_TYPE参数名称
     */
    String CODE_TERMINAL_TYPE = SYSTEM_NAME + "_TERMINAL_TYPE";

    /**
     * TRANSACTION_ID参数名称
     */
    String CODE_TRANSACTION_ID = SYSTEM_NAME + "_TRANSACTION_ID";

    /**
     * 未知用户编号，默认为-1
     */
    long UNKNOWN_USER_ID = -1;

    /**
     * 系统用户编号
     */
    long SYSTEM_USER_ID = 0;

    /**
     * 未知终端类型
     */
    String UNKNOWN_TERMINAL_TYPE = "-1";

    /**
     * 分页最大长度
     */
    Integer MAX_PAGE_SIZE = Integer.MAX_VALUE;

    /**
     * 分页默认长度
     */
    Integer DEFAULT_PAGE_SIZE = 10;
}
