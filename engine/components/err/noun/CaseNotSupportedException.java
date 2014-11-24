/**
 * 
 */
package engine.components.err.noun;

/**
 * @author dan
 *
 */
public class CaseNotSupportedException extends DeclensionException {

	/**
	 * This is used if the language or the function
	 * called does not support the case provided.
	 */
	public CaseNotSupportedException() {
	}

	/**
	 * @param message
	 */
	public CaseNotSupportedException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public CaseNotSupportedException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CaseNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CaseNotSupportedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
