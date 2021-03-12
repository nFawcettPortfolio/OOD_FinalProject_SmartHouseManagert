/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smart.iterator;

/**
 *
 * @author Kami
 */
public class DeviceBtn {

    private String name;
    private boolean onOff;
    	public DeviceBtn(String name, boolean onOff) {
		this.name = name;
                this.onOff=onOff;
	}
        public String toString(){
            return name;
        }
        public boolean getOnOff(){
            return onOff;
        }
}
