package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;

import ZCW.ChatApp.models.User;
import ZCW.ChatApp.repositories.ChannelRepository;
import ZCW.ChatApp.repositories.MessageRepository;
import ZCW.ChatApp.repositories.UserRepository;

import ZCW.ChatApp.repositories.MessageRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.awt.print.Pageable;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MessageServiceTest {

    @Autowired
    private MessageService service;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private UserService userService;

    @MockBean
    private MessageRepository repo;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ChannelRepository channelRepository;

    @Test
    @DisplayName("Test findById Success")
    public void findByIdSuccessTest(){
        // Set Up mock object and repo
        Message mockMessage = new Message(new User(), "testing time", new Date(), new Channel());
        doReturn(Optional.of(mockMessage)).when(repo).findById(1L);
        // Execute Call
        Optional<Message> returnMessage = service.findById(1L);
        // Check Assertions
        Assertions.assertTrue(returnMessage.isPresent(), "No Message was found");
        Assertions.assertSame(returnMessage.get(), mockMessage, "Models don't match");
    }

    @Test
    @DisplayName("Test findById Fail")
    public void findByIdFailTest(){
        doReturn(Optional.empty()).when(repo).findById(1L);

        Optional<Message> returnMessage = service.findById(1L);

        Assertions.assertFalse(returnMessage.isPresent(), "Message was not found");
    }

    @Test
    @DisplayName("Test findAll")
    public void findAllTest(){
        Message mockMessage1 = new Message(new User(), "testing time", new Date(), new Channel());
        Message mockMessage2 = new Message(new User(), "testing time", new Date(), new Channel());
        doReturn(Arrays.asList(mockMessage1, mockMessage2)).when(repo).findAll();

        List<Message> returnList = service.findAll();

        Assertions.assertEquals(2, returnList.size(), "findAll should return 2 messages");
    }

    @Test
    @DisplayName("Test create Message")
    public void createMessageTest() {
        User mockUser = new User(1L, "Bob", "Dole", "Lame", "password", true);
        Channel mockChannel = new Channel(1L, "General", new HashSet<>(Collections.singleton(mockUser)), true);
        Message mockMessage = new Message(mockUser, "testing time", new Date(), mockChannel);
        doReturn(mockMessage).when(repo).save(any());
        doReturn(mockUser).when(userRepository).getOne(1L);
        doReturn(mockChannel).when(channelRepository).getOne(1L);

        Message returnMessage = service.create(mockMessage, 1L, 1L);

        Assertions.assertEquals(returnMessage.getContent(), mockMessage.getContent());
    }

    @Test
    public void getMessageTest(){
        Message mockMessage = new Message(new User(), "testing time", new Date(), new Channel());
        doReturn(mockMessage).when(repo).getOne(mockMessage.getId());

        Message returnMessage = service.getMessage(mockMessage.getId());

        Assertions.assertNotNull(returnMessage);
    }

    @Test
    @DisplayName("Test create Message")
    public void saveMessageTest() {
        Message mockMessage = new Message(new User(), "testing time", new Date(), new Channel());
        doReturn(mockMessage).when(repo).save(any());

        Message returnMessage = service.save(mockMessage);

        Assertions.assertNotNull(returnMessage, "The Message should not be null");
    }

    @Test
    @DisplayName("Test find messages by user id")
    public void findMessagesByUserIdTest(){
        User user = new User();
        user.setId(1L);
        Message mockMessage = new Message(user, "testing", new Date(), new Channel());
        Message mockMessage2 = new Message(user, "testing 2", new Date(), new Channel());
        doReturn(Arrays.asList(mockMessage, mockMessage2)).when(repo).findMessagesBySender_Id(user.getId());

        List<Message> expected = service.findMessagesByUserId(user.getId());

        Assertions.assertEquals(expected.size(), 2);
    }

    @Test
    public void findMessagesByChannelTest(){
        Channel channel = new Channel();
        Message mockMessage = new Message(new User(), "testing", new Date(), channel);
        Message mockMessage2 = new Message(new User(), "testing 2", new Date(), channel);

        doReturn(Arrays.asList(mockMessage, mockMessage2)).when(repo).findByChannelId(channel.getId());

        List<Message> expected = service.findByChannel(channel.getId());

        Assertions.assertEquals(expected.size(), 2);
    }

    @Test
    public void deleteMessageTest(){
        Message mockMessage = new Message(new User(), "testing time", new Date(), new Channel());
        doReturn(mockMessage).when(repo).save(mockMessage);
        doReturn(mockMessage).when(repo).getOne(1L);

        Boolean actual = service.delete(1L);

        Assertions.assertTrue(actual);
    }

    @Test
    public void deleteAllTest(){
        Message mockMessage1 = new Message(new User(), "testing time", new Date(), new Channel());
        Message mockMessage2 = new Message(new User(), "testing time", new Date(), new Channel());
        doReturn(Arrays.asList(mockMessage1, mockMessage2)).when(repo).findAll();

        List<Message> returnMessages = service.findAll();
        Boolean actual = service.deleteAll();

        Assertions.assertTrue(actual);
    }

}

