package lv.javaguru.java2.console.businesslogic.user;

import lv.javaguru.java2.console.businesslogic.Error;
import lv.javaguru.java2.console.businesslogic.user.commonValidators.User_NicknameValidator;
import lv.javaguru.java2.console.database.UserRepository;
import lv.javaguru.java2.console.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class User_AddServiceTest {
    
    @Mock private UserRepository userRepository;
    @Mock private User_NicknameValidator validator;
    
    @InjectMocks
    private User_AddService userAddService = new User_AddService( );
    
    @Test
    public void shouldAddNewUser( ) {
        String userNickname = "TestUser";
        List<Error> errors = new ArrayList<>( );
        User user = Mockito.mock( User.class );
        Mockito.when( validator.validate( userNickname ) )
                .thenReturn( errors );
        Mockito.when( userRepository.getByNickname( userNickname ) )
                .thenReturn( Optional.empty( ) ); // User not found, create new
        
        User_AddResponse userAddResponse = userAddService.addUser( userNickname );
        
        assertTrue( userAddResponse.isSuccess( ) );
        assertNotNull( userAddResponse.getUser( ) );
        assertNull( userAddResponse.getErrors( ) );
    }
    
    @Test
    public void shouldFindExistingUser( ) {
        String userNickname = "TestUser";
        List<Error> errors = new ArrayList<>( );
        User user = Mockito.mock( User.class );
        Mockito.when( validator.validate( userNickname ) )
                .thenReturn( errors );
        Mockito.when( userRepository.getByNickname( userNickname ) )
                .thenReturn( Optional.of( user ) ); // User already exists in db
        
        User_AddResponse userAddResponse = userAddService.addUser( userNickname );
        
        assertTrue( userAddResponse.isSuccess( ) );
        assertNotNull( userAddResponse.getUser( ) );
        assertNull( userAddResponse.getErrors( ) );
    }
}
