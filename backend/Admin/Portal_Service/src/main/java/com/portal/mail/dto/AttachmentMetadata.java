package com.portal.mail.dto;

public record AttachmentMetadata(
    String fileName,
    String contentType,
    long size,
    String data
) {
}
