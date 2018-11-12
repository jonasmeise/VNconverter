package type.VNDS;

public class GSetVar extends VNDSCommand {

	static String command = "gsetvar";
	static String description = "gsetvar	gsetvar variable modifier value	gsetvar YuRoute = 1	modifier: =, +, -, >=, <=, >, <\r\n" + 
			"Sets a variable into the global.sav, to be kept across games (ie. Cleared path flags, cleared game, etc.)";
																																															
	public GSetVar() {					
		super(command, description);
	}
}
