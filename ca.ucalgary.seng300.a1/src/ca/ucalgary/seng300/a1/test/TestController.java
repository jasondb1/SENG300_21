/**
 *
 */
package ca.ucalgary.seng300.a1.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.vending.Coin;
import org.lsmr.vending.hardware.VendingMachine;

import ca.ucalgary.seng300.a1.logic.Controller;

/**
 * @author
 *
 */
public class TestController {

	Coin validCoin = new Coin(100);
	Coin invalidCoin = new Coin (500);

	//Vending Machine Parameters
	private int validCoins[] = {5, 10, 25, 100, 200};
	private int popCanCosts[] = {250, 250, 350};
	private String[] buttonLabel = {"button1", "button2", "button3"};
	private String[] popCanNames = {"pop1","pop2","pop3"};
	private int coinRackCapacity = 200;
	private int receptacleCapacity = 200;

	//Hardware
	private VendingMachine vendingMachine;

	private Controller controller;

	/**
	 *
	 */
	@Before
	public void setupVendingmachine() {
		vendingMachine = new VendingMachine(validCoins, buttonLabel.length, coinRackCapacity,
				popCanNames.length, receptacleCapacity);

		controller = new Controller(vendingMachine, buttonLabel);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
