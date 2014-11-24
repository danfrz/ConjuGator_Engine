package engine.components.err.verb;

public class LanguageContextException extends ConjugationException {

	public LanguageContextException() {
		super();
	}

	public LanguageContextException(String message) {
		super(message);
	}

	public LanguageContextException(Throwable cause) {
		super(cause);
	}

	public LanguageContextException(String message, Throwable cause) {
		super(message, cause);
	}

	public LanguageContextException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
