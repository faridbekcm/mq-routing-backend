package com.banque.mq.model.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "partners")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String alias;

    @Column(nullable = false)
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Direction direction;

    private String application;

    @Enumerated(EnumType.STRING)
    @Column(name = "flow_type", nullable = false)
    private ProcessedFlowType processedFlowType;

    @Column(nullable = false)
    private String description;

    public enum Direction {
        INBOUND,
        OUTBOUND
    }

    public enum ProcessedFlowType {
        MESSAGE,
        ALERTING,
        NOTIFICATION
    }
}
