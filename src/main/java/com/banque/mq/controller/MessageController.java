package com.banque.mq.controller;

import com.banque.mq.model.dto.MessageDTO;
import com.banque.mq.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public Page<MessageDTO> getAllMessages(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(required = false) String source,
                                           @RequestParam(required = false) String status,
                                           @RequestParam(required = false)
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                           LocalDateTime receivedAt) {
        Pageable pageable = PageRequest.of(page, size);
        return messageService.getFilteredMessages(pageable, source, status, receivedAt);
    }

    @GetMapping("/{id}")
    public MessageDTO getMessageById(@PathVariable Long id) {
        return messageService.getMessageById(id);
    }
}