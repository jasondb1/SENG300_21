package ca.ucalgary.seng300.a1.hardware;

/**
 * A simple device that can be on or off as an indication to users. By default
 * it is initially off. It ignores the enabled/disabled state.
 */
public final class IndicatorLight extends AbstractHardware<IndicatorLightListener> {
    private boolean on = false;

    /**
     * Turns the light on. Announces an "activated" event to its listeners.
     */
    public void activate() {
	on = true;
	notifyActivated();
    }

    /**
     * Turns the light off. Announces a "deactivated" event to its listeners.
     */
    public void deactivate() {
	on = false;
	notifyDeactivated();
    }

    /**
     * Returns whether the light is currently on (active). Causes no events.
     * 
     * @return true if the light is on; false if the light is off.
     */
    public boolean isActive() {
	return on;
    }

    private void notifyActivated() {
	for(IndicatorLightListener listener : listeners)
	    listener.activated(this);
    }

    private void notifyDeactivated() {
	for(IndicatorLightListener listener : listeners)
	    listener.deactivated(this);
    }
}
