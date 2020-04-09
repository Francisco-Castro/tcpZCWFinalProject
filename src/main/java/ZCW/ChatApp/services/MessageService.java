package ZCW.ChatApp.services;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message save(Message message) { return messageRepository.save(message); }

    // POST
    //=============================================================================

    public Message create(Message message) {
        return messageRepository.save(message);
    }

    // GET
    //=============================================================================

    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public List<Message> findByChannel(Long channelId){
        return new ArrayList<>(messageRepository.findByChannelId(channelId));
    }

    // TODO FindAll messages By User Id

    // DELETE
    //=============================================================================

    public Boolean delete(Long id) {
        messageRepository.deleteById(id);
        return true;
    }

    public Boolean deleteAll() {
        messageRepository.deleteAll();
        return true;
    }




}
