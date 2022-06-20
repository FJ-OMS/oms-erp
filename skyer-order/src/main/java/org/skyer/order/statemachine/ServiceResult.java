package org.skyer.order.statemachine;

public class ServiceResult {

    private boolean result;

    private String message;

    public ServiceResult(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public boolean isSuccess() {
        return result;
    }

    public String getMessage() {
        return message;
    }

}
