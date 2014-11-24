package engine.components.err.verb;

public class TenseNotSupportedException extends ConjugationException {

	public TenseNotSupportedException() {
	}

	public TenseNotSupportedException(String message) {
		super(message);
	}

	public TenseNotSupportedException(Throwable cause) {
		super(cause);
	}

	public TenseNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}

	public TenseNotSupportedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
