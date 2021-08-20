package com.maja.sdamovieapp.order.entity;

import com.maja.sdamovieapp.order.enums.OrderStatusEnum;
import com.maja.sdamovieapp.user.entity.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "orers")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(name = "rent_start_date")
    private LocalDate startDate;

    @Column(name = "rent_end_date")
    private LocalDate endDate;

    @Column(name = "rent_price_per_day")
    private double dailyRentPrice;

    @Column(name = "total_price")
    private double totalPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatusEnum orderStatus;
}
