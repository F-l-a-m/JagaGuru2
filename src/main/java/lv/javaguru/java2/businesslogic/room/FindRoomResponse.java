package lv.javaguru.java2.businesslogic.room;

import lv.javaguru.java2.businesslogic.Error;
import lv.javaguru.java2.domain.Room;

import java.util.List;

public class FindRoomResponse {
    private Room room;
    private List<Error> errors;
    private boolean success;

    public FindRoomResponse(Room room, List<Error> errors, boolean success) {
        this.room = room;
        this.errors = errors;
        this.success = success;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
