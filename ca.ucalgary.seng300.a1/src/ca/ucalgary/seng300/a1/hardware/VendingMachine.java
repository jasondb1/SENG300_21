package ca.ucalgary.seng300.a1.hardware;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.ucalgary.seng300.a1.Coin;
import ca.ucalgary.seng300.a1.PopCan;

/**
 * Represents a standard configuration of the vending machine hardware:
 * <ul>
 * <li>one coin slot;</li>
 * <li>one coin receptacle (called the coin receptacle) to temporarily store
 * coins entered by the user;</li>
 * <li>one coin receptacle (called the storage bin) to store coins that have
 * been accepted as payment;</li>
 * <li>a set of one or more coin racks (the number and the denomination of coins
 * stored by each is specified in the constructor);</li>
 * <li>one delivery chute used to deliver pop cans and to return coins;</li>
 * <li>a set of one or more pop can racks (the number, cost, and pop name stored
 * in each is specified in the constructor);</li>
 * <li>one textual display;</li>
 * <li>a set of one or more selection buttons (exactly one per pop can rack);
 * and</li>
 * <li>two indicator lights: one to indicate that exact change should be used by
 * the user; the other to indicate that the machine is out of order.</li>
 * </ul>
 * <p>
 * The component devices are interconnected as follows:
 * <ul>
 * <li>the output of the coin slot is connected to the input of the coin
 * receptacle;</li>
 * <li>the outputs of the coin receptacle are connected to the inputs of the
 * coin racks (for valid coins to be stored therein), the delivery chute (for
 * invalid coins or other coins to be returned), and the storage bin (for coins
 * to be accepted that do not fit in the coin racks);</li>
 * <li>the output of each coin rack is connected to the delivery chute; and</li>
 * <li>the output of each pop can rack is connected to the delivery chute.</li>
 * </ul>
 * <p>
 * Each component device can be disabled to prevent any physical movements.
 * Other functionality is not affected by disabling a device; hence devices that
 * do not involve physical movements are not affected by "disabling" them.
 * <p>
 * Most component devices have some sort of maximum capacity (e.g., of the
 * number of pop cans that can be stored therein). In some cases, this is a
 * simplification of the physical reality for the sake of simulation.
 */
public final class VendingMachine {
    private boolean safetyOn = false;

    private int[] coinKinds;
    private CoinSlot coinSlot;
    private CoinReceptacle receptacle, storageBin;
    private DeliveryChute deliveryChute;
    private CoinRack[] coinRacks;
    private Map<Integer, CoinChannel> coinRackChannels;
    private PopCanRack[] popCanRacks;
    private Display display;
    private SelectionButton[] buttons;
    private int[] popCanCosts;
    private String[] popCanNames;
    private IndicatorLight exactChangeLight, outOfOrderLight;

    /**
     * Creates a standard arrangement for the vending machine. All the
     * components are created and interconnected. The machine is initially
     * empty. The pop kind names and costs are initialized to "&lt;default&gt;"
     * and 1 respectively.
     * 
     * <p>
     * All pop kinds
     * 
     * @param coinKinds
     *            The values (in cents) of each kind of coin. The order of the
     *            kinds is maintained. One coin rack is produced for each kind.
     *            Each kind must have a unique, positive value.
     * @param selectionButtonCount
     *            The number of selection buttons on the machine. Must be
     *            positive.
     * @param coinRackCapacity
     *            The maximum capacity of each coin rack in the machine. Must be
     *            positive.
     * @param popCanRackCapacity
     *            The maximum capacity of each pop can rack in the machine. Must
     *            be positive.
     * @param receptacleCapacity
     *            The maximum capacity of the coin receptacle, storage bin, and
     *            delivery chute. Must be positive.
     * @throws SimulationException
     *             If any of the arguments is null.
     */
    public VendingMachine(int[] coinKinds, int selectionButtonCount, int coinRackCapacity, int popCanRackCapacity, int receptacleCapacity) {
	if(coinKinds == null)
	    throw new SimulationException("Arguments may not be null");

	if(selectionButtonCount < 1 || coinRackCapacity < 1 || popCanRackCapacity < 1)
	    throw new SimulationException("Counts and capacities must be positive");

	if(coinKinds.length < 1)
	    throw new SimulationException("At least one coin kind must be accepted");

	this.coinKinds = Arrays.copyOf(coinKinds, coinKinds.length);

	Set<Integer> currentCoinKinds = new HashSet<>();
	for(int coinKind : coinKinds) {
	    if(coinKind < 1)
		throw new SimulationException("Coin kinds must have positive values");

	    if(currentCoinKinds.contains(coinKind))
		throw new SimulationException("Coin kinds must have unique values");

	    currentCoinKinds.add(coinKind);
	}

	display = new Display();
	coinSlot = new CoinSlot(coinKinds);
	receptacle = new CoinReceptacle(receptacleCapacity);
	storageBin = new CoinReceptacle(receptacleCapacity);
	deliveryChute = new DeliveryChute(receptacleCapacity);
	coinRacks = new CoinRack[coinKinds.length];
	coinRackChannels = new HashMap<Integer, CoinChannel>();
	for(int i = 0; i < coinKinds.length; i++) {
	    coinRacks[i] = new CoinRack(coinRackCapacity);
	    coinRacks[i].connect(new CoinChannel(deliveryChute));
	    coinRackChannels.put(new Integer(coinKinds[i]), new CoinChannel(coinRacks[i]));
	}

	popCanRacks = new PopCanRack[selectionButtonCount];
	for(int i = 0; i < selectionButtonCount; i++) {
	    popCanRacks[i] = new PopCanRack(popCanRackCapacity);
	    popCanRacks[i].connect(new PopCanChannel(deliveryChute));
	}

	popCanNames = new String[selectionButtonCount];
	for(int i = 0; i < selectionButtonCount; i++)
	    popCanNames[i] = "<default>";
	popCanCosts = new int[selectionButtonCount];
	for(int i = 0; i < selectionButtonCount; i++)
	    popCanCosts[i] = 1;

	buttons = new SelectionButton[selectionButtonCount];
	for(int i = 0; i < selectionButtonCount; i++)
	    buttons[i] = new SelectionButton();

	coinSlot.connect(new CoinChannel(receptacle), new CoinChannel(deliveryChute));
	receptacle.connect(coinRackChannels, new CoinChannel(deliveryChute), new CoinChannel(storageBin));

	exactChangeLight = new IndicatorLight();
	outOfOrderLight = new IndicatorLight();
    }

