package com.robsonkades.exchange.adapters.outbound.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.robsonkades.exchange.core.domain.Side;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "order_book_transactions")
public class TransactionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String restingWallet;

    @Column(nullable = false)
    private String incomingWallet;

    @Column(nullable = false)
    private String restingOrderId;

    @Column(nullable = false)
    private String incomingOrderId;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false)
    private long executedQuantity;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Side side;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRestingWallet() {
        return restingWallet;
    }

    public void setRestingWallet(String restingWallet) {
        this.restingWallet = restingWallet;
    }

    public String getIncomingWallet() {
        return incomingWallet;
    }

    public void setIncomingWallet(String incomingWallet) {
        this.incomingWallet = incomingWallet;
    }

    public String getRestingOrderId() {
        return restingOrderId;
    }

    public void setRestingOrderId(String restingOrderId) {
        this.restingOrderId = restingOrderId;
    }

    public String getIncomingOrderId() {
        return incomingOrderId;
    }

    public void setIncomingOrderId(String incomingOrderId) {
        this.incomingOrderId = incomingOrderId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getExecutedQuantity() {
        return executedQuantity;
    }

    public void setExecutedQuantity(long executedQuantity) {
        this.executedQuantity = executedQuantity;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }
}
