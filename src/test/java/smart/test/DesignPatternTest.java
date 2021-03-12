/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smart.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import smart.state.Context;
import smart.state.State;
import junit.framework.*;
import smart.state.Context;
import smart.state.LightTabState;
import smart.state.SpeakerTabState;
import smart.state.State;
import smart.factory.Device;
import smart.factory.Light;
import smart.factory.Outlet;
import smart.factory.Speaker;
import smart.factory.Thermastat;
import smart.factory.DeviceFactory;
/**
 *
 * @author Kami
 */
public class DesignPatternTest {

    @Test
    public void testState(){
        Context context = new Context();
        LightTabState lightTabState = new LightTabState();
        lightTabState.doAction(context);
        assertEquals("Lights", context.getState().toString());
        SpeakerTabState speakerTabState = new SpeakerTabState();
        speakerTabState.doAction(context);
        assertEquals("Speakers", context.getState().toString());
    }
    @Test
    public void testFactory(){
        Device device = DeviceFactory.getDevice("Device", "Light", "addre55","DeviceNick",true);
        assertEquals("Device", device.getName());
        assertEquals("addre55", device.getDeviceAddress());
        assertEquals("Light", device.getDeviceType());
        assertTrue(device.getOnOffState());
    }
}
