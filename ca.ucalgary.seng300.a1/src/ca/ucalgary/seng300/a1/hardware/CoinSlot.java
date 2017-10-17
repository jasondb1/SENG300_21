package ca.ucalgary.seng300.a1.hardware;

import ca.ucalgary.seng300.a1.Coin;

/**
 * Represents a simple coin slot device that has two output channels, one to a
 * storage container and one to a coin return device. A coin slot detects the
 * presence of invalid coins and returns them. A coin slot can be disabled to
 * prevent the insertion of coins (temporarily).
 */
public final class CoinSlot extends AbstractHardware<CoinSlotListener> {
    private int[] validValues;
    private CoinChannel valid, invalid;

    /**
     * Creates a coin slot that recognizes coins of the specified values.
     * 
     * @param validValues
     *            An array of the valid coin values to accept.
     */
    public CoinSlot(int[] validValues) {
	this.validValues = validValues;
    }

    /**
     * Connects output channels to the coin slot. Causes no events.
     * 
     * @param valid
     *            Where valid coins to be stored should be passed.
     * @param invalid
     *            Where invalid coins (or coins that exceed capacity) should be
     *            passed.
     */
    public void connect(CoinChannel valid, CoinChannel invalid) {
	this.valid = valid;
	this.invalid = invalid;
    }

    private boolean isValid(Coin coin) {
	for(int vv : validValues) {
	    if(vv == coin.getValue())
		return true;
	}

	return false;
    }

    /**
     * Tells the coin slot that the indicated coin is being inserted. If the
     * coin is valid and there is space in the machine to store it, a
     * "validCoinInserted" event is announced to its listeners and the coin is
     * delivered to the storage device. If there is no space in the machine to
     * store it or the coin is invalid, a "coinRejected" event is announced to
     * its listeners and the coin is returned.
     * 
     * @param coin
     *            The coin to be added. Cannot be null.
     * @throws DisabledException
     *             if the coin slot is currently disabled.
     * @throws NullPointerException
     *             If the coin is null.
     */
    public void addCoin(Coin coin) throws DisabledException {
	if(isDisabled())
	    throw new DisabledException();

	if(isValid(coin) && valid.hasSpace()) {
	    try {
		valid.deliver(coin);
	    }
	    catch(CapacityExceededException e) {
		// Should never happen
		throw new SimulationException(e);
	    }
	    finally {
		notifyValidCoinInserted(coin);
	    }
	}
	else if(invalid.hasSpace()) {
	    try {
		invalid.deliver(coin);
	    }
	    catch(CapacityExceededException e) {
		// Should never happen
		throw new SimulationException(e);
	    }
	    finally {
		notifyCoinRejected(coin);
	    }
	}
	else
	    throw new SimulationException("Unable to route coin: All channels full");
    }

    private void notifyValidCoinInserted(Coin coin) {
	for(CoinSlotListener listener : listeners)
	    listener.validCoinInserted(this, coin);
    }

    private void notifyCoinRejected(Coin coin) {
	for(CoinSlotListener listener : listeners)
	    listener.coinRejected(this, coin);
    }
}
