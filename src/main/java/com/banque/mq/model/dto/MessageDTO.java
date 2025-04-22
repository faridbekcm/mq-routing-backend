package com.banque.mq.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MessageDTO {
    private String payload;
    private String source;
    private LocalDateTime receivedAt;
    private String status;
}
