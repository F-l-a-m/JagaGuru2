package lv.javaguru.java2.businesslogic.user;

import lv.javaguru.java2.businesslogic.Error;
import lv.javaguru.java2.database.Database;
import lv.javaguru.java2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddUserService {

    @Autowired private Database database;
    @Autowired private NicknameValidator validator;

    public AddUserResponse addUser(String nickname) {
        List<Error> errors = validator.validate(nickname);
        if( errors.isEmpty() ) {
            User user = database.addNewGuest(nickname);
            return new AddUserResponse(user, null, true);
        } else {
            return new AddUserResponse(null, errors, false);
        }
    }
}
