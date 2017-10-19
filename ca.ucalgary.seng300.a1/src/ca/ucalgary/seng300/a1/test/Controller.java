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
	//TODO: maybe make SBListener into an arraylist;
	private SBListener[] sbListener;


	/**Constructor
	 *
	 */
	public Controller() {

		vendingMachine = new VendingMachine(validCoins, buttonLabels.length, coinRackCapacity,
													popCanNames.length, receptacleCapacity);

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
	      //Coin Slot Event
			if (listener == csListener)
	      {
		         //TODO: Deal with the coin Slot
	    	  switch (csListener.getStatus()) {
	    	  case "Enabled":
	    		  //TODO: Something
	    		  break;
	    	  case "Disabled":
	    		  //TODO: Something
	    		  break;
	    	  case "Accepted":
	    		  //TODO: Something
	    		  break;
	    	  case "Rejected":
	    		  //TODO: Something
	    		  break;
	    	  default:
	    		  break;

	    	  }
	    	  System.out.println("CSListener event reporting!");
	      }

	      //Selection Button Event
	      for (Object buttonListener: sbListener)
	      if (listener == buttonListener)
	      {
		      //TODO: Deal with the selection button
	    	  switch (csListener.getStatus()) {
	    	  case "Enabled":
	    		  //TODO: Something
	    		  break;
	    	  case "Disabled":
	    		  //TODO: Something
	    		  break;
	    	  case "Pressed":
	    		  //TODO: Something
	    		  break;
	    	  default:
	    		  break;

	    	  }
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
