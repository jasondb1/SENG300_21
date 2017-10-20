package ca.ucalgary.seng300.a1.logic;

import java.util.Observable;

import org.lsmr.vending.PopCan;
import org.lsmr.vending.hardware.*;

/**
 * @author
 *
 */
public class PCRListener extends Observable implements PopCanRackListener {

	private String status = "Waiting";
	private String lastAction = "Waiting";
	private String stock = "Empty";
	private String label;
	private int rackID;
	private int popQuantity = 0;

	/**
	 * @param rackID
	 *            An integer identifying the associated rack
	 *  @param label The label given to the button
	 */
	public PCRListener(int rackID, String label) {
		this.rackID = rackID;
		this.label = label;
	}

	@Override
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		status = "Enabled";
	    setChanged();
	    notifyObservers();

	}

	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		status = "Disabled";
	    setChanged();
	    notifyObservers();

	}

	@Override
	public void popCanAdded(PopCanRack popCanRack, PopCan popCan) {
		lastAction = "Can Added";
		popQuantity++;
		stock = Integer.toString(popQuantity);
	    setChanged();
	    notifyObservers();
	}

	@Override
	public void popCanRemoved(PopCanRack popCanRack, PopCan popCan) {
		lastAction = "Can Removed";
		popQuantity--;
		stock = Integer.toString(popQuantity);
	    setChanged();
	    notifyObservers();
	}

	@Override
	public void popCansFull(PopCanRack popCanRack) {
		stock = "Full";
		setChanged();
	    notifyObservers();

	}

	@Override
	public void popCansEmpty(PopCanRack popCanRack) {
		stock = "Empty";
		setChanged();
	    notifyObservers();

	}

	@Override
	public void popCansLoaded(PopCanRack rack, PopCan... popCans) {
		// TODO Auto-generated method stub

	}

	@Override
	public void popCansUnloaded(PopCanRack rack, PopCan... popCans) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the quantity of pops in stock for this rack
	 */
	public String getStock(){
		return stock;
	}
	
	/**
	 * @return the status of the pop can rack
	 */
	public String getStatus(){
		return status;
	}

	/**
	 * @return the status of the pop can rack
	 */
	public String getLastAction(){
		return lastAction;
	}

	/**
	 * @return the id of the button
	 */
	public int getID() {
		return rackID;
	}

	/**
	 * Returns the label of the rack
	 *
	 * @return the label of the rack
	 */
	public String getLabel() {
		return label;
	}
}
