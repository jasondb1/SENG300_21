package ca.ucalgary.seng300.a1.logic;

import java.util.Observable;

import org.lsmr.vending.Coin;
import org.lsmr.vending.hardware.*;

public class SBListener extends Observable implements SelectionButtonListener {

	private String status = "Listening";
	private String label;
	private int rackID;

	/**
	 * @param rackID
	 *            An integer identifying the associated rack
	 *  @param label The label given to the button
	 */
	public SBListener(int rackID, String label) {
		this.rackID = id;
		this.label = label;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * ca.ucalgary.seng300.a1.hardware.AbstractHardwareListener#enabled(ca.ucalgary.
	 * seng300.a1.hardware.AbstractHardware)
	 */
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		status = "Enabled";
		setChanged();
		notifyObservers();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * ca.ucalgary.seng300.a1.hardware.AbstractHardwareListener#disabled(ca.ucalgary
	 * .seng300.a1.hardware.AbstractHardware)
	 */
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		status = "Disabled";
		setChanged();
		notifyObservers();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * ca.ucalgary.seng300.a1.hardware.SelectionButtonListener#pressed(ca.ucalgary.
	 * seng300.a1.hardware.SelectionButton)
	 */
	public void pressed(SelectionButton button) {
		status = "Pressed";
		setChanged();
		notifyObservers();

	}

	/**
	 * @return the status of the selection button listener
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status to the default value
	 *
	 */
	public void clearStatus() {
		status = "Listening";
	}

	/**
	 * @return the id of the button
	 */
	public int getID() {
		return rackID;
	}

	/**
	 * Returns the id of the button
	 *
	 * @return the id of the button
	 */
	public String getLabel() {
		return label;
	}
}
