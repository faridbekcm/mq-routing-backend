package com.banque.mq.service.impl;

import com.banque.mq.model.dto.MessageDTO;
import com.banque.mq.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MessageBatchPersister {

    private final MessageBuffer buffer;
    private final MessageService messageService;

    public MessageBatchPersister(MessageBuffer buffer, MessageService messageService) {
        this.buffer = buffer;
        this.messageService = messageService;
    }

    @Scheduled(fixedRate = 1000)
    public void flushBatch() {
        if (!buffer.isEmpty()) {
            List<MessageDTO> batch = buffer.drain(100);
            if (!batch.isEmpty()) {
                log.info("Persisting batch of {} messages", batch.size());
                messageService.saveAll(batch);
            }
        }
    }
}