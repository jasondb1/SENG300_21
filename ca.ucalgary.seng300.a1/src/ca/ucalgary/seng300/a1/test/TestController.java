package ca.ucalgary.seng300.a1.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.ucalgary.seng300.a1.logic.Controller;
import org.lsmr.vending.Coin;
import org.lsmr.vending.PopCan;
import org.lsmr.vending.hardware.CapacityExceededException;
import org.lsmr.vending.hardware.DisabledException;
import org.lsmr.vending.hardware.VendingMachine;



/**
 * @authors Brian Hoang, Jaskaran Sidhu, Jason De Boer
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
	private int popCanRackCapacity = 10;

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

	/**Any cleanup code required
	 *
	 */
	@After
	public void cleanup() {

	}

	//////////////////////////////////////////////////////////////////
	// Testing coin slot
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

	/**Tests invalid coin entries
	 * @throws DisabledException
	 */
	@Test
	public void testInvalidCoins() throws DisabledException {
		assertEquals(0, controller.getBalance()); //starting balance should be 0

		for( int i = 1 ; i < 10; i++) {
			addCoin(15);
			assertEquals(0, controller.getBalance()); //check that vending machine reports balances correctly
		}
	}

	/**Tests coin capacities exceeded
	 * @throws DisabledException
	 */
	@Test
	public void testValidCoinCapacityExeeded() throws DisabledException {
		assertEquals(0, controller.getBalance()); //starting balance should be 0

		for( int i = 1 ; i < 10; i++) {
			addCoin(15);
			assertEquals(0, controller.getBalance()); //check that vending machine reports balances correctly
		}
	}

	// test to see that exception is thrown if adding a coin while disabled
	@Test(expected = DisabledException.class)
	public void testSlotDisabledAddCoin() throws DisabledException {
		vendingMachine.getCoinSlot().disable();
		addCoin(100);
	}

	/**Tests Coin Slot Enabled
	 * @throws DisabledException
	 */
	@Test
	public void testCoinSlotEnabled() throws DisabledException {
		vendingMachine.getCoinSlot().enable();
		assertEquals("Enabled", controller.getLastMessage());
	}

	/**Tests Coin Slot Enabled
	 * @throws DisabledException
	 */
	@Test
	public void testCoinSlotDisabled() throws DisabledException {
		vendingMachine.getCoinSlot().enable();
		assertEquals("Enabled", controller.getLastMessage()); //ensure the coin slot is enabled
		vendingMachine.getCoinSlot().disable();
		assertEquals("Disabled", controller.getLastMessage()); //ensure the coin slot is enabled
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
		assertEquals("Can Removed" ,controller.getLastMessage());//need to check if can was removed
													//TODO: need to check with pop can channel sink if pop was accepted

	}

	
	
	@Test
	public void testIsFull() throws DisabledException, CapacityExceededException {
		addCoin(200);
		addCoin(100);
		pushButton(0);
		vendingMachine.getPopCanRack(0).acceptPopCan(new PopCan(names[0]));
		assertEquals("Full Rack", controller.getLastMessage());
	}

	
	@Test
	public void testIsEmpty() throws DisabledException, CapacityExceededException {
		vendingMachine.getPopCanRack(0).unload();
		addCoin(200);
		vendingMachine.getPopCanRack(0).acceptPopCan(new PopCan(names[0]));
		pushButton(0);	
		assertEquals("Empty Rack", controller.getLastMessage());
	}
	
	@Test (expected = DisabledException.class)
	public void testDisabled() throws DisabledException, CapacityExceededException {
		addCoin(200);
		pushButton(0);
		vendingMachine.getPopCanRack(0).disable();
		assertEquals("Disabled", controller.getLastMessage());
		vendingMachine.getPopCanRack(0).acceptPopCan(new PopCan(names[0]));
		
	}
	
	@Test 
	public void testEnabled() throws DisabledException, CapacityExceededException {
		addCoin(200);
		vendingMachine.getPopCanRack(0).enable();
		assertEquals("Enabled", controller.getLastMessage());
		pushButton(0);
		vendingMachine.getPopCanRack(0).acceptPopCan(new PopCan(names[0]));
		
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
