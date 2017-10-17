package ca.ucalgary.seng300.a1.hardware;

import ca.ucalgary.seng300.a1.PopCan;

/**
 * Listens for events emanating from a pop can rack.
 */
public interface PopCanRackListener extends AbstractHardwareListener {
    /**
     * An event announced when the indicated pop can is added to the indicated
     * pop can rack.
     * 
     * @param popCanRack
     *            The device on which the event occurred.
     * @param popCan
     *            The pop can added.
     */
    void popCanAdded(PopCanRack popCanRack, PopCan popCan);

    /**
     * An event announced when the indicated pop can is removed from the
     * indicated pop can rack.
     * 
     * @param popCanRack
     *            The device on which the event occurred.
     * @param popCan
     *            The pop can removed.
     */
    void popCanRemoved(PopCanRack popCanRack, PopCan popCan);

    /**
     * An event announced when the indicated pop can rack becomes full.
     * 
     * @param popCanRack
     *            The device on which the event occurred.
     */
    void popCansFull(PopCanRack popCanRack);

    /**
     * An event announced when the indicated pop can rack becomes empty.
     * 
     * @param popCanRack
     *            The device on which the event occurred.
     */
    void popCansEmpty(PopCanRack popCanRack);

    /**
     * Announces that the indicated sequence of pop cans has been added to the
     * indicated rack. Used to simulate direct, physical loading of
     * the rack.
     * 
     * @param rack
     *            The rack where the event occurred.
     * @param popCans
     *            The pop cans that were loaded.
     */
    void popCansLoaded(PopCanRack rack, PopCan... popCans);

    /**
     * Announces that the indicated sequence of pop cans has been removed to the
     * indicated pop can rack. Used to simulate direct, physical unloading of
     * the rack.
     * 
     * @param rack
     *            The rack where the event occurred.
     * @param popCans
     *            The pop cans that were unloaded.
     */
    void popCansUnloaded(PopCanRack rack, PopCan... popCans);
}
