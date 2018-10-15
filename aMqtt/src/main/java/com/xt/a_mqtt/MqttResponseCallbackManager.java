package com.xt.a_mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xuti on 2017/9/20.
 */

public class MqttResponseCallbackManager {
    private Set<MqttResponseCallback> mqttResponseCallbacks;

    public MqttResponseCallbackManager() {
        init();
    }

    /*public void onConnectionSuccess() {
        for (MqttResponseCallback callback : mqttResponseCallbacks) {
            callback.onConnectionSuccess();
        }
    }*/

    private void init() {
        mqttResponseCallbacks = new HashSet<>();
    }

    public void addCallback(MqttResponseCallback callback) {
        mqttResponseCallbacks.add(callback);
    }

    public void removeCallback(MqttResponseCallback callback) {
        mqttResponseCallbacks.remove(callback);
    }

    public void onSendSuccess(IMqttDeliveryToken token) {
        for (MqttResponseCallback callback : mqttResponseCallbacks) {
            callback.onSendSuccess(token);
        }
    }

    public void onMqttResponse(String topic, MqttMessage message) {
        for (MqttResponseCallback callback : mqttResponseCallbacks) {
            callback.onMqttResponse(topic, message);
        }
    }

    public void onConnectionLost(Throwable cause) {
        for (MqttResponseCallback callback : mqttResponseCallbacks) {
            callback.onConnectionLost(cause);
        }
    }

    public void onConnectionFailed(MqttException e) {
        for (MqttResponseCallback callback : mqttResponseCallbacks) {
            callback.onConnectionFailed(e);
        }
    }
}
