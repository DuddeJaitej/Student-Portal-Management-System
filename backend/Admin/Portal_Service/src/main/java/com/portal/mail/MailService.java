package com.portal.mail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.mail.dto.MailSendRequest;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MailService {

    private final InternalMessageRepository repository;
    private final ObjectMapper objectMapper;

    public MailService(InternalMessageRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public InternalMessage send(MailSendRequest request) {
        String senderRole = normalizeRole(request.senderRole());
        String recipientRole = normalizeRole(request.recipientRole());
        if (senderRole.equals("STUDENT") && !recipientRole.equals("FACULTY") && !recipientRole.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Students may send messages only to individual faculty or admin recipients.");
        }

        InternalMessage message = new InternalMessage();
        message.setSenderRole(senderRole);
        message.setSenderName(request.senderName().trim());
        message.setSenderId(request.senderId().trim());
        message.setRecipientRole(recipientRole);
        message.setRecipientName(request.recipientName().trim());
        message.setRecipientId(request.recipientId().trim());
        message.setSubject(request.subject().trim());
        message.setBody(request.body().trim());
        message.setAttachmentsJson(toJson(request));
        return repository.save(message);
    }

    public List<InternalMessage> inbox(String role, String id) {
        return repository.findByRecipientRoleAndRecipientIdOrderByCreatedAtDesc(normalizeRole(role), required(id, "Recipient id"));
    }

    public List<InternalMessage> sent(String role, String id) {
        return repository.findBySenderRoleAndSenderIdOrderByCreatedAtDesc(normalizeRole(role), required(id, "Sender id"));
    }

    public InternalMessage markRead(Long messageId, String role, String id) {
        InternalMessage message = repository.findById(messageId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found."));

        if (!message.getRecipientRole().equals(normalizeRole(role)) || !message.getRecipientId().equals(required(id, "Recipient id"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This message belongs to another mailbox.");
        }

        message.markRead();
        return repository.save(message);
    }

    public void deleteMessage(Long messageId, String role, String id) {
        InternalMessage message = repository.findById(messageId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found."));

        if (!message.getRecipientRole().equals(normalizeRole(role)) || !message.getRecipientId().equals(required(id, "Recipient id"))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This message belongs to another mailbox.");
        }

        repository.deleteById(messageId);
    }

    private String toJson(MailSendRequest request) {
        try {
            return objectMapper.writeValueAsString(request.attachments() == null ? List.of() : request.attachments());
        } catch (JsonProcessingException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid attachment metadata.");
        }
    }

    private String normalizeRole(String role) {
        String value = required(role, "Role").toUpperCase();
        if (!value.equals("STUDENT") && !value.equals("FACULTY") && !value.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role must be STUDENT, FACULTY, or ADMIN.");
        }
        return value;
    }

    private String required(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, field + " is required.");
        }
        return value.trim();
    }
}
