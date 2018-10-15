package com.xt.a_mqtt;

import com.xt.m_common_utils.MLogUtil;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by xuti on 2017/10/18.
 * mqtt断开连接，接收到消息，发送消息成功的回调
 */

class AMqttCallback implements MqttCallback {
    private static final String TAG = AMqttCallback.class.getSimpleName();

    @Override
    public void connectionLost(Throwable cause) {
        MLogUtil.d(TAG, "mqtt--------connectionLost(Throwable cause)连接断开------" + cause.getMessage());
        MqttManager.getInstance().getMqttResponseCallbackManager().onConnectionLost(cause);
        MqttReconnectManager.getInstance().reconnection(cause);
//            mqttConn();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String msg = new String(message.getPayload());
        MqttManager.getInstance().getMqttResponseCallbackManager().onMqttResponse(topic, message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        int messageId = token.getMessageId();
        MLogUtil.d(TAG, "mqtt--------发送完成------");
        MqttManager.getInstance().getMqttResponseCallbackManager().onSendSuccess(token);
    }
}
