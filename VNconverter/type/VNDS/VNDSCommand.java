package type.VNDS;

import java.util.ArrayList;

public class VNDSCommand {
	//https://github.com/BASLQC/vnds/wiki/Visual-Novel-Porting-Guide
	
	private String command = "unknown";
	private String description = "unknown";
	private ArrayList<String> parameters;
	
	public VNDSCommand() {
		parameters = new ArrayList<String>();
	}
	
	public VNDSCommand(String command, String description) {
		this.command = command;
		this.description = description;
		parameters = new ArrayList<String>();
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public String getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public ArrayList<String> getParameters() {
		return parameters;
	}
	
	public void addParameters(String parameter) {
		parameters.add(parameter);
	}
	
	public String outputCommand() {
		String returnString = getCommand();
		
		for(String singleParameter : getParameters()) {
			returnString += " " + singleParameter;
		}
		
		return returnString;
	}
}
