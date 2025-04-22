package com.banque.mq.service;

import com.banque.mq.model.dto.MessageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageService {
    MessageDTO getMessageById(Long id);
    Page<MessageDTO> getFilteredMessages(Pageable pageable, String source, String status, LocalDateTime receivedAt);
    void saveAll(List<MessageDTO> messages);
}