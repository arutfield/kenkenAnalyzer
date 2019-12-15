package exceptions;

public class MissingPossibilityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	final String message;
	public MissingPossibilityException(int value) {
		super();
		message = "Unable to find " + value + " in list of possibilities";
		System.out.println(message);
	}
}
