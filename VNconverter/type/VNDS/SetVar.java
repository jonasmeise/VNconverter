package type.VNDS;

public class SetVar extends VNDSCommand {

	static String command = "setvar";
	static String description = "setvar	setvar variable modifier value	setvar YuAffection + 1\r\n" + 
			"setvar ch01 = \"Not Available\"	modifier: =, +, -, >=, <=, >, <\r\n" + 
			"Sets a variable into the local save memory, to be kept in normal save files for things like character flags (ie. Talk to <person></person> 7 times to get their ending).";
																																															
	public SetVar() {					
		super(command, description);
	}
}
