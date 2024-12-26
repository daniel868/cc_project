package org.business.utils;

public class AppUtils {
    public static GenericResponse<?> createGenericResponse(int statusCode, String errorMessage, boolean success) {
        GenericResponse<?> genericResponse = new GenericResponse<>();
        genericResponse.setErrorMessage(errorMessage);
        genericResponse.setStatusCode(statusCode);
        genericResponse.setSuccess(success);
        return genericResponse;
    }
}
