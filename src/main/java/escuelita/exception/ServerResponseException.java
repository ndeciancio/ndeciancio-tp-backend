package escuelita.exception;

public class ServerResponseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final int statusCode;

	public ServerResponseException(String reasonPhrase, int statusCode) {
		super(reasonPhrase);
		this.statusCode = statusCode;
	}

	/**
	 * 
	 */

	public ServerResponseException() {
		super();
		this.statusCode = 0;
	}

	public ServerResponseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.statusCode = 0;
	}

	public ServerResponseException(String message, Throwable cause) {
		super(message, cause);
		this.statusCode = 0;
	}

	public ServerResponseException(String message) {
		super(message);
		this.statusCode = 0;
	}

	public ServerResponseException(Throwable cause) {
		super(cause);
		this.statusCode = 0;
	}

	public int getStatusCode() {
		return statusCode;
	}

}
