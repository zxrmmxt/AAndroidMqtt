package com.xt.m_mqtt;

/**
 * Created by Administrator on 2017/2/21.
 */

public class MqttBean {
    private String UserName;
    private String PassWord;
    private String ClientID;
    private String IP;
    private String Port;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }
    
    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPort() {
        return Port;
    }

    public void setPort(String port) {
        Port = port;
    }
}
