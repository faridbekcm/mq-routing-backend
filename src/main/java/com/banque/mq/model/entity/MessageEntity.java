package com.banque.mq.model.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String payload;
    private String source;
    private LocalDateTime receivedAt;
    private String status;
}
