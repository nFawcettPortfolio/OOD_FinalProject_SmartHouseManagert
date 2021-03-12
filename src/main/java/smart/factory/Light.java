
package smart.factory;

public class Light extends Device{

	private String name;
	private String deviceType;
	private String deviceAddress;
        private String nickname;
        private boolean onOffState;
        
	public Light(String name, String deviceType, String deviceAddress, String nickname, boolean onOffState) {
		this.name=name;
		this.deviceType=deviceType;
                this.deviceAddress=deviceAddress;
                this.nickname=nickname;
                this.onOffState=onOffState;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDeviceType() {
		return this.deviceType;
	}
        @Override
	public String getDeviceAddress() {
		return this.deviceAddress;
	}
	@Override
	public String getNickname() {
		return this.nickname;
	}
        @Override
	public boolean getOnOffState() {
		return this.onOffState;
	}
        public void setOnOffState(boolean b){
            this.onOffState=b;
        }
}
