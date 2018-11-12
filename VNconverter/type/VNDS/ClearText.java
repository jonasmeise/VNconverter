package type.VNDS;

public class ClearText extends VNDSCommand {

	static String command = "cleartext";
	static String description = "cleartext	cleartext [type]	cleartext\r\n" + 
			"cleartext !	If no type is given, it`ll make enough blank lines (text ~) to fill the bottom screen.\r\n" + 
			"If type is !, it`ll completely clear the text buffer (including history).\r\n" + 
			"Clears text from the screen.";
																																															
	public ClearText() {					
		super(command, description);
	}
}
