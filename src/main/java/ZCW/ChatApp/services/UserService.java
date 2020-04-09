package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.repositories.ChannelRepository;
import ZCW.ChatApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepo;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private MessageService messageService;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User save(User user){
        return userRepo.save(user);
    }

    // POST
    //=============================================================================
    public User create(User user) throws Exception{
        if(!userRepo.findByUserName(user.getUserName()).isPresent()){
            return userRepo.save(user);
        }
        throw new Exception("Username is taken. Try something else.");
    }

    // TODO send message to user
//    public Message sendMessageToUser(Long senderId, Long recipientId){
//        User sender = userRepo.getOne(senderId);
//        User recipient = userRepo.getOne(recipientId);
//        Message message = messageService.create(new Message());
//        return null;
//    }

    // TODO Refactor Too much going on here
    public Message sendMessageToChannel(Long messageId, Long channelId){
        Message message = messageService.getMessage(messageId);
        Channel channel = channelService.getChannel(channelId);
        message.setChannel(channel);
        channel.getMessages().add(message);
        channelService.saveChannel(channel);
        return messageService.save(message);
    }

    // GET
    //=============================================================================

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public Optional<User> findById(Long id){
        return userRepo.findById(id);
    }

    public User getUser(Long id){
        return userRepo.getOne(id);
    }

    public Optional<User> findUserByUsername(String username){ return userRepo.findByUserName(username); }

<<<<<<< HEAD
    public Optional<User> findUserByFirstName(String firstName) {return userRepo.findByFirstName(firstName);}

    public Optional<User> findUserByLastName(String lastName) { return userRepo.findByLastName(lastName);}


    // TODO Get All User Messages user message Service ADD ENDPOINT TO CONTROLLER
    // TODO GET ALL User Channels
    // TODO GET ALL Messages By Channel
=======
    // TODO TEST
    public List<User> findUsersByChannel(Long id){
        return userRepo.findAllByChannels(channelService.getChannel(id));
    }
>>>>>>> dcc084e212a3b701ab23e11a6f8681b88789a89c

    // UPDATE
    //=============================================================================
    public User updateConnection(Long id){
        User original = userRepo.getOne(id);
        if (original.isConnected()) {
            original.setConnected(false);
        } else {
            original.setConnected(true);
        }
        return userRepo.save(original);
    }

    public User joinChannelById(Long userId, Long channelId){
        User original = userRepo.getOne(userId);
        Channel channel = channelService.getChannel(channelId);
        if(!channel.getPrivate()){
            original.getChannels().add(channel);
            channel.getUsers().add(original);
            channelService.saveChannel(channel);
        }
        return userRepo.save(original);
    }

    public User leaveChannelById(Long userId, Long channelId){
        User original = userRepo.getOne(userId);
        Channel channel = channelService.getChannel(channelId);
        original.getChannels().remove(channel);
        return userRepo.save(original);
    }

    // DELETE
    //=============================================================================
    public Boolean deleteUser(Long id){
        userRepo.deleteById(id);
        return true;
    }

    public Boolean deleteAll(){
        userRepo.deleteAll();
        return true;
    }

}
