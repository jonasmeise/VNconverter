package type.VNDS;

public class Label extends VNDSCommand {

	static String command = "label";
	static String description = "label	label name	label start	Can jump to label with the \"goto\" or \"jump\" commands.\r\n" + 
			"Creates a label within the script that can be jumped to.";
																																															
	public Label() {					
		super(command, description);
	}
}
