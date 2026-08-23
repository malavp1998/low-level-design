package behavioral.command;

public class FanStartCommand implements Command{

    private Fan fan;

    FanStartCommand(Fan fan)
    {
        this.fan = fan;
    }

	@Override
	public void execute() {
        fan.start();
	}

	@Override
	public void undo() {
        fan.stop();	
	}
    
}
