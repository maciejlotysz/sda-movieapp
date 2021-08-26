package com.maja.sdamovieapp.application.constants;

public enum ErrorCode {

    INVALID_INPUT_REGISTER_CREDENTIALS("INVALID_EMAIL_OR_LOGIN"),
    INVALID_LOGIN_CREDENTIALS("INVALID_EMAIL_OR_PASSWORD"),
    REGISTRATION_SUCCEED("REGISTRATION_SUCCEED");



    public final String internalCode;

    ErrorCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public static String getInternalCode(String internalCode) {
        ErrorCode[] errorCodes = ErrorCode.values();
        for (ErrorCode errorCode : errorCodes) {
            return errorCode.internalCode;
        }
        return internalCode;
    }
}
