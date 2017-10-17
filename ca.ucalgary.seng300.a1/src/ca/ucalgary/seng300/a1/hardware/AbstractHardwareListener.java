package ca.ucalgary.seng300.a1.hardware;

/**
 * This class represents the abstract interface for all hardware listeners. All
 * subclasses should add their own event notification methods, the first
 * parameter of which should always be the instance affected.
 */
public interface AbstractHardwareListener {
    /**
     * Announces that the indicated hardware has been enabled.
     * 
     * @param hardware
     *            The device that has been enabled.
     */
    public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware);

    /**
     * Announces that the indicated hardware has been disabled.
     * 
     * @param hardware
     *            The device that has been enabled.
     */
    public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware);
}
