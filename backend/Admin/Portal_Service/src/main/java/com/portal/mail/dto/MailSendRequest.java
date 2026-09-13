package com.portal.mail.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record MailSendRequest(
    @NotBlank String senderRole,
    @NotBlank String senderName,
    @NotBlank String senderId,
    @NotBlank String recipientRole,
    @NotBlank String recipientName,
    @NotBlank String recipientId,
    @NotBlank String subject,
    @NotBlank String body,
    List<AttachmentMetadata> attachments
) {
}
