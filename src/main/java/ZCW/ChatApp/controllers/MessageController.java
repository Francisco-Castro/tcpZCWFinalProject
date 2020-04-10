package ZCW.ChatApp.controllers;

import ZCW.ChatApp.models.Channel;
import ZCW.ChatApp.models.Message;
import ZCW.ChatApp.models.User;
import ZCW.ChatApp.services.ChannelService;
import ZCW.ChatApp.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private static MessageService messageService;
    @Autowired
    private static ChannelService channelService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    public static MessageService getMessageService() {
        return messageService;
    }

    // POST
    //=============================================================================
    @PostMapping("/create")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message){
        return new ResponseEntity<>(messageService.create(message), HttpStatus.OK);
    }

//    @PostMapping("/channel/{channelId}")
//    public ResponseEntity<Message> addMessageToChannel(@RequestBody Message message, @PathVariable Long channelId){
//        Channel channel= channelService.getChannel(channelId);
//        message = messageService.postInChannel(message, channel.getId());
//        try{
//            return ResponseEntity
//                    .created(new URI( "/channel/" + channelId))
//                    .body(message);
//        } catch (URISyntaxException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    // GET
    //=============================================================================
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new ResponseEntity<>(messageService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Message>> showAll(){
        return new ResponseEntity<>((messageService.findAll()), HttpStatus.OK);
    }

//    // TODO Fix this to pass in user
//    @GetMapping("/sender/{user}")
//    public ResponseEntity<List<Message>> findBySender(@PathVariable User user, Pageable pageable){
//        return new ResponseEntity<>(messageService.findBySender(user, pageable), HttpStatus.OK);
//    }

    // PUT
    //=============================================================================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMessage(@RequestBody Message message, @PathVariable Long id){
        Optional<Message> existingMessage = messageService.findById(id);
        return existingMessage
                .map(m -> {
                    m.setContent(message.getContent());
                    messageService.save(m);
                    try{
                        return ResponseEntity
                                .ok()
                                .location(new URI("/" + m.getId()))
                                .body(m);
                    } catch(URISyntaxException e){
                        return ResponseEntity.status(HttpStatus.MULTI_STATUS.INTERNAL_SERVER_ERROR).build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    //=============================================================================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteMessage(@PathVariable Long id) {
        return new ResponseEntity<>(messageService.delete(id), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Boolean> deleteAllUsers() {
        return new ResponseEntity<>(messageService.deleteAll(), HttpStatus.NOT_FOUND);
    }

}
