package com.xt.a_mqtt;

import com.xt.m_common_utils.MLogUtil;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;

/**
 * Created by xuti on 2017/9/20.
 */

public abstract class MqttReceiveMsgControl {
    private static final String TAG = MqttReceiveMsgControl.class.getSimpleName();

    public MqttReceiveMsgControl(IMqttResponseMsgHandler iMqttResponseMsgHandler) {
        mIMqttResponseMsgHandler = iMqttResponseMsgHandler;
        MqttManager.getInstance().addCallback(callback);
    }

    private IMqttResponseMsgHandler mIMqttResponseMsgHandler;
    private MqttResponseCallback callback = new MqttResponseCallback() {
        @Override
        public void onSendSuccess(IMqttDeliveryToken token) {
        }

        @Override
        public void onMqttResponse(String topic, MqttMessage message) {
//            Charset charset = Charset.forName("GB2312");
            try {
                Charset charset = Charset.forName("UTF-8");
                String msg = new String(message.getPayload(), charset);
                MLogUtil.d(TAG, "mqtt-----返回主题-----》" + topic);
                MLogUtil.d(TAG, "mqtt-----返回消息----》" + msg);
                mIMqttResponseMsgHandler.onMqttResponse(topic, msg);
            } catch (Exception e) {
                MLogUtil.d("MqttReceiveMsgControl-onMqttResponse-Exception---" + e.getMessage());
            }
        }

        @Override
        public void onConnectionLost(Throwable cause) {
            onMqttConnectionLost(cause);
        }

        @Override
        public void onConnectionFailed(MqttException e) {
            onMqttConnectionFailed(e);
        }

        /*@Override
        public void onConnectionSuccess() {
            onMqttConnectionSuccess();
        }*/
    };

    /*public void onMqttConnectionSuccess() {

    }*/

    public void onMqttConnectionLost(Throwable cause) {

    }

    public void onMqttConnectionFailed(MqttException e) {

    }

    public void addCallback() {
        MqttManager.getInstance().addCallback(callback);
    }

    public void removeCallback() {
        MqttManager.getInstance().removeCallback(callback);
    }

}
