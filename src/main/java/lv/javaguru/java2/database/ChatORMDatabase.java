package lv.javaguru.java2.database;

import lv.javaguru.java2.businesslogic.message.MyTimestamp;
import lv.javaguru.java2.domain.Message;
import lv.javaguru.java2.domain.Room;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.domain.UserInRoom;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ChatORMDatabase implements Database {
    
    @Autowired SessionFactory sessionFactory;
    
    private Session session( ) {
        return sessionFactory.getCurrentSession( );
    }
    
    @Override
    public User addUser( String nickname ) {
        User user = new User( nickname, MyTimestamp.getSQLTimestamp( ) );
        Long id = (Long) session( ).save( user );
        user.setId( id );
        return user;
    }
    
    @Override
    public void updateUserActiveStatus( User user, boolean activeStatus ) {
        user.setActive( activeStatus );
        session( ).update( user );
    }
    
    @Override
    public Optional<User> findUser( Long userId ) {
        User user = (User) session( ).createCriteria( User.class )
                .add( Restrictions.eq( "id", userId ) )
                .uniqueResult( );
        return Optional.ofNullable( user );
    }
    
    @Override
    public Optional<User> findUser( String nickname ) {
        User user = (User) session( ).createCriteria( User.class )
                .add( Restrictions.eq( "nickname", nickname ) )
                .uniqueResult( );
        return Optional.ofNullable( user );
    }
    
    @Override
    public void changeUserNickname( String oldNickname, String newNickname ) {
        User user = (User) session( ).createCriteria( User.class )
                .add( Restrictions.eq( "nickname", oldNickname ) )
                .uniqueResult( );
        user.setNickname( newNickname );
        session( ).update( user );
    }
    
    @Override
    public void changeUserNickname( User user, String newNickname ) {
        user.setNickname( newNickname );
        session( ).update( user );
    }
    
    @Override
    public void addUserToRoom( Long userId, Long roomId ) {
        UserInRoom userInRoom = new UserInRoom( userId, roomId );
        session( ).save( userInRoom );
    }
    
    @Override
    public void removeUserFromRoom( Long userId, Long roomId ) {
    
    }
    
    @Override
    public boolean findUserInRoom( Long userId, Long roomId ) {
        /*User user = (User) session( ).createCriteria( User.class )
                .add( Restrictions.eq( "id", userId) )
                .uniqueResult( );
        return user != null;*/
        UserInRoom userInRoom = (UserInRoom) session( ).createCriteria( UserInRoom.class )
                .add( Restrictions.eq( "user_id", userId ) )
                .add( Restrictions.eq( "room_id", roomId ) )
                .uniqueResult( );
        if ( userInRoom == null )
            return false;
        else return true;
    }
    
    @Override
    public List<Room> getAListOfJoinedRooms( Long userId ) {
        List<UserInRoom> listOfRoomIds = new ArrayList<>( );
        
        /*Room room;
        Query query = session().createQuery( "select user_in_room.room_id from UserInRoom user_in_room" +
                " where user_id = :userId" );
        query.setParameter( "userId", userId );
        List results = query.list();*/
        
        /*Query query = session( ).createQuery( "from UserInRoom where user_id = :userId" );
        query.setParameter( "userId", userId );
        List roomIdList = query.list( );*/
        
        int i = 0;
        return null;
    }
    
    @Override
    public Optional<Room> createNewChatRoom( String roomName, String creatorNickname ) {
        Room room = new Room( roomName, creatorNickname, MyTimestamp.getSQLTimestamp( ) );
        Long id = (Long) session( ).save( room );
        room.setId( id );
        return Optional.of( room );
    }
    
    @Override
    public Optional<Room> findChatRoom( Long roomId ) {
        return Optional.empty( );
    }
    
    @Override
    public Optional<Room> findChatRoom( String roomName ) {
        Room room = (Room) session( ).createCriteria( Room.class )
                .add( Restrictions.eq( "name", roomName ) )
                .uniqueResult( );
        return Optional.ofNullable( room );
    }
    
    @Override
    public List getListOfAllRooms( ) {
        return session( )
                .createCriteria( Room.class )
                .list( );
    }
    
    @Override
    public Message addChatMessage( String messageBody, String nickname, Long roomId ) {
        Message message = new Message( MyTimestamp.getSQLTimestamp( ), nickname, messageBody, roomId );
        Long id = (Long) session( ).save( message );
        message.setId( id );
        return message;
    }
    
    @Override
    public List getAllChatHistoryInRoom( Long roomId ) {
        return session( )
                .createCriteria( Message.class )
                .list( );
    }
}