package com.xt.m_mqtt;

/**
 * Created by xuti on 2018/9/29.
 */
public interface IMqttResponseMsgHandler {
    void onMqttResponse(String topic, String msg);
}
