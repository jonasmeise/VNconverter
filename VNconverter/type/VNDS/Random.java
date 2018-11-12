package type.VNDS;

public class Random extends VNDSCommand {

	static String command = "random";
	static String description = "random	random var low high	random dice 1 6	Includes the low and high numbers.\r\n" + 
			"Sets variable \"var\" to a number between low and high.";
																																															
	public Random() {					
		super(command, description);
	}
}
