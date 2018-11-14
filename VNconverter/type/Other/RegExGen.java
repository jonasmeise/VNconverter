package type.Other;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegExGen {
	public String regEx;
	public double accuracy;
	
	public RegExGen(String regEx, double accuracy) {
		setRegEx(regEx);
		setAccuracy(accuracy);
	}

	public RegExGen() {
		
	}
	
	public String getRegEx() {
		return regEx;
	}

	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	
	public RegExGen createChild(RegExGen anotherGen, double mutation) {
		RegExGen child = new RegExGen();
		
		String newDNA = scramble(getRegEx(), anotherGen.getRegEx());
		
		return child;
	}
	
	public String scramble(String regExA, String regExB) {
		boolean isRegex = false;
		String generated = "";

		while(!isRegex) {
			generated = regExA.substring(0, Math.round(regExA.length()/2)) + regExB.substring(Math.round(regExA.length()/2));
			
			//randomly change one 
			try {
			  Pattern.compile(generated);
			  isRegex = true;
			} catch (PatternSyntaxException e) {
			  isRegex = false;
			}
		}
		
		return generated;
	}
}
