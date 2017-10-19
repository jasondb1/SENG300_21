package ca.ucalgary.seng300.a1.test;

import java.util.Observable;
import java.util.Observer;

import ca.ucalgary.seng300.a1.Coin;
import ca.ucalgary.seng300.a1.hardware.DisabledException;
import ca.ucalgary.seng300.a1.hardware.VendingMachine;

/** The controller class that initializes the implemented hardware and handles events
 * @author
 *
 */
public class Controller implements Observer{

	private VendingMachine vendingMachine;

    //Set machine parameters
	int validCoins[] = {5, 10, 25, 100, 200};
	int popCanCosts[] = {250, 250,350};
	String buttonLabels[] = {"button1", "button2", "button3"};
	private String[] popCanNames = {"pop1","pop2","pop3"};
	int coinRackCapacity = 200;
	int receptacleCapacity = 200;

	//hardware

	//listeners
	private CSListener csListener = new CSListener();
	//TODO: maybe make SBListener into an array;
	private SBListener[] sbListener;


	/**Constructor
	 *
	 */
	public Controller() {

		vendingMachine = new VendingMachine(validCoins, buttonLabels.length, coinRackCapacity,
													popCanNames.length, receptacleCapacity);

		//does vm need to be enabled - does it start in disabled mode?

		//register coinslot listener
		vendingMachine.getCoinSlot().register(csListener);
		csListener.addObserver(this);

		//register button listeners
		sbListener = new SBListener[buttonLabels.length];
		for(int x = 0; x < buttonLabels.length; x++) {
			sbListener[x] = new SBListener();
			vendingMachine.getSelectionButton(x).register(sbListener[x]);
			sbListener[x].addObserver(this);
		}
	}

	 /**Handles the event when a listener detects an event
	 * @param listener the listener object
	 * @param obj the object in this class that
	 */
	public void update(Observable listener, Object obj)
	   {
	      if (listener == csListener)
	      {
	    	  	System.out.println("CSListener event reporting!");
	         //TODO: Deal with the coin Slot
	      }

	      //Will have to see if this works or not.
	      for (Object buttonListener: sbListener)
	      if (listener == buttonListener)
	      {
	    	  System.out.println("SBListener event");
	         //TODO: Deal with selection buttons
	      }

	   }

	/** returns the vending machine object
	 * @return VendingMachine Object
	 */
	public VendingMachine getVendingMachine() {
		return vendingMachine;
	}

	//TODO: Methods related to machine function



	/** For quick running/tests of code
	 *
	 * @param args
	 * @throws DisabledException
	 */
	public static void main(String[] args) throws DisabledException {
		Controller controller = new Controller();
		controller.getVendingMachine().getCoinSlot().addCoin(new Coin(100));
	}



}
