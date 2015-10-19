package ch.local.common.exception;

/**
 * Exceptions that are thrown in DifferenceFinder
 * 
 * @author Philipp Keller, local.ch AG
 * @version $Id$
 * @since Mar 13, 2007
 */
public class DifferenceException extends Exception {
	private static final long serialVersionUID = -6526016984782905373L;

	/**
	 * Constructs a new exception with the specified detail message.<br />
	 * The cause is not initialized, and may subsequently be initialized by a call to
	 * <code>Throwable.initCause(java.lang.Throwable)</code>.<br />
	 * Calls the constructor of the super class <code>java.lang.Exception</code>
	 * 
	 * @param message error message
	 */
	public DifferenceException(String message) {
		super(message);
	}	
}
