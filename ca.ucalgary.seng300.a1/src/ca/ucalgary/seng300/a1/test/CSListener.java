package ca.ucalgary.seng300.a1.test;

import java.util.Observable;

import ca.ucalgary.seng300.a1.Coin;
import ca.ucalgary.seng300.a1.hardware.AbstractHardware;
import ca.ucalgary.seng300.a1.hardware.AbstractHardwareListener;
import ca.ucalgary.seng300.a1.hardware.CoinSlot;
import ca.ucalgary.seng300.a1.hardware.CoinSlotListener;

/**A Coin Slot Listener for the vending machine
 * @author
 *
 */
public class CSListener extends Observable implements CoinSlotListener {

	private String status = "Listening";

	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		status = "Enabled";
	    setChanged();
	    notifyObservers();
	}

	/* (non-Javadoc)
	 * @see ca.ucalgary.seng300.a1.hardware.AbstractHardwareListener#disabled(ca.ucalgary.seng300.a1.hardware.AbstractHardware)
	 */
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		status = "Disabled";
	      setChanged();
	      notifyObservers();
	}

	/* (non-Javadoc)
	 * @see ca.ucalgary.seng300.a1.hardware.CoinSlotListener#validCoinInserted(ca.ucalgary.seng300.a1.hardware.CoinSlot, ca.ucalgary.seng300.a1.Coin)
	 */
	public void validCoinInserted(CoinSlot slot, Coin coin) {
		status = "Accepted";
	    setChanged();
	    notifyObservers();
	}

	/* (non-Javadoc)
	 * @see ca.ucalgary.seng300.a1.hardware.CoinSlotListener#coinRejected(ca.ucalgary.seng300.a1.hardware.CoinSlot, ca.ucalgary.seng300.a1.Coin)
	 */
	public void coinRejected(CoinSlot slot, Coin coin) {
		status = "Rejected";
	    setChanged();
	    notifyObservers();
	}

	/**
	 * @return the status of the coinslot listener
	 */
	public String getStatus(){
		return status;
	}

	/**Sets the status to the default value
	 *
	 */
	public void clearStatus() {
		status = "Listening";
	}

}
