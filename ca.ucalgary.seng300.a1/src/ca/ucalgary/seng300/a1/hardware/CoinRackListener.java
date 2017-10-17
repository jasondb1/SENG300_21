package ca.ucalgary.seng300.a1.hardware;

import ca.ucalgary.seng300.a1.Coin;

/**
 * Listens for events emanating from a coin rack.
 */
public interface CoinRackListener extends AbstractHardwareListener {
    /**
     * Announces that the indicated coin rack is full of coins.
     * 
     * @param rack
     *            The rack where the event occurred.
     */
    void coinsFull(CoinRack rack);

    /**
     * Announces that the indicated coin rack is empty of coins.
     * 
     * @param rack
     *            The rack where the event occurred.
     */
    void coinsEmpty(CoinRack rack);

    /**
     * Announces that the indicated coin has been added to the indicated coin
     * rack.
     * 
     * @param rack
     *            The rack where the event occurred.
     * @param coin
     *            The coin that was added.
     */
    void coinAdded(CoinRack rack, Coin coin);

    /**
     * Announces that the indicated coin has been added to the indicated coin
     * rack.
     * 
     * @param rack
     *            The rack where the event occurred.
     * @param coin
     *            The coin that was removed.
     */
    void coinRemoved(CoinRack rack, Coin coin);

    /**
     * Announces that the indicated sequence of coins has been added to the
     * indicated coin rack. Used to simulate direct, physical loading of the
     * rack.
     * 
     * @param rack
     *            The rack where the event occurred.
     * @param coins
     *            The coins that were loaded.
     */
    void coinsLoaded(CoinRack rack, Coin... coins);

    /**
     * Announces that the indicated sequence of coins has been removed to the
     * indicated coin rack. Used to simulate direct, physical unloading of the
     * rack.
     * 
     * @param rack
     *            The rack where the event occurred.
     * @param coins
     *            The coins that were unloaded.
     */
    void coinsUnloaded(CoinRack rack, Coin... coins);
}
