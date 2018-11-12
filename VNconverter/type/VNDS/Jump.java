package type.VNDS;

public class Jump extends VNDSCommand {

	static String command = "jump";
	static String description = "jump	jump file.scr [label]	jump endings.scr NiceEnd	If label is specified, it jumps to that label within the .scr\r\n" + 
			"Jumps to defined .scr and starts reading from there.";
																																															
	public Jump() {					
		super(command, description);
	}
}
