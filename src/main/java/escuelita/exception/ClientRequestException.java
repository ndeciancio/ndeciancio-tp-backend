package escuelita.exception;

public class ClientRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int statusCode;

	public ClientRequestException() {
		super();
		this.statusCode = 0;
	}

	public ClientRequestException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.statusCode = 0;
	}


	public ClientRequestException(String message) {
		super(message);
		this.statusCode = 0;
	}

	public ClientRequestException(Throwable cause) {
		super(cause);
		this.statusCode = 0;
	}

	public ClientRequestException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
}
