package type.VNDS;

public class Delay extends VNDSCommand {

	static String command = "delay";
	static String description = "delay	delay X	delay 30	Audio still plays normally.\r\n" + 
			"Pause actions for X frames.";
																																															
	public Delay() {					
		super(command, description);
	}
}
