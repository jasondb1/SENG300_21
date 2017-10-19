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



	private boolean insertedValidCoin = false;
	private boolean coinRejected = false;
	private boolean enabled;
	private boolean disabled;
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		enabled = true;
	    setChanged();
	    notifyObservers();
	}

	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		disabled = true;
	      setChanged();
	      notifyObservers();
	}

	public void validCoinInserted(CoinSlot slot, Coin coin) {
		insertedValidCoin = true;
	    setChanged();
	    notifyObservers();
	}

	public void coinRejected(CoinSlot slot, Coin coin) {
		coinRejected = true;
	    setChanged();
	    notifyObservers();
	}

	//TODO: Getters and setters if required
}
