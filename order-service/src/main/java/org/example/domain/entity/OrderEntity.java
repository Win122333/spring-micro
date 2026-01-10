package org.example.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "order")
@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;
}
