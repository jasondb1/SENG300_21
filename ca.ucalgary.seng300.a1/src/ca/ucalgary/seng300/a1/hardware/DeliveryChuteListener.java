package ca.ucalgary.seng300.a1.hardware;

/**
 * Listens for events emanating from a delivery chute.
 */
public interface DeliveryChuteListener extends AbstractHardwareListener {
    /**
     * Indicates that an item has been delivered to the indicated delivery
     * chute.
     * 
     * @param chute
     *            The device on which the event occurred.
     */
    void itemDelivered(DeliveryChute chute);

    /**
     * Indicates that the door of the indicated delivery chute has been opened.
     * 
     * @param chute
     *            The device on which the event occurred.
     */
    void doorOpened(DeliveryChute chute);

    /**
     * Indicates that the door of the indicated delivery chute has been closed.
     * 
     * @param chute
     *            The device on which the event occurred.
     */
    void doorClosed(DeliveryChute chute);

    /**
     * Indicates that the delivery chute will not be able to hold any more
     * items.
     * 
     * @param chute
     *            The device on which the event occurred.
     */
    void chuteFull(DeliveryChute chute);
}
