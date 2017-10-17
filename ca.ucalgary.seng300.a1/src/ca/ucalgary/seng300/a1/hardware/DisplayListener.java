package ca.ucalgary.seng300.a1.hardware;

/**
 * Listens for events emanating from a display device.
 */
public interface DisplayListener extends AbstractHardwareListener {
    /**
     * Event that announces that the message on the indicated display has
     * changed.
     * 
     * @param display
     *            The device on which the event occurred.
     * @param oldMessage
     *            The previous message on display.
     * @param newMessage
     *            The new message on display.
     */
    void messageChange(Display display, String oldMessage, String newMessage);
}
