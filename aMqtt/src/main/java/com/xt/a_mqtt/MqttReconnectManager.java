package com.xt.a_mqtt;

import android.os.Handler;
import android.os.HandlerThread;

import com.xt.m_common_utils.MLogUtil;
import com.xt.m_common_utils.MNetworkUtils;

/**
 * Created by xuti on 2017/11/30.
 * mqtt重连管理
 */

public class MqttReconnectManager {
    private static MqttReconnectManager mInstance;
    private final Handler mHandler;
    private int reconnectCount = 0;

    private MqttReconnectManager() {
        HandlerThread handlerThread = new HandlerThread("mqttReconnect");
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper());
    }

    public static MqttReconnectManager getInstance() {
        if (mInstance == null) {
            synchronized (MqttReconnectManager.class) {
                if (mInstance == null) {
                    mInstance = new MqttReconnectManager();
                }
            }
        }
        return mInstance;
    }

    private void login() {

    }


    void reconnection(Throwable cause) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (MNetworkUtils.isConnected()) {
                    MLogUtil.d("mqtt----reconnection-------重连----》");
                    connect();
                } else {
                    MLogUtil.d("mqtt----reconnection-------网络未连接----》");
                }
            }
        });
    }

    private void connect() {
        if (reconnectCount >= 5) {
            reconnectCount = 0;
            login();
        } else {
            reconnectCount += 1;
            if (MqttManager.getInstance().mqttConn()) {
                MLogUtil.d("mqtt----MqttReconnectManager-------成功----》" + reconnectCount);
//                MqttManager.getInstance().mqttSubscribe(LoginDataSource.getINSTANCE().getCacheData().getMqtt_url_sub());
            } else {
                MLogUtil.d("mqtt----MqttReconnectManager-------失败----》" + reconnectCount);
                MqttManager.getInstance().mqttDisConn();
                MqttManager.getInstance().mqttClose();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        connect();
                    }
                }, 3000);
            }
        }
    }

    /*void onConnectionSuccess() {
        MqttManager.getInstance().mqttSubscribe(LoginDataSource.getINSTANCE().getCacheData().getMqtt_url_sub());
        timeoutControl.stopAllTimeouts();
    }*/
}
