package de.extremeenvironment.messageservice.domain;


import de.extremeenvironment.messageservice.domain.validation.ValidateMethod;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
@ValidateMethod(method = "checkUserIsMemberOfConversation", message = "user must be part of conversations members")
public class Message extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "message_text", nullable = false)
    private String messageText;

    @ManyToOne
    private UserHolder user;


    @ManyToOne
    private Conversation conversation;

    public Message() {
    }

    public Message(String messageText) {
        this.messageText = messageText;
    }

    public Message(String messageText, UserHolder userHolder) {
        this.messageText = messageText;
        this.user = userHolder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public UserHolder getUser() {
        return user;
    }

    public void setUser(UserHolder userHolder) {
        this.user = userHolder;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public boolean checkUserIsMemberOfConversation() {
        boolean conversationIsNull = conversation == null;
        boolean userNotNullAndInConversationMembers = user != null && !conversationIsNull && conversation.getUsers().contains(user);

        return conversationIsNull || userNotNullAndInConversationMembers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        if(message.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + id +
            ", messageText='" + messageText + "'" +
            '}';
    }
}