    /**
     * Configures the hardware to use a set of names and costs for pop cans.
     * 
     * @param popCanNames
     *            A list of names for pop cans, each position of which will
     *            correspond to a selection button. No name can be null or
     *            empty.
     * @param popCanCosts
     *            A list of costs for pop cans, each position of which will
     *            correspond to a selection button. No cost can be non-positive.
     */
    public void configure(List<String> popCanNames, List<Integer> popCanCosts) {
	if(popCanNames.size() != this.popCanNames.length || popCanCosts.size() != this.popCanCosts.length)
	    throw new SimulationException("The number of names and costs must be identical to the number of pop can racks in the machine");

	for(String popName : popCanNames)
	    if(popName.equals(""))
		throw new SimulationException("Pop can names cannot be the empty string");

	for(int popCost : popCanCosts)
	    if(popCost < 1)
		throw new SimulationException("Pop can costs cannot be less than 1");

	popCanNames.toArray(this.popCanNames);

	int i = 0;
	for(Integer popCanCost : popCanCosts)
	    this.popCanCosts[i++] = popCanCost;
    }

    /**
     * Disables all the components of the hardware that involve physical
     * movements. Activates the out of order light.
     */
    public void enableSafety() {
	safetyOn = true;
	coinSlot.disable();
	deliveryChute.disable();

	for(int i = 0; i < popCanRacks.length; i++)
	    popCanRacks[i].disable();

	for(int i = 0; i < coinRacks.length; i++)
	    coinRacks[i].disable();

	outOfOrderLight.activate();
    }

    /**
     * Enables all the components of the hardware that involve physical
     * movements. Deactivates the out of order light.
     */
    public void disableSafety() {
	safetyOn = false;
	coinSlot.enable();
	deliveryChute.enable();

	for(int i = 0; i < popCanRacks.length; i++)
	    popCanRacks[i].enable();

	for(int i = 0; i < coinRacks.length; i++)
	    coinRacks[i].enable();

	outOfOrderLight.deactivate();
    }

    /**
     * Returns whether the safety is currently engaged.
     * 
     * @return true if the safety is enabled; false if the safety is disabled.
     */
    public boolean isSafetyEnabled() {
	return safetyOn;
    }

    /**
     * Accesses the exact change light.
     * 
     * @return The relevant device.
     */
    public IndicatorLight getExactChangeLight() {
	return exactChangeLight;
    }

    /**
     * Accesses the out of order light.
     * 
     * @return The relevant device.
     */
    public IndicatorLight getOutOfOrderLight() {
	return outOfOrderLight;
    }

    /**
     * Accesses a selection button at the indicated index.
     * 
     * @param index
     *            The index of the desired selection button.
     * @return The relevant device.
     * @throws IndexOutOfBoundsException
     *             if the index &lt; 0 or the index &gt;= number of selection
     *             buttons.
     */
    public SelectionButton getSelectionButton(int index) {
	return buttons[index];
    }

    /**
     * Accesses the number of selection buttons.
     * 
     * @return The number of selection buttons. Will be positive.
     */
    public int getNumberOfSelectionButtons() {
	return buttons.length;
    }

    /**
     * Accesses the coin slot.
     * 
     * @return The relevant device.
     */
    public CoinSlot getCoinSlot() {
	return coinSlot;
    }

    /**
     * Accesses the coin receptacle.
     * 
     * @return The relevant device.
     */
    public CoinReceptacle getCoinReceptacle() {
	return receptacle;
    }

    /**
     * Accesses the storage bin.
     * 
     * @return The relevant device.
     */
    public CoinReceptacle getStorageBin() {
	return storageBin;
    }

