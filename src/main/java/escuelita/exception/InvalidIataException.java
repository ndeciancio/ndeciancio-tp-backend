package escuelita.exception;

public class InvalidIataException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidIataException() {
		super();
	}

	public InvalidIataException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidIataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidIataException(String message) {
		super(message);
	}

	public InvalidIataException(Throwable cause) {
		super(cause);
	}

}
