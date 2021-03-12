/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smart.command;
import smart.smarthousemanagerapp.App;
/**
 *
 * @author Kami
 */
public class OnOffCommand implements Command{
        private App app = new App();
	
	public void DeviceOn() {
                app.TurnOnDevice();
                
	}
	public void DeviceOff() {
                 app.TurnOffDevice();
        }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
