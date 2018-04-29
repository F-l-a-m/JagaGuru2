package lv.javaguru.java2.views;

import lv.javaguru.java2.businesslogic.MyTimestamp;
import lv.javaguru.java2.businesslogic.Session.ConsoleSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmptyMessageView implements View {
    
    @Autowired private ConsoleSession session;
    
    @Override
    public void execute( ) {
        String nickname = session.getUser( ).getNickname( );
        System.out.println( MyTimestamp.getStringTimestamp( ) + ' ' + nickname + ":" );
    }
}
