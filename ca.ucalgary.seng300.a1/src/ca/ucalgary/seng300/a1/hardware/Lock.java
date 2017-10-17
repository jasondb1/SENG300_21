package ca.ucalgary.seng300.a1.hardware;

/**
 * A simple lock device to prevent or to permit the interior of the vending
 * machine to be accessed. The lock does not directly act on the hardware
 * otherwise. By default the lock is initially locked. It ignores the
 * enabled/disabled state.
 */
public final class Lock extends AbstractHardware<LockListener> {
    private boolean locked = true;

    /**
     * Causes the lock to become locked. Announces a "locked" event to its
     * listeners.
     */
    public void lock() {
	locked = true;
	notifyLocked();
    }

    /**
     * Causes the lock to become unlocked. Announces an "unlocked" event to its
     * listeners.
     */
    public void unlock() {
	locked = false;
	notifyUnlocked();
    }

    /**
     * Returns whether the lock is currently locked. Causes no events.
     * 
     * @return true if the device is locked; false if the device is unlocked.
     */
    public boolean isLocked() {
	return locked;
    }

    private void notifyLocked() {
	for(LockListener listener : listeners)
	    listener.locked(this);
    }

    private void notifyUnlocked() {
	for(LockListener listener : listeners)
	    listener.unlocked(this);
    }
}
