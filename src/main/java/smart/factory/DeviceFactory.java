/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smart.factory;

/**
 *
 * @author Kami
 */
public class DeviceFactory {
    	public static Device getDevice(String name, String deviceType, String deviceAddress, String nickname, boolean onOffState) {
		if("Light".equalsIgnoreCase(deviceType)) return new Light(name, deviceType, deviceAddress, nickname, onOffState);
		else if("Speaker".equalsIgnoreCase(deviceType)) return new Speaker(name, deviceType, deviceAddress, nickname, onOffState);
                else if("Thermastat".equalsIgnoreCase(deviceType)) return new Thermastat(name, deviceType, deviceAddress, nickname, onOffState);
                else if("Outlet".equalsIgnoreCase(deviceType)) return new Outlet (name, deviceType, deviceAddress, nickname, onOffState);
		return null;
        }
}
