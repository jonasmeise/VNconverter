package type.Other;

public class RegExMatch {
	private String text;
	private int startPos;
	
	public RegExMatch(String text) {
		setText(text);
	}
	
	public RegExMatch(String text, int startPos) {
		setText(text);
		setStartPos(startPos);
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getStartPos() {
		return startPos;
	}
	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}
	public int getEndPos() {
		return (getStartPos()+getTextLength());
	}
	
	public int getTextLength() {
		return getText().length();
	}
	
}
