package ca.ucalgary.seng300.a1.hardware;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ca.ucalgary.seng300.a1.Coin;

/**
 * Represents a device that stores coins of a particular denomination to
 * dispense them as change.
 * <p>
 * Coin racks can receive coins from other sources. To simplify the simulation,
 * no check is performed on the value of each coin, meaning it is an external
 * responsibility to ensure the correct routing of coins.
 */
public final class CoinRack extends AbstractHardware<CoinRackListener> implements AbstractCoinAcceptor {
    private int maxCapacity;
    private Queue<Coin> queue = new LinkedList<Coin>();
    private CoinChannel sink;

    /**
     * Creates a coin rack with the indicated maximum capacity.
     * 
     * @param capacity
     *            The maximum number of coins that can be stored in the rack.
     *            Must be positive.
     * @throws SimulationException
     *             if capacity is not positive.
     */
    public CoinRack(int capacity) {
	if(capacity <= 0)
	    throw new SimulationException("Capacity must be positive: " + capacity);
	this.maxCapacity = capacity;
    }

    /**
     * Accesses the current number of coins in the rack.
     * 
     * @return The number of coins currently in the rack.
     */
    public int size() {
	return queue.size();
    }

    /**
     * Allows a set of coins to be loaded into the rack directly. Existing coins
     * in the rack are not removed. Causes a "coinsLoaded" event to be
     * announced.
     * 
     * @param coins
     *            A sequence of coins to be added. Each cannot be null.
     * @throws SimulationException
     *             if the number of coins to be loaded exceeds the capacity of
     *             the rack.
     * @throws NullPointerException
     *             If any coin is null.
     */
    public void load(Coin... coins) throws SimulationException {
	if(maxCapacity < queue.size() + coins.length)
	    throw new SimulationException("Capacity of rack is exceeded by load");

	for(Coin coin : coins)
	    queue.add(coin);

	notifyLoad(coins);
    }

    private void notifyLoad(Coin[] coins) {
	for(CoinRackListener listener : listeners)
	    listener.coinsLoaded(this, coins);
    }

    /**
     * Unloads coins from the rack directly. Causes a "coinsUnloaded" event to
     * be announced.
     * 
     * @return A list of the coins unloaded. May be empty. Will never be null.
     */
    public List<Coin> unload() {
	List<Coin> result = new ArrayList<>(queue);
	queue.clear();
	
	notifyUnload(result.toArray(new Coin[result.size()]));
	
	return result;
    }

    private void notifyUnload(Coin[] coins) {
	for(CoinRackListener listener : listeners)
	    listener.coinsUnloaded(this, coins);
    }

    /**
     * Connects an output channel to this coin rack. Any existing output
     * channels are disconnected. Causes no events to be announced.
     * 
     * @param sink
     *            The new output device to act as output. Can be null, which
     *            leaves the channel without an output.
     */
    public void connect(CoinChannel sink) {
	this.sink = sink;
    }

    /**
     * Returns the maximum capacity of this coin rack.
     * 
     * @return The capacity. Will be positive.
     */
    public int getCapacity() {
	return maxCapacity;
    }

    /**
     * Causes the indicated coin to be added into the rack. If successful, a
     * "coinAdded" event is announced to its listeners. If a successful coin
     * addition causes the rack to become full, a "coinsFull" event is announced
     * to its listeners.
     * 
     * @throws DisabledException
     *             if the coin rack is currently disabled.
     * @throws CapacityExceededException
     *             if the coin rack is already full.
     */
    @Override
    public void acceptCoin(Coin coin) throws CapacityExceededException, DisabledException {
	if(isDisabled())
	    throw new DisabledException();

	if(queue.size() >= maxCapacity)
	    throw new CapacityExceededException();

	queue.add(coin);
	notifyCoinAdded(coin);

	if(queue.size() >= maxCapacity)
	    notifyCoinsFull();
    }

    /**
     * Releases a single coin from this coin rack. If successful, a
     * "coinRemoved" event is announced to its listeners. If a successful coin
     * removal causes the rack to become empty, a "coinsEmpty" event is
     * announced to its listeners.
     * 
     * @throws CapacityExceededException
     *             if the output channel is unable to accept another coin.
     * @throws EmptyException
     *             if no coins are present in the rack to release.
     * @throws DisabledException
     *             if the rack is currently disabled.
     */
    public void releaseCoin() throws CapacityExceededException, EmptyException, DisabledException {
	if(isDisabled())
	    throw new DisabledException();

	if(queue.size() == 0)
	    throw new EmptyException();

	Coin coin = queue.remove();

	notifyCoinRemoved(coin);
	sink.deliver(coin);

	if(queue.isEmpty())
	    notifyCoinsEmpty();
    }

    /**
     * Returns whether this coin rack has enough space to accept at least one
     * more coin. Announces no events.
     */
    @Override
    public boolean hasSpace() {
	return queue.size() < maxCapacity;
    }

    private void notifyCoinAdded(Coin coin) {
	for(CoinRackListener listener : listeners)
	    listener.coinAdded(this, coin);
    }

    private void notifyCoinRemoved(Coin coin) {
	for(CoinRackListener listener : listeners)
	    listener.coinRemoved(this, coin);
    }

    private void notifyCoinsFull() {
	for(CoinRackListener listener : listeners)
	    listener.coinsFull(this);
    }

    private void notifyCoinsEmpty() {
	for(CoinRackListener listener : listeners)
	    listener.coinsEmpty(this);
    }
}
