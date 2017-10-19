package ca.ucalgary.seng300.a1.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
//import org.junit.Rule;
import org.junit.Test;

import ca.ucalgary.seng300.a1.Coin;
import ca.ucalgary.seng300.a1.hardware.AbstractCoinAcceptor;
import ca.ucalgary.seng300.a1.hardware.AbstractHardware;
import ca.ucalgary.seng300.a1.hardware.AbstractHardwareListener;
import ca.ucalgary.seng300.a1.hardware.CapacityExceededException;
import ca.ucalgary.seng300.a1.hardware.CoinChannel;
import ca.ucalgary.seng300.a1.hardware.CoinReceptacle;
import ca.ucalgary.seng300.a1.hardware.CoinSlot;
import ca.ucalgary.seng300.a1.hardware.CoinSlotListener;
import ca.ucalgary.seng300.a1.hardware.DeliveryChute;
import ca.ucalgary.seng300.a1.hardware.DisabledException;
import ca.ucalgary.seng300.a1.hardware.SimulationException;

/**
 * @author Jason De Boer Student ID: 30034428
 *
 * Tests all of the methods in the CoinSlot and Parent (AbstractHardware) class
 * Notes: Two stub methods are used to test the methods: StubCoinSlotListener, and StubSink which simulate a listener and sink
 *
 * As this is an automated test, code coverage is for all lines of code in the CoinSlot class as well as the methods provided in
 * the parent class (AbstractHardware) class. Valid, and invalid coin simulations are tested, as well as enabling and disabling
 * of the coinslot. The capacity exceeded is also tested. And deregistration of the listeners is tested.
 *
 *
 * The tests that were selected test functionality with typical classes from the simulator,
 * as well as with a StubSink, simulating hardware that forces a CapacityExceeded Exception as required
 *
 */
public class TestCoinSlot {


	/** A stub class that implements CoinSlotListener providing access to notification messages
	 *
	 */
	private class StubCoinSlotListener implements CoinSlotListener {

		public boolean insertedValidCoin = false;
		public boolean coinRejected = false;
		public boolean enabled;
		public boolean disabled;
		@Override
		public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
			enabled = true;
		}

