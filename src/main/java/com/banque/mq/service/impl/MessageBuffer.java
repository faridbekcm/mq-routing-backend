package com.banque.mq.service.impl;

import com.banque.mq.model.dto.MessageDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class MessageBuffer {
    private final BlockingQueue<MessageDTO> buffer = new LinkedBlockingQueue<>();

    public void add(MessageDTO dto) {
        buffer.offer(dto);
    }

    public List<MessageDTO> drain(int max) {
        List<MessageDTO> batch = new ArrayList<>();
        buffer.drainTo(batch, max);
        return batch;
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }
}