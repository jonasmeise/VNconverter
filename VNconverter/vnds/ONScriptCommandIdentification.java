package vnds;

import type.VNDS.*;
import java.util.regex.Pattern;

import extra.ConsoleLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;


public class ONScriptCommandIdentification {
	public String regexIgnore = "(;;;)";
	public String regexSound = "(?<=dwave \\d,\")(.*)(?=\")";
	public String regexPlaybackStop="(stop)";
	public String regexMusicStop="((?<!\\w)stop)";
	public String regexLabel = "(?<=\\*)(.*)$";
	public String regexClearSprite = "((csp)$|(csp (.*)))";
	public String regexBgLoad = "(?<=bg\\s?\")(.*)(?=\")";
	public String regexDelay = "((?<=!d)(\\d+))|(?<=wait )(\\d+)";
	public String regexTextNormal = "(?<=`)(?<!..)(.*)(?=`)"; 
	public String regexMusic = "(?<=mp3loop(\\s)?\")(.*)(?=\")";
	public String regexTextSpeakingDialogue = "(?<=]`)(.*)(?=`)";
	public String regexTextSpeakingCharacter = "(?<=\\[`)(.*)((?=`\\]`)|(?=`\\/))";
	public String regexTextSpeakingAudio = "(?<=[\\w\\d\\s]`\\/)(.+)(?=\\]`)";
	public String regexTextOff = "(textoff)";
	public String regexSpriteLocation = "(?<=ld )(\\w)(?=,\\\")";
	public String regexSpriteImage = "(?<=ld \\w,\":a;)(.*)(?=\")";
	public String regexClick = "(click)";
	public String regexClearSprite2 = "(cl \\w,\\d)";

	ConsoleLog myLog = new ConsoleLog();
	private ArrayList<String> regexLoop;
	
	public ONScriptCommandIdentification() {
		regexLoop = new ArrayList<String>();
		
		//add everything to the parser loop
		regexLoop.add(regexIgnore);
		regexLoop.add(regexTextNormal);
		regexLoop.add(regexTextSpeakingDialogue);
		regexLoop.add(regexTextSpeakingCharacter);
		regexLoop.add(regexTextSpeakingAudio);
		regexLoop.add(regexTextOff);
		regexLoop.add(regexSpriteLocation);
		regexLoop.add(regexSpriteImage);
		regexLoop.add(regexSound);
		regexLoop.add(regexPlaybackStop);
		regexLoop.add(regexLabel);
		regexLoop.add(regexClearSprite);
		regexLoop.add(regexBgLoad);
		regexLoop.add(regexDelay);
		regexLoop.add(regexMusic);
		regexLoop.add(regexMusicStop);
		regexLoop.add(regexClick);
		regexLoop.add(regexClearSprite2);
	}
	
	private BgLoad lastBGcommand = new BgLoad();
	
