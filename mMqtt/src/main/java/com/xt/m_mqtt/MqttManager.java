package com.xt.m_mqtt;


/**
 * Created by Administrator on 2017/2/23.
 * 支持mqtt连接，断开，订阅，发布，添加和移除监听器
 */

public class MqttManager {
    private static final String TAG = MqttManager.class.getSimpleName();
    private static MqttManager instance = null;
    private MqttClientControl mqttClientControl;
    private MqttResponseCallbackManager mqttResponseCallbackManager;

//    private String createTime;

    private MqttManager() {
//        createTime = TimeUtils.date2String(TimeUtils.getNowDate());
        mqttClientControl = new MqttClientControl();
        mqttResponseCallbackManager = new MqttResponseCallbackManager();
    }

    public static MqttManager getInstance() {
        if (instance == null) {
            instance = new MqttManager();
        }
        return instance;
    }

    void addCallback(MqttResponseCallback callback) {
        mqttResponseCallbackManager.addCallback(callback);
    }

    void removeCallback(MqttResponseCallback callback) {
        mqttResponseCallbackManager.removeCallback(callback);
    }

    //订阅到服务器
    public boolean mqttConn() {
        return mqttClientControl.mqttConn();
    }

    //断开
    public void mqttDisConn() {
        mqttClientControl.disConn();
    }
    //关闭
    public void mqttClose() {
        mqttClientControl.close();
    }

    //订阅到服务器
    public void mqttSubscribe(String subscribeTopic) {
        mqttClientControl.mqttSubscribe(subscribeTopic);

    }

    //取消订阅到服务器
    public void mqttUnsubscribe(String subscribeTopic) {
        mqttClientControl.mqttUnsubscribe(subscribeTopic);

    }

    //发布到服务器
    public void mqttPublish(String publishTopic, String json) {
        mqttClientControl.mqttPublish(publishTopic, json);
    }

    public MqttResponseCallbackManager getMqttResponseCallbackManager() {
        return mqttResponseCallbackManager;
    }


    public boolean isConnected() {
        return mqttClientControl.getMqttClient().isConnected();
    }
}
