package com.xt.a_mqtt;

import com.xt.m_common_utils.MLogUtil;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by xuti on 2017/10/18.
 */

public class MqttClientControl {
    private static final String TAG = MqttClientControl.class.getSimpleName();
    private MqttClient mqttClient;

    private void initMqttClient() throws MqttException {
        if (mqttClient != null) {
            if (mqttClient.isConnected()) {
                disConn();
                close();
            }
        }
        MqttBean mqttBean = MqttParamsUtils.getMqttProperties();
        mqttClient = new MqttClient(MqttParamsUtils.getMqttServerUrl(mqttBean), mqttBean.getClientID(), new MemoryPersistence());
        mqttClient.setCallback(new AMqttCallback());
    }

    //连接到到服务器,退出应用前未断开mqtt连接，下次进来时，mqttClient状态为未连接，调用连接的方法时会连接失败，应该是应用上次和服务器的连接还在，退出应用是否要断开
    boolean mqttConn() {
        try {
            initMqttClient();
            return doConnect();
        } catch (MqttException e) {
            e.printStackTrace();
            MLogUtil.d(TAG, "mqtt---------mqttConn()连接失败----->" + e.getMessage());
            onConnectLost(e);
            return false;
        }
    }

    void disConn() {
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    void close() {
        try {
            mqttClient.close();
            mqttClient = null;
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private boolean doConnect() throws MqttException {
        //            XTLogUtil.d(TAG, "mqttComm-------------->mqtt正在连接");
        IMqttToken iMqttToken = mqttClient.connectWithResult(MqttParamsUtils.getConnOpts());
        if (iMqttToken.isComplete()) {
            MLogUtil.d(TAG, "mqtt-------------->连接成功");
//            MqttManager.getInstance().getMqttResponseCallbackManager().onConnectionSuccess();
//            MqttReconnectManager.getInstance().onConnectionSuccess();
            return true;
        } else {
            return false;
        }
    }

    //订阅到服务器
    void mqttSubscribe(String subscribeTopic) {
        if (mqttClient == null) {
            return;
        }
        try {
            mqttClient.subscribe(subscribeTopic, 1);
//            XTLogUtil.d(TAG, "订阅成功");
        } catch (MqttException e) {
            e.printStackTrace();
            MLogUtil.d(TAG, "mqtt---------mqttSubscribe---" + subscribeTopic + "连接断开----->" + e.getMessage());
            onConnectLost(e);
        }
    }

    //取消订阅到服务器
    void mqttUnsubscribe(String subscribeTopic) {
        try {
            mqttClient.unsubscribe(subscribeTopic);
        } catch (MqttException e) {
            e.printStackTrace();
            MLogUtil.d(TAG, "mqtt---------mqttUnsubscribe()连接断开----->" + e.getMessage());
            onConnectLost(e);
        }
    }

    private void onConnectLost(MqttException e) {
        MLogUtil.d(TAG, "mqtt--------MqttClientControl-onConnectLost----->" + e.getMessage());
        MqttReconnectManager.getInstance().reconnection(e);
        MqttManager.getInstance().getMqttResponseCallbackManager().onConnectionFailed(e);
    }

    //发布到服务器
    void mqttPublish(String publishTopic, String json) {
        if (mqttClient == null) {
            return;
        }
//        XTLogUtil.d(TAG, "mqtt发送json---------------->" + json);
        MqttMessage message = new MqttMessage(json.getBytes());
        message.setQos(1);
        if (mqttClient.isConnected()) {
            try {
                mqttClient.publish(publishTopic, message);
                MLogUtil.d(TAG, "mqtt-----发送主题-----》" + publishTopic);
                MLogUtil.d(TAG, "mqtt-----发送消息-----》" + json);
            } catch (MqttException e) {
                e.printStackTrace();
                MLogUtil.d(TAG, "mqtt---------mqttPublish()连接断开----->" + e.getMessage());
                onConnectLost(e);
            }
        } else {
            MLogUtil.d(TAG, "mqtt------mqttPublish-----未连接--->");
        }
    }

    public MqttClient getMqttClient() {
        return mqttClient;
    }
}
