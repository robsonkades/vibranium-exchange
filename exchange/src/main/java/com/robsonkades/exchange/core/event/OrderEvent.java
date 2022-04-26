package com.robsonkades.exchange.core.event;

import com.robsonkades.exchange.core.domain.Side;
import com.robsonkades.exchange.core.domain.Value;

public class OrderEvent {

    public interface Event {
    }

    public static class Match extends Value implements Event {

        public final String restingWallet;
        public final String incomingWallet;
        public final String restingOrderId;
        public final String incomingOrderId;
        public final Side incomingSide;
        public final long price;
        public final long executedQuantity;
        public final long remainingQuantity;

        public Match(String restingWallet, String incomingWallet, String restingOrderId, String incomingOrderId, Side incomingSide,
                     long price, long executedQuantity, long remainingQuantity) {
            this.incomingWallet = incomingWallet;
            this.restingWallet = restingWallet;
            this.restingOrderId    = restingOrderId;
            this.incomingOrderId   = incomingOrderId;
            this.incomingSide      = incomingSide;
            this.price             = price;
            this.executedQuantity  = executedQuantity;
            this.remainingQuantity = remainingQuantity;
        }
    }

    public static class Add extends Value implements Event {

        public final String wallet;
        public final String orderId;
        public final Side side;
        public final long price;
        public final long size;

        public Add(String wallet, String orderId, Side side, long price, long size) {
            this.wallet = wallet;
            this.orderId = orderId;
            this.side    = side;
            this.price   = price;
            this.size    = size;
        }
    }

    public static class Cancel extends Value implements Event {
        public final String wallet;
        public final String orderId;
        public final long canceledQuantity;
        public final long remainingQuantity;

        public Cancel(String wallet, String orderId, long canceledQuantity, long remainingQuantity) {
            this.wallet = wallet;
            this.orderId           = orderId;
            this.canceledQuantity  = canceledQuantity;
            this.remainingQuantity = remainingQuantity;
        }
    }
}
