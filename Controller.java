package ca.ucalgary.seng300.a1.hardware;

import ca.ucalgary.seng300.a1.Coin;

public class Controller {
	
	
    private PopCanRack[] popCanRacks;
    private int[] popCanCosts;
    private String[] popCanNames;
    private SelectionButton[] buttons;
    private CoinRack[] coinRacks;
    
    
	int validCoins[] = {5, 10, 25, 100, 200};
	
	
	private CoinSlot coinSlotInstance = new CoinSlot(validCoins); 
	
	private CoinReceptacle invalidCapacity = new CoinReceptacle(200);
	private CoinReceptacle validCapacity = new CoinReceptacle(200);
	
	private CoinChannel validChannel = new CoinChannel(validCapacity);
	private CoinChannel invalidChannel = new CoinChannel(invalidCapacity);
	
	
	public void setup() {
		coinSlotInstance.connect(validChannel, invalidChannel);
	}
	
	
}
