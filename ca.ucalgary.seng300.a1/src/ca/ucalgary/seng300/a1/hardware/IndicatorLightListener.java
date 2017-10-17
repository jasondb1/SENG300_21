package ca.ucalgary.seng300.a1.hardware;

/**
 * Listens for events emanating from an indicator light.
 */
public interface IndicatorLightListener extends AbstractHardwareListener {
    /**
     * An event that is announced when the indicated light has been activated
     * (turned on).
     * 
     * @param light
     *            The device on which the event occurred.
     */
    void activated(IndicatorLight light);

    /**
     * An event that is announced when the indicated light has been deactivated
     * (turned off).
     * 
     * @param light
     *            The device on which the event occurred.
     */
    void deactivated(IndicatorLight light);
}
