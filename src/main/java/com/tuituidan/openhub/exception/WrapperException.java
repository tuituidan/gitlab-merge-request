package com.tuituidan.openhub.exception;

import com.tuituidan.openhub.util.StringExtUtils;

import ch.qos.logback.classic.spi.EventArgUtil;

/**
 * WrapperException.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
public class WrapperException extends RuntimeException {

    private static final long serialVersionUID = 3965364044296420465L;

    /**
     * WrapperException.
     *
     * @param message message
     * @param args    args
     */
    public WrapperException(String message, Object... args) {
        super(StringExtUtils.format(message, args), EventArgUtil.extractThrowable(args));
    }

}
