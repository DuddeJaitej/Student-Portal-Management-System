package com.portal.mail;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternalMessageRepository extends JpaRepository<InternalMessage, Long> {

    List<InternalMessage> findByRecipientRoleAndRecipientIdOrderByCreatedAtDesc(String recipientRole, String recipientId);

    List<InternalMessage> findBySenderRoleAndSenderIdOrderByCreatedAtDesc(String senderRole, String senderId);
}
