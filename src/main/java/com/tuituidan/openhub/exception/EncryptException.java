package com.tuituidan.openhub.exception;

/**
 * EncryptException.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2022/2/17
 */
public class EncryptException extends RuntimeException {

    private static final long serialVersionUID = -1934844110417702833L;

    /**
     * EncryptException
     *
     * @param message message
     * @param cause cause
     */
    public EncryptException(String message, Throwable cause) {
        super(message, cause);
    }

}
