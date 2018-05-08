package com.xgb.demoRabbitmq.enumeration;

/**
 * 消息确认机制中调用Controller后的结果枚举
 */
public interface EnumsMQAck {

    /**
     * 需要对消息ack
     */
	String ACK_OK = "ok";

    /**
     * 需要对消息nack
     */
    String ACK_FAIL = "fail";
}