    /**
     * Accesses the delivery chute.
     * 
     * @return The relevant device.
     */
    public DeliveryChute getDeliveryChute() {
	return deliveryChute;
    }

    /**
     * Accesses the number of coin racks.
     * 
     * @return The number of coin racks. Will be positive.
     */
    public int getNumberOfCoinRacks() {
	return coinRacks.length;
    }

    /**
     * Accesses a coin rack at the indicated index.
     * 
     * @param index
     *            The index of the desired coin rack.
     * @return The relevant device.
     * @throws IndexOutOfBoundsException
     *             if the index &lt; 0 or the index &gt;= number of coin racks.
     */
    public CoinRack getCoinRack(int index) {
	return coinRacks[index];
    }

    /**
     * Accesses the coin rack that handles coins of the specified kind. If none
     * exists, null is returned.
     * 
     * @param kind
     *            The value of the coin kind for which the rack is sought.
     * @return The relevant device.
     */
    public CoinRack getCoinRackForCoinKind(int kind) {
	CoinChannel cc = coinRackChannels.get(kind);
	if(cc != null)
	    return (CoinRack)cc.getSink();
	return null;
    }

    /**
     * Accesses a coin kind that corresponds to a coin rack at the specified
     * index.
     * 
     * @param index
     *            The index of the coin rack.
     * @return The coin kind at the specified index.
     * @throws IndexOutOfBoundsException
     *             If the index is &lt; 0 or the index is &gt;= the number of
     *             coin racks.
     */
    public Integer getCoinKindForCoinRack(int index) {
	return coinKinds[index];
    }

    /**
     * Accesses the number of pop can racks.
     * 
     * @return The number of pop can racks in the machine.
     */
    public int getNumberOfPopCanRacks() {
	return popCanRacks.length;
    }

    /**
     * Accesses the name of the pop kind whose selection button and rack are at
     * the indicated index.
     * 
     * @param index
     *            The index of the desired pop can rack/selection button.
     * @return The name of the pop brand corresponding to the pop can
     *         rack/selection button at the specified index.
     * @throws IndexOutOfBoundsException
     *             if the index &lt; 0 or the index &gt;= the number of pop can
     *             racks.
     */
    public String getPopKindName(int index) {
	return popCanNames[index];
    }

    /**
     * Accesses the cost of the pop kind whose selection button and rack are at
     * the indicated index.
     * 
     * @param index
     *            The index of the desired pop can rack/selection button.
     * @return The cost of the pop kind stored in the rack at the specified
     *         index.
     * @throws IndexOutOfBoundsException
     *             if the index &lt; 0 or the index &gt;= number of pop can
     *             rack.
     */
    public int getPopKindCost(int index) {
	return popCanCosts[index];
    }

    /**
     * Accesses a pop can rack at the indicated index.
     * 
     * @param index
     *            The index of the desired pop can rack.
     * @return The pop can rack at the indicated index.
     * @throws IndexOutOfBoundsException
     *             if the index &lt; 0 or the index &gt;= number of pop can
     *             rack.
     */
    public PopCanRack getPopCanRack(int index) {
	return popCanRacks[index];
    }

    /**
     * Accesses the textual display.
     * 
     * @return The relevant device.
     */
    public Display getDisplay() {
	return display;
    }

    /**
     * A convenience method for constructing and loading a set of pop cans into
     * the machine.
     * 
     * @param popCanCounts
     *            A list representing the number of pops to create and load into
     *            the corresponding rack.
     * @throws SimulationException
     *             If the number of arguments is different than the number of
     *             racks, or if any of the counts are negative.
     */
    public void loadPopCans(int... popCanCounts) {
	if(popCanCounts.length != getNumberOfPopCanRacks())
	    throw new SimulationException("Pop can counts have to equal number of racks");

	int i = 0;
	for(int popCanCount : popCanCounts) {
	    if(popCanCount < 0)
		throw new SimulationException("Each count must not be negative");

	    PopCanRack pcr = getPopCanRack(i);
	    String name = getPopKindName(i);
	    for(int pops = 0; pops < popCanCount; pops++)
		pcr.load(new PopCan(name));

	    i++;
	}
    }

    /**
     * A convenience method for constructing and loading a set of coins into the
     * machine.
     * 
     * @param coinCounts
     *            A variadic list of ints each representing the number of coins
     *            to create and load into the corresponding rack.
     * @throws SimulationException
     *             If the number of arguments is different than the number of
     *             racks, or if any of the counts are negative.
     */
    public void loadCoins(int... coinCounts) {
	if(coinCounts.length != getNumberOfCoinRacks())
	    throw new SimulationException("Coin counts have to equal number of racks");

	int i = 0;
	for(int coinCount : coinCounts) {
	    if(coinCount < 0)
		throw new SimulationException("Each count must not be negative");

	    CoinRack cr = getCoinRack(i);
	    int value = getCoinKindForCoinRack(i);
	    for(int coins = 0; coins < coinCount; coins++)
		cr.load(new Coin(value));

	    i++;
	}
    }
}
