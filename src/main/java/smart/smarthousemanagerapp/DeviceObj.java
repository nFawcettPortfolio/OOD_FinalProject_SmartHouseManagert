/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smart.smarthousemanagerapp;

/**
 *
 * @author Kami
 */
public class DeviceObj {
    public String name;
    public String deviceType;
    public String deviceAddress;
    public String nickname;
    public boolean onOffState;
    public boolean connected;

    public DeviceObj(String name, String deviceType, String deviceAddress, String nickname, boolean onOffState, boolean connected){
        this.name=name;
        this.deviceType=deviceType;
        this.deviceAddress=deviceAddress;
        this.nickname=nickname;
        this.onOffState=onOffState;
        this.connected=connected;
    }
    public String getName(){
        return name;
    }
    public String getDeviceType(){
        return deviceType;
    }
    public String getDeviceAddress(){
        return deviceAddress;
    }
    public String getNickname(){
        return nickname;
    }
    public boolean getOnOffState(){
        return onOffState;
    }
        public boolean getConnected(){
        return connected;
    }
}
