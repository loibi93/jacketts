package com.loibi93.jacketts.data.http;

public class HttpException extends Throwable {
    private int code;
    private Exception originalException;

    public HttpException(int code) {
        this(code, null);
    }

    public HttpException(int code, Exception originalException) {
        this.code = code;
        this.originalException = originalException;
    }

    @Override
    public String getMessage() {
        return String.format("Error when executing HttpRequest: %s", code);
    }

    @Override
    public void printStackTrace() {
        if (originalException != null) {
            originalException.printStackTrace();
        } else {
            super.printStackTrace();
        }
    }
}