	public ArrayList<VNDSCommand> parseCommand(String originalLine) { //A shitload of magic happens
		ArrayList<VNDSCommand> returnList = new ArrayList<VNDSCommand>();
		VNDSCommand parsedCommand = new VNDSCommand();
		
		Pattern pattern = null;

		Matcher matcher = null;
		
		for(String regexExp : regexLoop) {
		    pattern = Pattern.compile(regexExp);
	        matcher = pattern.matcher(originalLine);
	        
	        //myLog.log("Trying " + regexExp+ "...");
	        
            //System.out.println("found: " + matcher.group(0));
            //System.out.println("clean: " + cleanString(matcher.group(), "\""));
            if(matcher.find()) {
	            if(regexExp.compareTo(regexIgnore)==0) {
	            	break;
	            } else if(regexExp.compareTo(regexSound)==0) {
	            	parsedCommand = new Sound();
	            	parsedCommand.addParameters(matcher.group(0));
	            	break;
	            }
            	else if(regexExp.compareTo(regexClick)==0) {
            		parsedCommand = new Text();
	            	parsedCommand.addParameters("!");
	            	break;
            	}
	            else if(regexExp.compareTo(regexPlaybackStop)==0) {
	            	parsedCommand = new Sound();
	            	parsedCommand.addParameters("~");
	            	Music commandStop = new Music();
	            	commandStop.addParameters("~");
	            	returnList.add(commandStop);
	            	break;
	            } else if(regexExp.compareTo(regexClearSprite)==0) {
	            	parsedCommand = new SetImg();
	            	parsedCommand.addParameters("~");
	            	break;
	            } else if(regexExp.compareTo(regexClearSprite2)==0) {
	            	parsedCommand = lastBGcommand;
	            	break;
	            } else if(regexExp.compareTo(regexBgLoad)==0) {
	            	parsedCommand = new BgLoad();
	            	parsedCommand.addParameters(matcher.group(0).toLowerCase());
	            	lastBGcommand = (BgLoad) parsedCommand;
	            	break;
	            } else if(regexExp.compareTo(regexMusicStop)==0) {
	            	parsedCommand = new Music();
	            	parsedCommand.addParameters("~");
	            	break;
	            } else if(regexExp.compareTo(regexDelay)==0) {
	            	parsedCommand = new Delay();
	            	parsedCommand.addParameters(String.valueOf((Integer.valueOf(matcher.group(0))/16)));
	            	break;
	            } else if(regexExp.compareTo(regexTextNormal)==0) {
	            	parsedCommand = new Text();
	            	parsedCommand.addParameters(matcher.group(0));
	            	break;
	            } else if(regexExp.compareTo(regexMusic)==0) {
	            	parsedCommand = new Music();
	            	parsedCommand.addParameters(matcher.group(0));
	            	break;
	            } else if(regexExp.compareTo(regexTextSpeakingDialogue)==0) {
	            	//audio?
	            	String parameter_text = matcher.group(0).toString();
	            	
	            	pattern = Pattern.compile(regexTextSpeakingAudio);
	    	        matcher = pattern.matcher(originalLine);
	            	
	    	        while(matcher.find()) {
	    	        	VNDSCommand audio_command = new Sound();
	    	        	audio_command.addParameters(matcher.group(0).toString());
	    	        	returnList.add(audio_command);
	    	        }
	    	        
	    	        pattern = Pattern.compile(regexTextSpeakingCharacter);
	    	        matcher = pattern.matcher(originalLine);

	    	        //get character name?
	    	        while(matcher.find()) {
	    	        	parsedCommand = new Text();
	    	        	parsedCommand.addParameters("[" + cleanString(matcher.group(0).toString(), " ") + "]");
	    	        	parsedCommand.addParameters(parameter_text);
	    	        }
	    	        
	            	break;
	            	
	            } else if(regexExp.compareTo(regexTextOff)==0) {
	            	parsedCommand = new ClearText();
                	//parsedCommand.addParameters(matcher.group(0));
	            	break;
	            } else if(regexExp.compareTo(regexSpriteImage)==0) {
	            	returnList.add(lastBGcommand);
	            	parsedCommand = new SetImg();
	            	parsedCommand.addParameters(matcher.group(0));
	            	
	            	pattern = Pattern.compile(regexSpriteLocation);
	    	        matcher = pattern.matcher(originalLine);
	            	
	    	        //get character name?
	    	        while(matcher.find()) {
	    	        	parsedCommand.addParameters(matcher.group(0).toString());
	    	        }
	    	        break;
	            } else {
	            	//error
	            }
	            //myLog.log("Found " + parsedCommand.outputCommand());
            }
		}
		//if(parsedCommand.getCommand().compareTo("unknown")!=0) {
			returnList.add(parsedCommand);
		//}
		
		return returnList;
	}
	
	public static void main(String[] args) {
		ONScriptCommandIdentification myProgram = new ONScriptCommandIdentification();
		myProgram.testRun();
	}
	
	public String cleanString(String original, String toRemove) { //removes "" etc.
		String returnString = original.toString();
		
		if(returnString.startsWith(toRemove)) {
			returnString = cleanString(original.substring(1), toRemove);
		}
		if(returnString.endsWith(toRemove)) {
			returnString = cleanString(returnString.substring(0, original.length()-1), toRemove);
		}
		
		return returnString;
	}
	
	
	public void testRun() {
		
		String regex = regexTextSpeakingDialogue;
		String testString = "\r\n" + 
				"	ld c,\":a;ld/saku01.bmp\",3";
		//System.out.println(testString + " /// " + regex);
		ArrayList<VNDSCommand> fullScript = parseCommand(testString);
		
		for(VNDSCommand command : fullScript) {
			myLog.log(command.outputCommand());
		}
	}
}
