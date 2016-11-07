package simplify.utils;


public class CustomException extends Exception {
	private static final long serialVersionUID = 5657656223198131026L;
	private String message;
	public CustomException (String message) {
		super(message);
		this.message= message;
	}
	public String getMessage() {
		return this.message;
	}
	
}
