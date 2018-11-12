package type.VNDS;

public class Sound extends VNDSCommand {

	static String command = "sound";
	static String description = "sound	sound file N	sound waves.aac 12\r\n" + 
			"sound ~	-1 for infinite looping.\r\n" + 
			"~ to stop any currently playing sound.\r\n" + 
			"Loads sound into memory (don`t load anything over 1MB) and plays it N times.";
																																															
	public Sound() {					
		super(command, description);
	}
}
