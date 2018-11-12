package type.VNDS;

public class Music extends VNDSCommand {

	static String command = "music";
	static String description = "music	music file	music bgm01.mp3\r\n" + 
			"music ~	~ to stop any currently playing music.\r\n" + 
			"Plays and loops music.";
																																															
	public Music() {					
		super(command, description);
	}
}
