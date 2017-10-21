package ca.ucalgary.seng300.a1.logic;

import java.util.Observable;

import org.lsmr.vending.PopCan;
import org.lsmr.vending.hardware.*;

/**
 * @authorBrian Hoang, Jaskaran Sidhu, Jason De Boer
 *
 */
public class PCRListener extends Observable implements PopCanRackListener {
	
	private String lastAction = "Waiting";
	private String label;
	private int rackID;

	/**
	 * @param rackID
	 *            An integer identifying the associated rack
	 *  @param label The label given to the button
	 */
	public PCRListener(int rackID, String label) {
		this.rackID = rackID;
		this.label = label;
	}
  
	
	/**
	 * An event is announced when the pop can rack is enabled. 
	 */
	@Override
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		lastAction = "Enabled";
	    setChanged();
	    notifyObservers();

	}

	
	/**
	 * An event is announced when the pop can rack is disabled. 
	 */
	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		lastAction = "Disabled";
	    setChanged();
	    notifyObservers();

	}

	
	/**
	 * @see ca.ucalgary.seng300.a1.hardware.PopCanRackListener#popCanAdded
	 */
	@Override
	public void popCanAdded(PopCanRack popCanRack, PopCan popCan) {
		lastAction = "Can Added";
		setChanged();
	    notifyObservers();
	}
	
	
	/**
	 * @see ca.ucalgary.seng300.a1.hardware.PopCanRackListener#popCanRemoved
	 */
	@Override
	public void popCanRemoved(PopCanRack popCanRack, PopCan popCan) {
		lastAction = "Can Removed";
	    setChanged();
	    notifyObservers();
	}

	
	/**
	 * @see ca.ucalgary.seng300.a1.hardware.PopCanRackListener#popCanFull
	 */
	@Override
	public void popCansFull(PopCanRack popCanRack) {
		lastAction = "Full Rack";
		setChanged();
	    notifyObservers();

	}

	/**
	 * @see ca.ucalgary.seng300.a1.hardware.PopCanRackListener#popCanEmpty
	 */
	@Override
	public void popCansEmpty(PopCanRack popCanRack) {
		lastAction = "Empty Rack";
		setChanged();
	    notifyObservers();

	}

	/**
	 * @see ca.ucalgary.seng300.a1.hardware.PopCanRackListener#popCanLoaded
	 */
	@Override
	public void popCansLoaded(PopCanRack rack, PopCan... popCans) {

	}

	
	/**
	 * @see ca.ucalgary.seng300.a1.hardware.PopCanRackListener#popCanUnloaded
	 */
	@Override
	public void popCansUnloaded(PopCanRack rack, PopCan... popCans) {

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
