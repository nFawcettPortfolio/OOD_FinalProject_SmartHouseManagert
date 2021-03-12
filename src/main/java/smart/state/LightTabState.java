/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smart.state;

/**
 *
 * @author Kami
 */
public class LightTabState implements State{

    @Override
    public void doAction(Context context) {
        context.setState(this);
    }
    @Override
    public String toString(){
        return "Lights";
    }
    
}
