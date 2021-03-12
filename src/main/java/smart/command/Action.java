/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smart.command;

/**
 *
 * @author Nathan
 */
import java.util.ArrayList;
import java.util.List;

public class Action {
	private List<Command> commandList = new ArrayList<Command>();
	public void takeCommand(Command command) {
		commandList.add(command);
	}
	public void performCommand() {
            commandList.forEach(command -> {
                command.execute();
            });
		commandList.clear();
	}

    void takeCommand(DeviceOn on) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
