package com.one.workoptimizer.exception;

/**
 * 
 * @author Ashish G
 *
 */
public class InvalidInputException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param message
	 */
	public InvalidInputException(String message) {
        super(message);
    }
}
