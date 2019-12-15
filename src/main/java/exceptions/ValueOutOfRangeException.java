package exceptions;

public class ValueOutOfRangeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	final String message;
	public ValueOutOfRangeException(int value) {
		super();
		message = "value of " + value + " is out of range";
	}
	public ValueOutOfRangeException(String string) {
		super();
		message = string;
		// TODO Auto-generated constructor stub
	}
}
