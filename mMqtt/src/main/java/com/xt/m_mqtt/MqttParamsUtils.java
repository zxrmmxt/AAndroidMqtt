package com.xt.m_mqtt;

import android.support.annotation.NonNull;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 * Created by xuti on 2017/10/18.
 */

public class MqttParamsUtils {
    @NonNull
    static String getMqttServerUrl(MqttBean mqttBean) {
        return "tcp://" + mqttBean.getIP() + ":" + mqttBean.getPort();
    }

    /**
     * 从外部获取mqtt配置参数
     */
    static MqttBean getMqttProperties() {
        MqttBean mqttBean = new MqttBean();
        String clientId = "";
        mqttBean.setClientID(clientId);
        String mqttIp = "";
        mqttBean.setIP(mqttIp);
        String mqttPort = "";
        mqttBean.setPort(mqttPort);
        String mqttUserName = "";
        mqttBean.setUserName(mqttUserName);
        String mqttPassword = "";
        mqttBean.setPassWord(mqttPassword);
//            testParams(mqttBean);
        return mqttBean;
    }

    static MqttConnectOptions getConnOpts() {
        MqttBean mqttBean = getMqttProperties();
        MqttConnectOptions connOpts = new MqttConnectOptions();
        String mqttServerUrl = getMqttServerUrl(mqttBean);
//        XTLogUtil.d(TAG, "Connecting to serverURI: " + mqttServerUrl);
        connOpts.setUserName(mqttBean.getUserName());
        connOpts.setServerURIs(new String[]{mqttServerUrl});
        connOpts.setPassword(mqttBean.getPassWord().toCharArray());
        connOpts.setCleanSession(true);
        connOpts.setKeepAliveInterval(60);
        return connOpts;
    }

    static void testParams(MqttBean mqttBean) {
        mqttBean.setClientID("200300ATsF7Fs6sF7v");
        mqttBean.setIP("mqttserver.iot.582.so");
        mqttBean.setPort("61060");
        mqttBean.setUserName("23A180A13937B65F2A71876E1C65E414");
        mqttBean.setPassWord("1FDD4D98A85B823B4AE73927089EEFB2");
    }
}
