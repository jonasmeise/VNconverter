package type.VNDS;

public class BgLoad extends VNDSCommand {

	static String command = "bgload";
	static String description = "bgload	bgload file [fadetime]	bgload bg001.jpg 20	Default fadetime is 16\r\n" + 
			"Draws background image.";
																																															
	public BgLoad() {					
		super(command, description);
	}
}
