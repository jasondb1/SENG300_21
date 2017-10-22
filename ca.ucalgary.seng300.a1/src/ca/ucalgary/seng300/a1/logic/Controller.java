package ca.ucalgary.seng300.a1.logic;

import java.util.Observable;
import java.util.Observer;

import org.lsmr.vending.hardware.*;

import ca.ucalgary.seng300.a1.hardware.SimulationException;

/**
 * The controller class that initializes the implemented hardware and handles
 * events
 *
 * @authors Brian Hoang, Jaskaran Sidhu, Jason De Boer
 *
 */
public class Controller implements Observer {

	private int balance = 0;
	private String lastMessage = ""; 
	private String dclLastAction;
	
	// hardware
	private VendingMachine vendingMachine;

	// listeners
	private CSListener csListener = new CSListener();
	private SBListener[] sbListener;
	private PCRListener[] pcrListener;
	private DCListener dcListener = new DCListener();
	
	/**
	 * Constructor that takes in a virtual vending machine and the labels for each
	 * button
	 *
	 * @param vendingMachine The vending machine hardware the controller is being run on
	 * @param buttonLabel Button Labels
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
		
		// register delivery chute listener
		vendingMachine.getDeliveryChute().register(dcListener);
		dcListener.addObserver(this);
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
				switch (activatedButton.getStatus()) {
				case "Pressed":
					int rackID = activatedButton.getID();

					// dispense pop if enough money
					if (balance >= vendingMachine.getPopKindCost(rackID)) {
						try {
							vendingMachine.getPopCanRack(rackID).dispensePopCan();
						} catch (DisabledException | EmptyException | CapacityExceededException e) {
						}

						// adjust balance
						balance -= vendingMachine.getPopKindCost(rackID);
					}
					// do nothing if not enough change
					break;
					
				case "Disabled":
					// Thinking of adding something so it know's that it is disabled TODO
					break;
					
				case "Enabled":
					break;

				default:
					throw new SimulationException("Unknown Button Action");
				}
			}
		}

		// Pop Can Rack Event
		for (PCRListener activatedRack : pcrListener) {
			if (listener == activatedRack) {
				switch (activatedRack.getLastAction()) {

				case "Can Removed":
					lastMessage = "Can Removed";
					break;
					
				case "Can Added":
					lastMessage = "Can Added";
					break;
					
				case "Full Rack":
					lastMessage = "Full Rack";
					break;
				
				case "Empty Rack":
					lastMessage = "Empty Rack";
					break;
					
				case "Enabled":
					lastMessage = "Enabled";
					break;
					
				case "Disabled":
					lastMessage = "Disabled";
					break;
				
				default:
					throw new SimulationException("Unknown Rack Action");
				}
			}
		}
		
		// Delivery Chute Event
		if(listener == dcListener) {
			switch(dcListener.getState()) {
			
			case "Item Delivered":
					dclLastAction = "Item Delivered";
					break;
			
			default:
				throw new SimulationException("Unknown Delivery Chute Event");
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
	
	public String getDCLastAction() {
		return dclLastAction;
	}

}
