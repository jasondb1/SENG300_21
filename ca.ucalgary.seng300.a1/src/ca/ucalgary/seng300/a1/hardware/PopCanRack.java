package ca.ucalgary.seng300.a1.hardware;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ca.ucalgary.seng300.a1.PopCan;

/**
 * Represents a storage rack for pop cans within the vending machine. More than
 * one would typically exist within the same vending machine. The pop can rack
 * has finite, positive capacity. A pop can rack can be disabled, which prevents
 * it from dispensing pop cans.
 */
public final class PopCanRack extends AbstractHardware<PopCanRackListener> implements AbstractPopCanAcceptor {
    private int maxCapacity;
    private Queue<PopCan> queue = new LinkedList<PopCan>();
    private PopCanChannel sink;

    /**
     * Creates a new pop can rack with the indicated maximum capacity. The pop
     * can rack initially is empty.
     * 
     * @param capacity
     *            Positive integer indicating the maximum capacity of the rack.
     * @throws SimulationException
     *             if the indicated capacity is not positive.
     */
    public PopCanRack(int capacity) {
	if(capacity <= 0)
	    throw new SimulationException("Capacity cannot be non-positive: " + capacity);

	this.maxCapacity = capacity;
    }

    /**
     * The current number of pop cans stored.
     * 
     * @return The current count. Will be non-negative.
     */
    public int size() {
	return queue.size();
    }

    /**
     * Returns the maximum capacity of this pop can rack. Causes no events.
     * 
     * @return The maximum number of items that this device can store.
     */
    public int getCapacity() {
	return maxCapacity;
    }

    /**
     * Connects the pop can rack to an outlet channel, such as the delivery
     * chute. Causes no events.
     * 
     * @param sink
     *            The channel to be used as the outlet for dispensed pop cans.
     */
    public void connect(PopCanChannel sink) {
	this.sink = sink;
    }

    /**
     * Adds the indicated pop can to this pop can rack if there is sufficient
     * space available. If the pop can is successfully added to this pop can
     * rack, a "popCanAdded" event is announced to its listeners. If, as a result
     * of adding this pop can, this pop can rack has become full, a "popCansFull"
     * event is announced to its listeners.
     * 
     * @param popCan
     *            The pop can to be added.
     * @throws CapacityExceededException
     *             If the pop can rack is already full.
     * @throws DisabledException
     *             If the pop can rack is currently disabled.
     */
    public void acceptPopCan(PopCan popCan) throws CapacityExceededException, DisabledException {
	if(isDisabled())
	    throw new DisabledException();

	if(queue.size() >= maxCapacity)
	    throw new CapacityExceededException();

	queue.add(popCan);

	notifyPopCanAdded(popCan);

	if(queue.size() >= maxCapacity)
	    notifyPopCansFull();
    }

    /**
     * Causes one pop can to be removed from this pop can rack, to be placed in
     * the output channel to which this pop can rack is connected. If a pop can
     * is removed from this pop can rack, a "popCanRemoved" event is announced to
     * its listeners. If the removal of the pop can causes this pop can rack to
     * become empty, a "popCansEmpty" event is announced to its listeners.
     * 
     * @throws DisabledException
     *             If this pop can rack is currently disabled.
     * @throws EmptyException
     *             If no pop cans are currently contained in this pop can rack.
     * @throws CapacityExceededException
     *             If the output channel cannot accept the dispensed pop can.
     */
    public void dispensePopCan() throws DisabledException, EmptyException, CapacityExceededException {
	if(isDisabled())
	    throw new DisabledException();

	if(queue.isEmpty())
	    throw new EmptyException();

	PopCan popCan = queue.remove();
	notifyPopCanRemoved(popCan);

	if(sink == null)
	    throw new SimulationException("The output channel is not connected");

	sink.acceptPopCan(popCan);

	if(queue.isEmpty())
	    notifyPopCansEmpty();
    }

    /**
     * Allows pop cans to be loaded into the pop can rack, to simulate direct,
     * physical loading. Note that any existing pop cans in the rack are not
     * removed. Causes a "popCansLoaded" event to be announced.
     * 
     * @param popCans
     *            One or more pop cans to be loaded into this pop can rack.
     * @throws SimulationException
     *             if the number of cans to be loaded exceeds the capacity of
     *             this pop can rack.
     */
    public void load(PopCan... popCans) throws SimulationException {
	if(maxCapacity < queue.size() + popCans.length)
	    throw new SimulationException("Capacity exceeded by attempt to load");

	for(PopCan popCan : popCans)
	    queue.add(popCan);

	notifyLoad(popCans);
    }

    private void notifyLoad(PopCan[] popCans) {
	for(PopCanRackListener listener : listeners)
	    listener.popCansLoaded(this, popCans);
    }

    /**
     * Unloads pop cans from the rack, to simulate direct, physical unloading.
     * Causes a "popCansUnloaded" event to be announced.
     * 
     * @return A list of the items unloaded.
     */
    public List<PopCan> unload() {
	List<PopCan> result = new ArrayList<>(queue);
	queue.clear();
	notifyUnload(result.toArray(new PopCan[result.size()]));
	return result;
    }

    private void notifyUnload(PopCan[] popCans) {
	for(PopCanRackListener listener : listeners)
	    listener.popCansUnloaded(this, popCans);
    }

    private void notifyPopCanAdded(PopCan popCan) {
	for(PopCanRackListener listener : listeners)
	    listener.popCanAdded(this, popCan);
    }

    private void notifyPopCansFull() {
	for(PopCanRackListener listener : listeners)
	    listener.popCansFull(this);
    }

    private void notifyPopCansEmpty() {
	for(PopCanRackListener listener : listeners)
	    listener.popCansEmpty(this);
    }

    private void notifyPopCanRemoved(PopCan popCan) {
	for(PopCanRackListener listener : listeners)
	    listener.popCanRemoved(this, popCan);
    }
}
