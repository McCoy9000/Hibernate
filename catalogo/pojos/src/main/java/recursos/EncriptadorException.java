package recursos;

public class EncriptadorException extends Exception {

	private static final long serialVersionUID = 485462366866953395L;

	public EncriptadorException() {
	}

	public EncriptadorException(String message) {
		super(message);
	}

	public EncriptadorException(Throwable cause) {
		super(cause);
	}

	public EncriptadorException(String message, Throwable cause) {
		super(message, cause);
	}

	public EncriptadorException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
