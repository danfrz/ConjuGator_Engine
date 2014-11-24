package engine.components.err.verb;

import engine.components.err.EngineException;

public class ConjugationException extends EngineException {

	public ConjugationException() {
		super();
	}

	public ConjugationException(String message) {
		super(message);
	}

	public ConjugationException(Throwable cause) {
		super(cause);
	}

	public ConjugationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConjugationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
