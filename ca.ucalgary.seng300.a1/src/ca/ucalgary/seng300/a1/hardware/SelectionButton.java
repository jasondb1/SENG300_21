package ca.ucalgary.seng300.a1.hardware;

/**
 * Represents a simple push button on the vending machine. It ignores the
 * enabled/disabled state.
 */
public final class SelectionButton extends
        AbstractHardware<SelectionButtonListener> {
    /**
     * Simulates the pressing of the button. Notifies its listeners of a
     * "pressed" event.
     */
    public void press() {
	notifyPressed();
    }

    private void notifyPressed() {
	for(SelectionButtonListener listener : listeners)
	    listener.pressed(this);
    }
}
