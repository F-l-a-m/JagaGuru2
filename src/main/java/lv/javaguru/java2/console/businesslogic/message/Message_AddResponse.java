package lv.javaguru.java2.console.businesslogic.message;

import lv.javaguru.java2.console.businesslogic.Error;
import lv.javaguru.java2.console.domain.Message;

import java.util.List;

public class Message_AddResponse {

    private boolean success;
    private List<Error> errors;
    private Message message;

    public Message_AddResponse( boolean success, Message message, List<Error> errors) {
        this.success = success;
        this.errors = errors;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
