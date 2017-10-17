package ca.ucalgary.seng300.a1.hardware;

/**
 * Listens for events emanating from a selection button.
 */
public interface SelectionButtonListener extends AbstractHardwareListener {
    /**
     * An event that is announced to the listener when the indicated button has
     * been pressed.
     * 
     * @param button
     *            The device on which the event occurred.
     */
    void pressed(SelectionButton button);
}
