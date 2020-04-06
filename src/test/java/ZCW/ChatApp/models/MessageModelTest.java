package ZCW.ChatApp.models;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;

public class MessageModelTest {

    private Message message;

    @Before
    public void setup(){
        this.message = new Message();
    }

    @Test
    public void constructorTest(){
        Message message = new Message(new User(), "content");
        assertEquals("content", message.getContent());
    }

    @Test
    public void getMessageTest(){
        Long expected = 1L;
        message.setId(1L);
        assertEquals(expected, message.getId());
    }

    @Test
    public void getSenderTest(){
        User user = new User();
        user.setUserName("John");
        message.setSender(user);
        assertEquals("John", message.getSender().getUserName() );
    }

    @Test
    public void getContentTest(){
        String expected = "We TDD out here son";
        message.setContent(expected);
        assertEquals(expected, message.getContent());
    }

}