package type.VNDS;

public class Choice extends VNDSCommand {

	static String command = "choice";
	static String description = "choice	choice |option1|option2|etc...	choice |Yes|No|$variable	When a choice is clicked, the variable \"selected\" is set to the value of what was selected, starting at 1.\r\n" + 
			"Variables can be shown by adding $ before the variable name\r\n" + 
			"Use \"if selected == 1\", etc. to branch off what was selected.\r\n" + 
			"Displays choices on the lower screen.";
																																															
	public Choice() {					
		super(command, description);
	}
}
