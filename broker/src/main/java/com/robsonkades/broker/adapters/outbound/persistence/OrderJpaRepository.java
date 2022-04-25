package com.robsonkades.broker.adapters.outbound.persistence;

import com.robsonkades.broker.adapters.outbound.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<Order, UUID> { }
