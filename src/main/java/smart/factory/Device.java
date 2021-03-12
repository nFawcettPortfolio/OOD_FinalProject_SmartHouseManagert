/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smart.factory;
    public abstract class Device {
	public abstract String getName();
	public abstract String getDeviceType();
        public abstract String getDeviceAddress();
        public abstract String getNickname();
        public abstract boolean getOnOffState();
	
	@Override
	public String toString() {
		return this.getName()+"("+this.getDeviceType()+")";
	}
}
