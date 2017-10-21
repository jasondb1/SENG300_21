package ca.ucalgary.seng300.a1.logic;

import java.util.Observable;
import java.util.Observer;

import org.lsmr.vending.hardware.*;

/**
 * The controller class that initializes the implemented hardware and handles
 * events
 *
 * @authors Brian Hoang, Jaskaran Sidhu, Jason De Boer
 *
 */
public class Controller implements Observer {

	private int balance = 0;
	private String lastMessage = ""; // Maybe make this into a log later

	// hardware
	private VendingMachine vendingMachine;

	// listeners
	private CSListener csListener = new CSListener();
	// TODO: maybe make SBListener into an arraylist???;
	private SBListener[] sbListener;
	private PCRListener[] pcrListener;

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
		for (int i = 0; i < vendingMachine.getNumberOfSelectionButtons(); i++) {
			// register and bind each button to the observer with the name of the popkind
			sbListener[i] = new SBListener(i, vendingMachine.getPopKindName(i));
			vendingMachine.getSelectionButton(i).register(sbListener[i]);
			sbListener[i].addObserver(this);
		}

		// register pop can rack listeners
		pcrListener = new PCRListener[vendingMachine.getNumberOfPopCanRacks()];
		for (int i = 0; i < vendingMachine.getNumberOfPopCanRacks(); i++) {
			// register and bind each button to the observer with the name of the popkind
			pcrListener[i] = new PCRListener(i, vendingMachine.getPopKindName(i));
			vendingMachine.getPopCanRack(i).register(pcrListener[i]);
			(pcrListener[i]).addObserver(this);
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
			lastMessage = csListener.getStatus();
			switch (lastMessage) {
			case "Enabled":
				break;
			case "Disabled":
				break;
			case "Accepted":
				balance += csListener.getLastCoinValue();
				break;
			case "Rejected":
				break;
			default:
				throw new SimulationException("Unknown Coin Slot Event");
			}
		}

		// Selection Button Event
		for (SBListener activatedButton : sbListener) {
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

						// adjust balance
						balance -= vendingMachine.getPopKindCost(rackID);
					}
					// do nothing if not enough change
					break;

				default:
					throw new SimulationException("Unknown Button Action");
				}
				// System.out.println("SBListener event"); //Just to test if it is functioning
			}
		}

		// Pop Can Rack Event
		for (PCRListener activatedRack : pcrListener) {
			if (listener == activatedRack) {
				// TODO: Deal with the selection button
				switch (activatedRack.getLastAction()) {

				case "Can Removed":
					lastMessage = "Can Removed";
					break;

				default:
					throw new SimulationException("Unknown Rack Action");
				}
				// System.out.println("PCRListener event"); //Just to test if it is functioning
			}
		}

	}

	/**
	 * Returns the balance of credit entered
	 *
	 * @return balance
	 */
	public int getBalance() {
		return balance;
	}

	public String getLastMessage() {
		return lastMessage;
	}
	// TODO: Methods related to machine function

}
