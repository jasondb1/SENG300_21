package ca.ucalgary.seng300.a1.hardware;

import java.util.ArrayList;

/**
 * The abstract base class for all hardware devices involved in the vending
 * machine simulator.
 * <p>
 * This class utilizes the Observer design pattern. Subclasses inherit the
 * appropriate register method, but each must define its own notifyXXX methods.
 * The notifyListener method is provided to minimize the work of subclasses.
 * <p>
 * Each hardware device must possess an appropriate listener, which extends
 * AbstractHardwareListener; the type parameter T represents this listener.
 * <p>
 * Any hardware can be disabled, which means it will not permit physical
 * movements. Any method that would cause a physical movement (potentially) will
 * declare that it throws DisabledException.
 * 
 * @param <T>
 *            The class of listeners used for this device.
 */
public abstract class AbstractHardware<T extends AbstractHardwareListener> {
    /**
     * A list of the registered listeners on this device.
     */
    protected ArrayList<T> listeners = new ArrayList<>();

    /**
     * Locates the indicated listener and removes it such that it will no longer
     * be informed of events from this device. If the listener is not currently
     * registered with this device, calls to this method will return false, but
     * otherwise have no effect.
     * 
     * @param listener
     *            The listener to remove.
     * @return true if the listener was found and removed, false otherwise.
     */
    public final boolean deregister(T listener) {
	return listeners.remove(listener);
    }

    /**
     * All listeners registered with this device are removed. If there are none,
     * calls to this method have no effect.
     */
    public final void deregisterAll() {
	listeners.clear();
    }

    /**
     * Registers the indicated listener to receive event notifications from this
     * device.
     * 
     * @param listener
     *            The listener to be added.
     */
    public final void register(T listener) {
	listeners.add(listener);
    }

    private boolean disabled = false;

    /**
     * Disables this hardware from permitting any physical movements.
     */
    public final void disable() {
	disabled = true;
	notifyDisabled();
    }

    private void notifyDisabled() {
	for(T listener : listeners)
	    listener.disabled(this);
    }

    /**
     * Enables this hardware for permitting physical movements.
     */
    public final void enable() {
	disabled = false;
	notifyEnabled();
    }

    private void notifyEnabled() {
	for(T listener : listeners)
	    listener.enabled(this);
    }

    /**
     * Returns whether this hardware is currently disabled from permitting
     * physical movements.
     * 
     * @return true if the device is disabled; false if the device is enabled.
     */
    public final boolean isDisabled() {
	return disabled;
    }
}
