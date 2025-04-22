package com.banque.mq.service.impl;

import com.banque.mq.mapper.MessageMapper;
import com.banque.mq.model.dto.MessageDTO;
import com.banque.mq.model.entity.MessageEntity;
import com.banque.mq.repository.MessageRepository;
import com.banque.mq.repository.MessageSpecifications;
import com.banque.mq.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public MessageDTO getMessageById(Long id) {
        return messageRepository.findById(id)
                .map(messageMapper::toDto)
                .orElse(null);
    }

    @Override
    public Page<MessageDTO> getFilteredMessages(Pageable pageable, String source, String status, LocalDateTime receivedAt) {
        return messageRepository.findAll(
                MessageSpecifications.buildFilter(source, status, receivedAt), pageable
        ).map(messageMapper::toDto);
    }

    @Override
    public void saveAll(List<MessageDTO> messages) {
        List<MessageEntity> entities = messages.stream()
                .map(messageMapper::toEntity)
                .collect(Collectors.toList());
        messageRepository.saveAll(entities);
    }
}