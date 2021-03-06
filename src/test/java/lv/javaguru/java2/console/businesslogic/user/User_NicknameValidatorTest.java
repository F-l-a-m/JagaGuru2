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

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class User_NicknameValidatorTest {
    
    @Mock private UserRepository repository;
    @InjectMocks private User_NicknameValidator validator = new User_NicknameValidator( );
    
    @Test
    public void shouldNotReturnErrors( ) {
        String nickname1 = "ab"; // length of 2 is allowed
        String nickname2 = "TestNickname";
        String nickname3 = "0123456789123456"; // length of 16 is allowed
        User user = Mockito.mock( User.class );
        Mockito.when( repository.getByNickname( nickname1 )).thenReturn( Optional.empty( ) );
        Mockito.when( repository.getByNickname( nickname2 )).thenReturn( Optional.empty( ) );
        Mockito.when( repository.getByNickname( nickname3 )).thenReturn( Optional.empty( ) );
    
        List<Error> result1 = validator.validate( nickname1 );
        List<Error> result2 = validator.validate( nickname2 );
        List<Error> result3 = validator.validate( nickname3 );
        
        assertTrue( result1.isEmpty( ) );
        assertTrue( result2.isEmpty( ) );
        assertTrue( result3.isEmpty( ) );
    }
    
    @Test
    public void shouldReturnErrorsBecauseOfLength( ) {
        String nickname1 = "a"; // length of 2
        String nickname2 = "01234567891234567"; // length of 17
        User user = Mockito.mock( User.class );
        Mockito.when( repository.getByNickname( nickname1 )).thenReturn( Optional.empty( ) );
        Mockito.when( repository.getByNickname( nickname2 )).thenReturn( Optional.empty( ) );
        
        List<Error> result1 = validator.validate( nickname1 );
        List<Error> result2 = validator.validate( nickname2 );
        
        assertEquals( result1.size( ), 1 );
        assertEquals( result2.size( ), 1 );
    }
    
    @Test
    public void shouldReturnErrorsWhenNicknameIsEmpty( ) {
        String nickname1 = "";
        String nickname2 = " ";
        String nickname3 = "  ";
        String nickname4 = null;
        User user = Mockito.mock( User.class );
        Mockito.when( repository.getByNickname( nickname2 )).thenReturn( Optional.empty( ) );
        Mockito.when( repository.getByNickname( nickname3 )).thenReturn( Optional.empty( ) );
        
        List<Error> result1 = validator.validate( nickname1 );
        List<Error> result2 = validator.validate( nickname2 );
        List<Error> result3 = validator.validate( nickname3 );
        List<Error> result4 = validator.validate( nickname4 );
        
        assertEquals( result1.size( ), 1 );
        assertEquals( result2.size( ), 2 ); // One error for length, and one for restricted symbol
        assertEquals( result3.size( ), 1 );
        assertEquals( result4.size( ), 1 );
    }
    
    @Test
    public void shouldReturnErrorWhenNicknameIsShort( ) {
        String nickname = "a";
        User user = Mockito.mock( User.class );
        Mockito.when( repository.getByNickname( nickname )).thenReturn( Optional.empty( ) );
        
        List<Error> result = validator.validate( nickname );
        
        assertEquals( result.size( ), 1 );
    }
    
    @Test
    public void shouldReturnErrorsWhenNicknameContainsDisallowedSymbols( ) {
        String nickname1 = "$";
        String nickname2 = "!@";
        String nickname3 = "a$";
        String nickname4 = "$a";
        String nickname5 = "abc$%^qwerty";
        User user = Mockito.mock( User.class );
        Mockito.when( repository.getByNickname( nickname1 )).thenReturn( Optional.empty( ) );
        Mockito.when( repository.getByNickname( nickname2 )).thenReturn( Optional.empty( ) );
        Mockito.when( repository.getByNickname( nickname3 )).thenReturn( Optional.empty( ) );
        Mockito.when( repository.getByNickname( nickname4 )).thenReturn( Optional.empty( ) );
        Mockito.when( repository.getByNickname( nickname4 )).thenReturn( Optional.empty( ) );
        
        List<Error> result1 = validator.validate( nickname1 );
        List<Error> result2 = validator.validate( nickname2 );
        List<Error> result3 = validator.validate( nickname3 );
        List<Error> result4 = validator.validate( nickname4 );
        List<Error> result5 = validator.validate( nickname5 );
        
        assertEquals( result1.size( ), 2 ); // One error for length and one for restricted symbol
        assertEquals( result2.size( ), 1 );
        assertEquals( result3.size( ), 1 );
        assertEquals( result4.size( ), 1 );
        assertEquals( result5.size( ), 1 );
    }
}
