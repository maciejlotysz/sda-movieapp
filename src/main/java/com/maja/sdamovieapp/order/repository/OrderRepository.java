package com.maja.sdamovieapp.order.repository;

import com.maja.sdamovieapp.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findOrderByUser_Email(String email);
}
