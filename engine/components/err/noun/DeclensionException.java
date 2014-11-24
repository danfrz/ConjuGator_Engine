package engine.components.err.noun;

import engine.components.err.EngineException;

public class DeclensionException extends EngineException {
	public DeclensionException() {
	}

	public DeclensionException(String message) {
		super(message);
	}

	public DeclensionException(Throwable cause) {
		super(cause);
	}

	public DeclensionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeclensionException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
