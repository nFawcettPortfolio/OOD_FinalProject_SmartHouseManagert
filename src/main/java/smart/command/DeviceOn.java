package smart.command;

public class DeviceOn implements Command{
    private OnOffCommand cmd;
    	public DeviceOn(OnOffCommand cmd) {
		this.cmd=cmd;
	}
    @Override
	public void execute() {
		cmd.DeviceOn();
	}
	
}
