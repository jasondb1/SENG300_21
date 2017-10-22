package ca.ucalgary.seng300.a1.logic;

import java.util.Observable;

import org.lsmr.vending.Coin;
import org.lsmr.vending.hardware.*;

/**A Coin Slot Listener for the vending machine
 * @authors Brian Hoang, Jaskaran Sidhu, Jason De Boer
 *
 */
public class CSListener extends Observable implements CoinSlotListener {

	private String state = "";
	private String status = "Listening";
	private int lastCoinValue = 0;

	/* (non-Javadoc)
	 * @see org.lsmr.vending.hardware.AbstractHardwareListener#enabled(org.lsmr.vending.hardware.AbstractHardware)
	 */
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
		lastCoinValue = coin.getValue();
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
	 * @return the status of the coinslot
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

	/**
	 * @return the status of the last value of coin inserted
	 */
	public int getLastCoinValue(){
		return lastCoinValue;
	}

}
