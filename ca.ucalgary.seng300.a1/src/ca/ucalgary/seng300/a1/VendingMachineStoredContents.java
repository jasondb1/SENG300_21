package ca.ucalgary.seng300.a1;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a simple data class used to record the contents of the vending
 * machine when it is unloaded.
 */
public class VendingMachineStoredContents {
    /**
     * This list records separate lists of Coins for each coin rack, in the same
     * order as they appeared in the vending machine.
     */
    public List<List<Coin>> coinsInCoinRacks = new ArrayList<>();

    /**
     * This list records the coins that were used as payment that had to be
     * stored in the storage bin.
     */
    public List<Coin> paymentCoinsInStorageBin = new ArrayList<>();

    /**
     * This list records separate lists of {@link PopCan}s for each pop can
     * rack, in the same order as they appeared in the vending machine.
     */
    public List<List<PopCan>> popCansInPopCanRacks = new ArrayList<>();
}
