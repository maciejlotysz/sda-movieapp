package com.maja.sdamovieapp.application.constants;

public enum ErrorCode {

    INVALID_INPUT_REGISTER_CREDENTIALS("INVALID_EMAIL_OR_LOGIN"),
    REGISTRATION_SUCCEED("REGISTRATION_SUCCEED"),
    INVALID_LOGIN_CREDENTIALS("INVALID_EMAIL_OR_PASSWORD"),
    USER_NOT_ACTIVE("USER_NOT_ACTIVE"),
    USER_IS_LOGGED("USER_IS_LOGGED"),
    USER_NOT_FOUND("USER_NOT_FOUND");


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
