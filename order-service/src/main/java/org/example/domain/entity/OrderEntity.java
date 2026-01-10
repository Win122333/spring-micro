package org.example.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Table(name = "order_")
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

    @Column(name = "customerId")
    private Long customerId;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Column(name = "address")
    private String address;

    @Column(name = "eta_minutes")
    private Integer etaMinutes;

    @Column(name = "amount", precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "courier_name")
    private String courierName;

    @OneToMany(mappedBy = "id", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<OrderItemEntity> items = new LinkedHashSet<>();
}
