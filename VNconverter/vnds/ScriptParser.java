package vnds;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import extra.ConsoleLog;
import extra.FileUtil;
import type.VNDS.*;

public class ScriptParser {
	
	private int app_width = 800;
	private int app_heigth = 600;
	
	private String filePath = "C:\\Users\\Jonas\\Downloads\\KillerQueen\\KillerQueen-en\\script.txt";
	private String scriptOutputPath = "C:\\Users\\Jonas\\Downloads\\KillerQueen\\2change\\foreground\\script.txt";
	private ConsoleLog myLog = new ConsoleLog(); //for logging purposes
	private FileUtil fu = new FileUtil();
	private ONScriptCommandIdentification ons = new ONScriptCommandIdentification();
	
	public static void main(String[] args) {
		ScriptParser myParser = new ScriptParser();
		myParser.run();
	}
	
	public void run() {
		ArrayList<VNDSCommand> fullScript = parseONSScript(filePath);
		String commandName;	
		
		OutputStreamWriter osw = null;
		try {
			osw = fu.createWriter(scriptOutputPath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		VNDSCommand finalCommand = new VNDSCommand();
		
		for(VNDSCommand command : fullScript) {
			commandName = command.getCommand(); 
			finalCommand = command;
				
			if(commandName.compareTo("setimg")==0 && command.getParameters().size()>1) {
				String orientation = command.getParameters().get(1);
				
				if(commandName.compareTo("setimg")==0) {
					//png umbenennen
					command.getParameters().set(0, command.getParameters().get(0).substring(0, command.getParameters().get(0).length()-4) + ".png");
					command.getParameters().set(0, command.getParameters().get(0).replace("system/", "foreground/"));
					command.getParameters().set(0, command.getParameters().get(0).replace("ld/", "foreground/"));
				}
				
				BufferedImage bimg = null;
				try {
					myLog.log("Try to read '" + command.getParameters().get(0) + "'.");
					bimg = ImageIO.read(new File(command.getParameters().get(0)));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				int width          = bimg.getWidth();
				int heigth         = bimg.getHeight();

				if(orientation.compareTo("c")==0) {
					int new_x_pos = (app_width / 2) - (width / 2);
					int new_y_pos = (app_heigth) - (heigth);
					
					finalCommand = new SetImg();
					finalCommand.addParameters(new File(command.getParameters().get(0)).getPath());
					finalCommand.addParameters("" + Math.round(new_x_pos/3.125));
					finalCommand.addParameters("" + Math.round(new_y_pos/3.125));
				} else if(orientation.compareTo("l")==0) {
					int new_x_pos = 0;
					int new_y_pos = (app_heigth) - (heigth);
					
					finalCommand = new SetImg();
					finalCommand.addParameters(new File(command.getParameters().get(0)).getPath());
					finalCommand.addParameters("" + Math.round(new_x_pos/3.125));
					finalCommand.addParameters("" + Math.round(new_y_pos/3.125));
				} else if(orientation.compareTo("r")==0) {
					int new_x_pos = (app_width) - (width);
					int new_y_pos = (app_heigth) - (heigth);
					
					finalCommand = new SetImg();
					finalCommand.addParameters(new File(command.getParameters().get(0)).getPath());
					finalCommand.addParameters("" + Math.round(new_x_pos/3.125));
					finalCommand.addParameters("" + Math.round(new_y_pos/3.125));
				}
			}
			
			if(commandName.compareTo("setimg")==0 || commandName.compareTo("bgload")==0 || commandName.compareTo("sound")==0 || commandName.compareTo("music")==0)
			{
				//myLog.log(command.outputCommand());
				String[] split = command.getParameters().get(0).split("/");
				if(split.length>1) {
					finalCommand.getParameters().set(0, split[1]);
				}
			}	
			
			try {
				if(finalCommand.getCommand().compareTo("unknown")!=0) {
					myLog.log(finalCommand.outputCommand());
					osw.write(finalCommand.outputCommand() + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
		try {
			osw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<VNDSCommand> parseONSScript(File filePath) {
		ArrayList<VNDSCommand> completeScript = new ArrayList<VNDSCommand>();
		int counter = 0;
		
		myLog.log("Starting to parse from '" + filePath.getAbsolutePath() + "' ...");
		
		ArrayList<String> completeScriptString = fu.readFromFileArrayList(filePath);
		
		myLog.log("Starting to parse " + completeScriptString.size() + " ...");
		
		for(String line : completeScriptString) {
			counter++;
			completeScript.addAll(ons.parseCommand(line));
			myLog.log("Parsed " + counter + "/" + completeScriptString.size() + " lines.");
		}
		
		return completeScript;
	}
	
	public ArrayList<VNDSCommand> parseONSScript(String filePath) {
		return parseONSScript(new File(filePath));
	}
}