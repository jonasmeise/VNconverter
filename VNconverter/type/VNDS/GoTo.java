package type.VNDS;

public class GoTo extends VNDSCommand {

	static String command = "goto";
	static String description = "goto	goto name	goto start	Only jumps to labels within the same .scr\r\n" + 
			"Jumps to the label with the same name.";
																																															
	public GoTo() {					
		super(command, description);
	}
}
