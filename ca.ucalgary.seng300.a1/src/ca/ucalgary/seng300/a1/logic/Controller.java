package ca.ucalgary.seng300.a1.logic;

import java.util.Observable;
import java.util.Observer;

import org.lsmr.vending.Coin;
import org.lsmr.vending.hardware.*;

/**
 * The controller class that initializes the implemented hardware and handles
 * events
 *
 * @author
 *
 */
public class Controller implements Observer {

	private int balance = 0;

	// hardware
	private VendingMachine vendingMachine;

	// listeners
	private CSListener csListener = new CSListener();
	// TODO: maybe make SBListener into an arraylist???;
	private SBListener[] sbListener;

	/**
	 * Constructor that takes in a virtual vending machine and the labels for each
	 * button
	 *
	 * @param vendingMachine
	 * @param buttonLabel
	 */
	public Controller(VendingMachine vendingMachine, String[] buttonLabel) {

		this.vendingMachine = vendingMachine;

		// register coinslot listener
		vendingMachine.getCoinSlot().register(csListener);
		csListener.addObserver(this);

		// register button listeners
		sbListener = new SBListener[vendingMachine.getNumberOfSelectionButtons()];
		for (int x = 0; x < vendingMachine.getNumberOfSelectionButtons(); x++) {
			// register and bind each button to the observer with the name of the popkind
			sbListener[x] = new SBListener(x, vendingMachine.getPopKindName(x));
			vendingMachine.getSelectionButton(x).register(sbListener[x]);
			sbListener[x].addObserver(this);
		}
	}

	/**
	 * Handles the event when a listener detects an event
	 *
	 * @param listener
	 *            the listener object
	 * @param obj
	 *            the object in this class that
	 */
	public void update(Observable listener, Object obj) {
		// Coin Slot Event
		if (listener == csListener) {
			// TODO: Deal with the coin Slot
			switch (csListener.getStatus()) {
			case "Enabled":
				// TODO: Something
				break;
			case "Disabled":
				// TODO: Something
				break;
			case "Accepted":
				balance += csListener.getLastCoinValue();
				break;
			case "Rejected":
				// TODO: Something
				break;
			default:
				throw new SimulationException("Unknown CoinSlot Event");

			}
			// System.out.println("CSListener event reporting!"); //Just to test if it is
			// functioning
		}

		// Selection Button Event
		for (SBListener activatedButton : sbListener)
			if (listener == activatedButton) {
				// TODO: Deal with the selection button
				switch (activatedButton.getStatus()) {
				case "Pressed":
					int rackID = activatedButton.getID();

					// dispense pop if enough money
					if (balance >= vendingMachine.getPopKindCost(rackID)) {
						try {
							vendingMachine.getPopCanRack(rackID).dispensePopCan();
						} catch (DisabledException | EmptyException | CapacityExceededException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						//adjust balance
						balance -= vendingMachine.getPopKindCost(rackID);
					}
					//do nothing if not enough change
					break;

				default:
					throw new SimulationException("Unknown Button Action");
				}
				// System.out.println("SBListener event"); //Just to test if it is functioning
			}

	}

	/**
	 * returns the vending machine object
	 *
	 * @return VendingMachine Object
	 */
	public VendingMachine getVendingMachine() {
		return vendingMachine;
	}

	// TODO: Methods related to machine function

	/**
	 * For quick running/tests of code
	 *
	 * @param args
	 * @throws DisabledException
	 */
	// public static void main(String[] args) throws DisabledException {
	// Controller controller = new Controller();
	// controller.getVendingMachine().getCoinSlot().addCoin(new Coin(100));
	// }

}
