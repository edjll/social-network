package ru.edjll.backend.service;

import org.springframework.stereotype.Service;
import ru.edjll.backend.dto.message.MessageDto;
import ru.edjll.backend.dto.message.MessageDtoForDelete;
import ru.edjll.backend.dto.message.MessageDtoForSave;
import ru.edjll.backend.dto.message.MessageDtoForUpdate;
import ru.edjll.backend.entity.Message;
import ru.edjll.backend.repository.MessageRepository;

import java.util.Collection;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public MessageDto save(MessageDtoForSave messageDtoForSave) {
        Message savedMessage = messageRepository.save(messageDtoForSave.toMessage());
        return this.getMessageDtoById(savedMessage.getId());
    }

    public MessageDto update(MessageDtoForUpdate messageDtoForUpdate) {
        Message savedMessage = messageRepository.save(messageDtoForUpdate.toMessage());
        return this.getMessageDtoById(savedMessage.getId());
    }

    public void delete(MessageDtoForDelete messageDtoForDelete) {
        messageRepository.deleteById(messageDtoForDelete.getId());
    }

    public Collection<MessageDto> getAllMessageDtoBetweenUsersById(String senderId, String recipientId) {
        return messageRepository.getAllMessageDtoBetweenUsersById(senderId, recipientId);
    }

    public MessageDto getMessageDtoById(Long id) {
        return messageRepository.getMessageDtoById(id);
    }
}
