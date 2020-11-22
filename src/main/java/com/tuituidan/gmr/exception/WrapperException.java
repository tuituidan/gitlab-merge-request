package com.tuituidan.gmr.exception;

import com.tuituidan.gmr.util.StringExtUtils;

import ch.qos.logback.classic.spi.EventArgUtil;

/**
 * WrapperException.
 *
 * @author tuituidan
 * @version 1.0
 * @date 2020/11/22
 */
public class WrapperException extends RuntimeException {

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
