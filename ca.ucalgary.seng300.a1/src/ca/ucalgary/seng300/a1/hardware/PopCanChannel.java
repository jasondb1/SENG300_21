package ca.ucalgary.seng300.a1.hardware;

import ca.ucalgary.seng300.a1.PopCan;

/**
 * Represents the hardware through which a pop can is carried from one device to
 * another. Once the hardware is configured, pop can channels will not be used
 * directly by other applications.
 */
public final class PopCanChannel implements AbstractPopCanAcceptor {
    private AbstractPopCanAcceptor sink;

    /**
     * Creates a new pop can channel whose output will go to the indicated sink.
     * 
     * @param sink
     *            The output of the channel. Can be null, which disconnects any
     *            current output device.
     */
    public PopCanChannel(AbstractPopCanAcceptor sink) {
	this.sink = sink;
    }

    /**
     * This method should only be called from hardware devices.
     * 
     * @throws CapacityExceededException
     *             If the output sink cannot accept the pop can.
     * @throws DisabledException
     *             If the output sink is currently disabled.
     */
    @Override
    public void acceptPopCan(PopCan popCan) throws CapacityExceededException, DisabledException {
	sink.acceptPopCan(popCan);
    }
}
