package smart.command;

public class DeviceOff implements Command{
    private OnOffCommand cmd;
    	public DeviceOff(OnOffCommand cmd) {
		this.cmd=cmd;
	}
	public void execute() {
		cmd.DeviceOff();
	}
	
}
