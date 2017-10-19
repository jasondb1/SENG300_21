/**
 *
 */
package ca.ucalgary.seng300.a1.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

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
	private int[] validCoins = {5, 10, 25, 100, 200};
	private int[] costs = {200, 250, 300};
	private ArrayList<Integer> popCanCosts;
	private ArrayList<String> popCanNames;
	private String[] names = {"pop1","pop2","pop3"};
	private int coinRackCapacity = 200;
	private int receptacleCapacity = 200;
	private int popCanRackCapacity = 20;

	//Hardware
	private VendingMachine vendingMachine;

	private Controller controller;

	/**
	 *
	 */
	@Before
	public void setupVendingmachine() {

		popCanCosts = new ArrayList(Arrays.asList(costs));
		popCanNames = new ArrayList(Arrays.asList(names));

		vendingMachine = new VendingMachine(validCoins, popCanNames.size(), coinRackCapacity,
				popCanRackCapacity, receptacleCapacity);
		vendingMachine.configure(popCanNames, popCanCosts);

		controller = new Controller(vendingMachine, names);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
