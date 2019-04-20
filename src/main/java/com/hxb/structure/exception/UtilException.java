package com.hxb.structure.exception;

/**
 * @author create by huang xiao bao
 * @date 2019-04-20 17:14:12
 */
public class UtilException extends RuntimeException {
    public UtilException() {
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilException(Throwable cause) {
        super(cause);
    }
}
