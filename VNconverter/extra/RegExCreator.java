package extra;

import java.util.ArrayList;

public class RegExCreator {
	static void main(String[] args) {
		RegExCreator myCreator = new RegExCreator();
		myCreator.run();
	}
	
	public void run() {
		
	}
	
	public String deriveRegEx(String text, ArrayList<String> trueMatches) {
		String compiledRegex = new String();
		
		return compiledRegex;
	}
	
	public double calculatePrecision(String text, ArrayList<String> foundMatches, ArrayList<String> trueMatches) {
		double precision = 0;
		
		if(foundMatches.removeAll(trueMatches)) {
			if(foundMatches.isEmpty()) {
				return 1; //maximal precision
			}
		}
		
		return precision;
	}
}
