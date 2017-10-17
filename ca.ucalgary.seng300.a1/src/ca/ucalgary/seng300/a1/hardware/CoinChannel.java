package ca.ucalgary.seng300.a1.hardware;

import ca.ucalgary.seng300.a1.Coin;

/**
 * Represents a simple device (like, say, a tube) that allows coins to move
 * between other devices.
 */
public final class CoinChannel {
    private AbstractCoinAcceptor sink;

    /**
     * Constructs a new coin channel whose output is connected to the indicated
     * sink.
     * 
     * @param sink
     *            The device at the output end of the channel.
     */
    public CoinChannel(AbstractCoinAcceptor sink) {
	this.sink = sink;
    }

    /**
     * Moves the indicated coin to the sink. This method should be called by the
     * source device, and not by an external application.
     * 
     * @param coin
     *            The coin to transport via the channel.
     * @throws CapacityExceededException
     *             if the sink has no space for the coin.
     * @throws DisabledException
     *             if the sink is currently disabled.
     */
    public void deliver(Coin coin) throws CapacityExceededException, DisabledException {
	getSink().acceptCoin(coin);
    }

    /**
     * Returns whether the sink has space for at least one more coin.
     * 
     * @return true if the channel can accept a coin; false otherwise.
     */
    public boolean hasSpace() {
	return getSink().hasSpace();
    }

    /**
     * Returns the sink to which this channel is connected.
     * 
     * @return The device at the output end of the device.
     */
    public AbstractCoinAcceptor getSink() {
	return sink;
    }
}