		@Override
		public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
			disabled = true;
		}

		@Override
		public void validCoinInserted(CoinSlot slot, Coin coin) {
			insertedValidCoin = true;
		}

		@Override
		public void coinRejected(CoinSlot slot, Coin coin) {
			coinRejected = true;
		}
	}

	//Use this stub to ensure a hasSpace() is false to test try/catch exceptions

	/**A stub class that implements a sink connected to a coinChannel providing access to hasSpace() method,
	 * as well as providing a way to throw a CapacityExceededException for testing
	 *
	 * 	 */
	public final class StubSink implements AbstractCoinAcceptor{
		public boolean space = true;

		public StubSink(){

		}

		public boolean hasSpace() {//always return that space is available
			return space;
		}

		public void deliver(Coin coin) throws CapacityExceededException {
			throw new CapacityExceededException();
		}

		@Override
		public void acceptCoin(Coin coin) throws CapacityExceededException, DisabledException {
			throw new CapacityExceededException();
		}
	}

	private CoinSlot coinslot;
	private StubCoinSlotListener coinslotListenerStub = new StubCoinSlotListener();
	//private CoinChannel coinChannelStub = new CoinChannel();
	private StubSink mySink = new StubSink();
	int maxCapacity = 20; //set higher if this is a concern
	Coin validCoin = new Coin(100);
	Coin invalidCoin = new Coin (500);

	@Before
	public void setupCoinSlot() {
		int[] coinVals = { 25, 100, 200 };
		coinslot = new CoinSlot(coinVals);

		// connect coinslot to coin receptacle with 20 coin capacity
		coinslot.connect(new CoinChannel(new CoinReceptacle(maxCapacity)), new CoinChannel(new DeliveryChute(maxCapacity)));
		coinslot.register(coinslotListenerStub);
	}

	@After
	public void cleanupCoinSlot() {
		coinslot.deregisterAll();
	}

	//////////////////////////////////////////////////////////////////
	// Testing valid value of coin
	//////////////////////////////////////////////////////////////////
	@Test
	public void testInsertValidCoin() throws DisabledException {
		coinslot.addCoin(validCoin);
		assertTrue(coinslotListenerStub.insertedValidCoin);	//test listener to ensure insertedvalid coin is true
		assertFalse(coinslotListenerStub.coinRejected);		//test listener to ensure coinRejected is False
	}

	// Testing valid value of coin
	@Test
	public void testInsertInvalidCoin() throws DisabledException {
		coinslot.addCoin(invalidCoin);
		assertTrue(coinslotListenerStub.coinRejected);		//test listener to ensure coinRejected is true
		assertFalse(coinslotListenerStub.insertedValidCoin);//test listener to ensure insertedvalid coin is false
	}

	//////////////////////////////////////////////////////////////////
	// Test enabling/disabling features
	//////////////////////////////////////////////////////////////////

	// test disabling lockout features
	@Test
	public void testCoinSlotDisabled() throws DisabledException {
		coinslot.disable();
		assertTrue(coinslot.isDisabled());				//test to see that isDisabled reports correctly
		assertTrue(coinslotListenerStub.disabled);		//test that message was sent to listener
	}

	// test enable hardware
	@Test
	public void testCoinSlotEnabled() throws DisabledException {
		coinslot.disable(); 							//set to disable first to disable to ensure default value is not showing
		assertTrue(coinslot.isDisabled());
		coinslot.enable();
		assertFalse(coinslot.isDisabled());				//test to see that isDisabled reports correctly
		assertTrue(coinslotListenerStub.enabled);		//test that message was sent to listener
	}

	// test to see that exception is thrown if adding a coin while disabled
	@Test(expected = DisabledException.class)
	public void testSlotDisabledAddCoin() throws DisabledException {
		coinslot.disable();
		coinslot.addCoin(validCoin);
	}

	//////////////////////////////////////////////////////////////////
	// Test capacities exceeded
	//////////////////////////////////////////////////////////////////

	//test valid coin capacity exceeded on typical setup
	//if max is entered, then additional coins go into invalid coin channel
	@Test
	public void testCapacityExceededValidCoin() throws DisabledException {
		for (int i = 0; i < (maxCapacity + 1); i++ ) {
			coinslot.addCoin(validCoin);
		}
		assertTrue(coinslotListenerStub.coinRejected);	//check listener to see if coin is rejected
	}

	//test invalid coin capacity exceeded on typical setup
	//@Test(expected = CapacityExceededException.class)
	@Test(expected = SimulationException.class)
	public void testCapacityExceededInvalidCoin() throws DisabledException {
		for (int i = 0; i < (maxCapacity + 1); i++ ) {
			coinslot.addCoin(invalidCoin);
		}
	}

	//////////////////////////////////////////////////////////////////
	// Test capacities
	//////////////////////////////////////////////////////////////////
	//Test with mySink to ensure that try/catch block in addCoin method is tested
	//ensure capacity is exceeded with mySink to force CapacityExceededException

	//tests Capacity Exceeded on valid coin channel
	@Test(expected = SimulationException.class)
	public void testCapacityExceededCustomSinkValidCoin() throws DisabledException {
		coinslot.connect(new CoinChannel(mySink), new CoinChannel(mySink));
		for (int i = 0; i < (maxCapacity + 1); i++ ) {
			coinslot.addCoin(validCoin);
		}
	}

	//tests Capacity Exceeded on invalid coin channel
	@Test(expected = SimulationException.class)
	public void testCapacityExceededCustomSinkInvalidCoin() throws DisabledException {
		coinslot.connect(new CoinChannel(mySink), new CoinChannel(mySink));
		for (int i = 0; i < (maxCapacity + 1); i++ ) {
			coinslot.addCoin(invalidCoin);
		}
	}

	//////////////////////////////////////////////////////////////////
	// Deregistration of listener
	//////////////////////////////////////////////////////////////////

	//implies that the registration of the listener works, or an null pointer exception
	// would be thrown

	//tests deregistering of the coinSlot listener
	@Test
	public void testDeRegister() throws DisabledException {
		coinslot.enable();
		assertTrue(coinslotListenerStub.enabled);					//ensure that the listener is registered and working
		coinslot.deregister(coinslotListenerStub);
		coinslot.addCoin(validCoin);
		assertFalse(coinslotListenerStub.insertedValidCoin);		//ensure that the listener is not receiving the valid coin message
	}

	//tests deregistering of the coinSlot listener
	@Test
	public void testDeRegisterAll() throws DisabledException {
		coinslot.enable();
		assertTrue(coinslotListenerStub.enabled);					//ensure that the listener is registered and working
		coinslot.deregisterAll();
		coinslot.addCoin(validCoin);
		assertFalse(coinslotListenerStub.insertedValidCoin);		//ensure that the listener is not receiving the valid coin message
	}

}
