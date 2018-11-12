package type.VNDS;

public class SetImg extends VNDSCommand {

	static String command = "setimg";
	static String description = "setimg	setimg file x y	setimg yuki.png 85 0	X>0 shifts image right, Y>0 shifts image down, and vice versa.";
																																															
	public SetImg() {					
		super(command, description);
	}
}
