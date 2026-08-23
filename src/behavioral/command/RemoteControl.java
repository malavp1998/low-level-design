package behavioral.command;

import java.util.Stack;

public class RemoteControl {

    Stack<Command> actions = new Stack<>();

    // private Light light;
    // private Fan fan;

    // public void pressButton(String device, String action) {
    // if (device.equals("LIGHT")) {
    // if (action.equals("ON")) light.on();
    // else if (action.equals("OFF")) light.off();
    // } else if (device.equals("FAN")) {
    // if (action.equals("START")) fan.start();
    // else if (action.equals("STOP")) fan.stop();
    // }
    // // No way to undo the last action
    // // No way to record history
    // // No way to queue up multiple actions
    // // Every new device = more if-else
    // }

    public void pressButton(Command command) {
        actions.push(command);
        command.execute();
    }

    public void pressUndo() {
        if (!actions.isEmpty()) {
            Command command = actions.pop();
            command.undo();
        }
    }

}