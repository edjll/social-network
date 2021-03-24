package ru.edjll.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.edjll.backend.dto.MessageDto;
import ru.edjll.backend.entity.Message;

import java.util.Collection;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select new ru.edjll.backend.dto.MessageDto(" +
                "m.id, m.text, m.createdDate, m.modifiedDate, " +
                "s.id, s.firstName, s.lastName, s.username, " +
                "r.id, r.firstName, r.lastName, r.username) " +
            "from Message m join m.sender s join m.recipient r " +
            "where m.sender.id in (:senderId, :recipientId) " +
            "and m.recipient.id in (:senderId, :recipientId) " +
            "order by m.createdDate")
    Collection<MessageDto> getAllMessageDtoBetweenUsersById(@Param("senderId") String senderId, @Param("recipientId") String recipientId);

    @Query("select new ru.edjll.backend.dto.MessageDto(" +
                "m.id, m.text, m.createdDate, m.modifiedDate, " +
                "s.id, s.firstName, s.lastName, s.username, " +
                "r.id, r.firstName, r.lastName, r.username) " +
            "from Message m join m.sender s join m.recipient r " +
            "where m.id = :id ")
    MessageDto getMessageDtoById(@Param("id") Long id);
}