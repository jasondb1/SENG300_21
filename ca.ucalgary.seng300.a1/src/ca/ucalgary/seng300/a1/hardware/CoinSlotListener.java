package ca.ucalgary.seng300.a1.hardware;

import ca.ucalgary.seng300.a1.Coin;

/**
 * Listens for events emanating from a coin slot.
 */
public interface CoinSlotListener extends AbstractHardwareListener {
    /**
     * An event announcing that the indicated, valid coin has been inserted and
     * successfully delivered to the storage device connected to the indicated
     * coin slot.
     * 
     * @param slot
     *            The device on which the event occurred.
     * @param coin
     *            The coin inserted.
     */
    void validCoinInserted(CoinSlot slot, Coin coin);

    /**
     * An event announcing that the indicated coin has been rejected and, hence,
     * returned.
     * 
     * @param slot
     *            The device on which the event occurred.
     * @param coin
     *            The coin rejected.
     */
    void coinRejected(CoinSlot slot, Coin coin);
}
