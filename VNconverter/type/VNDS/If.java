package type.VNDS;

public class If extends VNDSCommand {

	static String command = "if";
	static String description = "if/fi	if x == 1\r\n" + 
			"commands\r\n" + 
			"fi\r\n" + 
			"conditional jump	if YuAffection == 7\r\n" + 
			"jump YuEnding.scr\r\n" + 
			"fi\r\n" + 
			"jump BadEnding.scr	The left operand \"x\" must be a variable, right may be either another variable, or a number.\r\n" + 
			"If the initial condition is true, it keeps reading. If false, skip all between the \"if x == 7\" and \"fi\"";
																																															
	public If() {					
		super(command, description);
	}
}
