package behavioral.command;

/**
 * CLIENT / DRIVER.
 * The client's job: create receivers, wrap them in commands, load the invoker.
 *
 * EXPECTED OUTPUT ONCE IMPLEMENTED (roughly):
 *   Living Room light is ON
 *   Living Room light is OFF
 *   Bedroom fan running at speed 3
 *   -- undo --
 *   Bedroom fan stopped
 *   -- undo --
 *   Living Room light is ON
 */
public class CommandMain {

    public static void main(String[] args) {

        Fan fan = new Fan();
        Light light = new Light();
        RemoteControl remoteControl = new RemoteControl();

        remoteControl.pressButton(new FanStartCommand(fan));
        remoteControl.pressButton(new LightOnCommand(light));
        
        
        remoteControl.pressUndo();
        remoteControl.pressUndo();
     
    }
}
