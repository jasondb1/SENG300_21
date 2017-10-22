package ca.ucalgary.seng300.a1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.ucalgary.seng300.a1.Coin;
import ca.ucalgary.seng300.a1.PopCan;
import ca.ucalgary.seng300.a1.hardware.DisabledException;
import ca.ucalgary.seng300.a1.hardware.VendingMachine;
import ca.ucalgary.seng300.a1.logic.Controller;



/**
 * @author
 *
 */
public class TestController {

	Coin validCoin = new Coin(100);
	Coin invalidCoin = new Coin (500);

	//Vending Machine Parameters
	private int[] validCoins = {5, 10, 25, 100, 200};
	private Integer[] costs = {200, 250, 300};
	private ArrayList<Integer> popCanCosts;
	private ArrayList<String> popCanNames;
	private String[] names = {"pop1","pop2","pop3"};
	private int coinRackCapacity = 200;
	private int receptacleCapacity = 200;
	private int popCanRackCapacity = 20;

	//Hardware
	private VendingMachine vendingMachine;

	private Controller controller;

	/** Setup before every test
	 * @throws DisabledException
	 *
	 */
	@Before
	public void setupVendingmachine() throws DisabledException {

		popCanCosts = new ArrayList<Integer>(Arrays.asList(costs));
		popCanNames = new ArrayList<String>(Arrays.asList(names));


		//initialize vending machine
		vendingMachine = new VendingMachine(validCoins, popCanNames.size(), coinRackCapacity,
				popCanRackCapacity, receptacleCapacity);

		vendingMachine.configure(popCanNames, popCanCosts);

		controller = new Controller(vendingMachine, names);

		//load all of the the pop racks
		for (int i = 0; i < popCanNames.size(); i++) {
			for(int j = 0; j < popCanRackCapacity; j++) {
				loadPopCan(i, names[i]);
			}
		}

	}

	@After
	public void cleanup() {

	}

	//////////////////////////////////////////////////////////////////
	// Testing coin entries
	//////////////////////////////////////////////////////////////////
	/**Tests coin balances for entered coins
	 * @throws DisabledException
	 */
	@Test
	public void testValidCoins() throws DisabledException {
		assertEquals(0, controller.getBalance()); //starting balance should be 0

		int amountEntered = 0;

		//Test all valid coin values
		for(int coinValue: validCoins) {
			addCoin(coinValue);
			amountEntered += coinValue;
			assertEquals(amountEntered, controller.getBalance()); //check that vending machine reports balances correctly
		}
	}

	/**
	 *
	 */
	@Test
	public void testInvalidCoin() {
		fail("Not yet implemented");
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	//////////////////////////////////////////////////////////////////
	// Testing hardware interactions
	//////////////////////////////////////////////////////////////////

	//TODO: tests: dispenses with correct amounts entered on all racks, does not dispense with not enough entered
	// probably need a popCanRack listener or stub

	//TODO: expand to all racks
	/**
	 * @throws DisabledException
	 *
	 */
	@Test
	public void testDispensePop() throws DisabledException {
		addCoin(200);
		addCoin(100);
		pushButton(0);
		assertEquals(100, controller.getBalance()); //check that vending machine reports balances correctly
		assertEquals("Can Removed" ,controller.getLastMessage()); //check that pop can is removed from rack		
		assertEquals("Item Delivered", controller.getDCLastAction()); // check that pop can arrives at delivery chute
	}


	//method for automatically entering coins
	public void addCoin(int value) throws DisabledException {
		vendingMachine.getCoinSlot().addCoin(new Coin(value));
	}

	//method for automatically entering coins
	public void pushButton(int index) throws DisabledException {
		vendingMachine.getSelectionButton(index).press();
	}

	//method for automatically entering coins
	public void loadPopCan(int popRack, String popType) throws DisabledException {
		vendingMachine.getPopCanRack(popRack).load(new PopCan(popType));
	}

}
