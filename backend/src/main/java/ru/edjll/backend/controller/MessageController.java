package ru.edjll.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.edjll.backend.dto.message.*;
import ru.edjll.backend.service.MessageService;
import ru.edjll.backend.validation.exists.Exists;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.security.Principal;
import java.util.Collection;

@RestController
@Validated
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/users/{userId}/messages")
    @ResponseStatus(HttpStatus.OK)
    public Page<MessageDto> getAllMessageDtoBetweenUsersById(
            @PathVariable @Exists(table = "user_entity", column = "id") String userId,
            @RequestParam @NotNull @PositiveOrZero Integer page,
            @RequestParam @NotNull @Positive Integer size,
            Principal principal
    ) {
        return messageService.getAllMessageDtoBetweenUsersById(userId, page, size, principal);
    }

    @GetMapping("/users/messages/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageDto getMessageDtoById(
            @PathVariable Long id,
            Principal principal
    ) {
        return messageService.getMessageDtoById(id, principal);
    }

    @PostMapping("/users/{userId}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(
            @PathVariable String userId,
            Principal principal,
            @RequestBody @Valid MessageDtoForSave messageDtoForSave
    ) {
        messageService.save(userId, principal, messageDtoForSave);
    }

    @PutMapping("/users/messages/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(
            @PathVariable @Positive(message = "{message.id.positive}") Long id,
            Principal principal,
            @RequestBody @Valid MessageDtoForUpdate messageDtoForUpdate
    ) {
        messageService.update(id, principal, messageDtoForUpdate);
    }

    @PutMapping("/users/messages/{id}/viewed")
    @ResponseStatus(HttpStatus.OK)
    public void updateViewed(
            @PathVariable @Positive(message = "{message.id.positive}") Long id,
            Principal principal
    ) {
        messageService.updateViewed(id, principal);
    }

    @PutMapping("/users/{userId}/messages")
    @ResponseStatus(HttpStatus.OK)
    public void updateViewed(
            @PathVariable String userId,
            Principal principal
    ) {
        messageService.updateViewed(userId, principal);
    }

    @DeleteMapping("/users/messages/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable @Positive(message = "{message.id.positive}") Long id,
            Principal principal
    ) {
        messageService.delete(id, principal);
    }
}
