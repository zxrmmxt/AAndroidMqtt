package com.xt.m_mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by xuti on 2017/9/20.
 */

public abstract class MqttResponseCallback {
    public abstract void onSendSuccess(IMqttDeliveryToken token);

    public abstract void onMqttResponse(String topic, MqttMessage message);

    public void onConnectionLost(Throwable cause) {

    }
    public void onConnectionFailed(MqttException e) {

    }

    /*public void onConnectionSuccess() {

    }*/
}
