package com.demo.kinesis.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The {@code CartAbandonmentEvent} class represents an event that captures information
 * about a cart abandonment incident in an e-commerce system. It provides details
 * about the items in the cart, the customer who abandoned the cart, the seller's ID,
 * and the timestamp of the abandonment event.
 * <p>
 * Instances of this class are used to record and analyze cart abandonment behavior
 * within the e-commerce platform, enabling the identification of trends and the
 * development of strategies to recover abandoned carts.
 */
@Data
public class CartAbandonmentEvent {
    static final long MAX_CART_ABANDONMENT_TIME_MINUTES = 60;

    @JsonProperty("cart_items")
    private List<CartItem> cartItems;

    @JsonProperty("customer_id")
    private String customerID;

    @JsonProperty("seller_id")
    private String sellerID;

    @JsonProperty("event_time")
    private long timestamp;

    @Builder()
    public CartAbandonmentEvent(List<CartItem> cartItems, String customerID, String sellerID) {
        this.cartItems = cartItems;
        this.customerID = customerID;
        this.sellerID = sellerID;
        this.timestamp = generateTimestamp();
    }

    /**
     * Generates a random timestamp within a specified time range to simulate cart abandonment events.
     * <p>
     * This method calculates a random timestamp between the current time minus
     * a maximum predefined duration (MAX_CART_ABANDONMENT_TIME_MINUTES) and the current time.
     *
     * @return A randomly generated timestamp in seconds.
     */
    private static long generateTimestamp() {
        // Calculate the inclusive start and exclusive end timestamps
        Instant startInclusive = Instant.now().minus(Duration.ofMinutes(MAX_CART_ABANDONMENT_TIME_MINUTES));
        Instant endExclusive = Instant.now();

        // Convert the timestamps to milliseconds
        long startMillis = startInclusive.toEpochMilli();
        long endMillis = endExclusive.toEpochMilli();

        // Generate a random timestamp in milliseconds within the specified range
        return ThreadLocalRandom.current().nextLong(startMillis, endMillis);
    }
}
