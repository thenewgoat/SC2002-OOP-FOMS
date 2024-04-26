package enums;

/**
 * The possible status values for an order.
 */
public enum OrderStatus {
    /**
     * Represents an order that is still being prepared.
     */
    PREPARING,
    /**
     * Represents an order that is ready for collection.
     */
    READY,
    /**
     * Represents an order that has already been collected.
     */
    COMPLETED,
    /**
     * Represents an order that has been cancelled.
     */
    CANCELLED,
    /**
     * Represents an order that is unavailable.
     */
    UNAVAILABLE
}
