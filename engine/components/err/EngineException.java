package engine.components.err;

public class EngineException extends RuntimeException {
	public EngineException() {
	}

	public EngineException(String message) {
		super(message);
	}

	public EngineException(Throwable cause) {
		super(cause);
	}

	public EngineException(String message, Throwable cause) {
		super(message, cause);
	}

	public EngineException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
