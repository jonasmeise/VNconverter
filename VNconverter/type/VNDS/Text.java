package type.VNDS;

public class Text extends VNDSCommand {

	static String command = "text";
	static String description = "text	text string	text Life began anew\r\n" + 
			"text @Life began anew\r\n" + 
			"text ~\r\n" + 
			"text !\r\n" + 
			"text $variable	@ will show text and not require clicking to advance\r\n" + 
			"~ will make a blank line and not require clicking to advance\r\n" + 
			"! will make a blank line and require clicking to advance\r\n" + 
			"variables can be shown by placing \"$\" in front of the variable name.";
																																															
	public Text() {					
		super(command, description);
	}
}
