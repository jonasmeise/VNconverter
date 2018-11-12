package extra;

public class ConsoleLog {
	private boolean active = true;
	
	public void log(String message) {
		String source = getMethodName(3);
		System.out.println(source + ": " + message);
	}
	
	public void log(int message) {
		String source = getMethodName(3);
		System.out.println(source + ": " + message);
	}
	
	public String getMethodName(final int depth)
	{
	  final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
	  return ste[depth].getMethodName();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
