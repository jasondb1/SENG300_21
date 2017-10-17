package ca.ucalgary.seng300.a1;

/**
 * Instances of this class represent individual coins.
 */
public class Coin implements Deliverable {
    private int value;

    /**
     * Basic constructor.
     * 
     * @param value
     *            The value of the coin, in multiples of the basic unit of
     *            currency (e.g., cents). Fractions are not supported.
     * @throws IllegalArgumentException
     *             If the value is &lt; 1.
     */
    public Coin(int value) {
	if(value <= 0)
	    throw new IllegalArgumentException("The value must be greater than 0: the argument passed was " + value);
	this.value = value;
    }

    /**
     * Accessor for the value.
     * 
     * @return The value of the coin. Should always be greater than 0.
     */
    public int getValue() {
	return value;
    }

    @Override
    public String toString() {
	return "" + getValue();
    }
}
