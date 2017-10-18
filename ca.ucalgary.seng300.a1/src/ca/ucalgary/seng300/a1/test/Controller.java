package ca.ucalgary.seng300.a1.test;

import java.util.Observable;
import java.util.Observer;

import ca.ucalgary.seng300.a1.Coin;
import ca.ucalgary.seng300.a1.hardware.CoinChannel;
import ca.ucalgary.seng300.a1.hardware.CoinRack;
import ca.ucalgary.seng300.a1.hardware.CoinReceptacle;
import ca.ucalgary.seng300.a1.hardware.CoinSlot;
import ca.ucalgary.seng300.a1.hardware.PopCanRack;
import ca.ucalgary.seng300.a1.hardware.SelectionButton;
import ca.ucalgary.seng300.a1.hardware.SelectionButtonListener;

/** The controller class that initializes the implemented hardware and handles events
 * @author
 *
 */
public class Controller implements Observer{


    private PopCanRack[] popCanRacks;
    private int[] popCanCosts;
    private String[] popCanNames;
    private SelectionButton[] button;
    private CoinRack[] coinRacks;


    //Set machine parameters
	int validCoins[] = {5, 10, 25, 100, 200};
	int prices[] = {250, 250,350};

	//hardware
	private CoinSlot coinSlotInstance = new CoinSlot(validCoins);

	private CoinReceptacle invalidCapacity = new CoinReceptacle(200);
	private CoinReceptacle validCapacity = new CoinReceptacle(200);

	private CoinChannel validChannel = new CoinChannel(validCapacity);
	private CoinChannel invalidChannel = new CoinChannel(invalidCapacity);

	//listeners
	private CSListener csListener = new CSListener();
	private SBListener[] sbListener;


	/**Constructor
	 *
	 */
	//TODO: may need to put parameters in constructor such as validCoins and prices, pop_types, rack size, etc
	public Controller() {

		coinSlotInstance.connect(validChannel, invalidChannel);
		coinSlotInstance.register(csListener);
		csListener.addObserver(this);

		//try with 3 buttons - Maybe move to constructor
		for(int x = 0; x<3; x++) {
			sbListener[x] = new SBListener();
			button[x] = new SelectionButton();
			button[x].register(sbListener[x]);
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
	         //TODO: Deal with the coin Slot
	      }

	      //i'm not sure if this will work or not
	      for (Object buttonListener: sbListener)
	      if (listener == buttonListener)
	      {
	         //TODO: Deal with selection buttons
	      }

	   }
	//TODO: Methods related to machine function



}
