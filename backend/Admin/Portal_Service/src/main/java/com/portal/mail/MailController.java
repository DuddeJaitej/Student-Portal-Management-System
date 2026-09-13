package com.portal.mail;

import com.portal.mail.dto.MailSendRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portal/mail")
@CrossOrigin("*")
public class MailController {

    private final MailService service;

    public MailController(MailService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public InternalMessage send(@Valid @RequestBody MailSendRequest request) {
        return service.send(request);
    }

    @GetMapping("/inbox")
    public List<InternalMessage> inbox(@RequestParam String role, @RequestParam String id) {
        return service.inbox(role, id);
    }

    @GetMapping("/sent")
    public List<InternalMessage> sent(@RequestParam String role, @RequestParam String id) {
        return service.sent(role, id);
    }

    @PatchMapping("/{messageId}/read")
    public InternalMessage markRead(
        @PathVariable Long messageId,
        @RequestParam String role,
        @RequestParam String id
    ) {
        return service.markRead(messageId, role, id);
    }

    @DeleteMapping("/{messageId}")
    public void deleteMessage(
        @PathVariable Long messageId,
        @RequestParam String role,
        @RequestParam String id
    ) {
        service.deleteMessage(messageId, role, id);
    }
}
