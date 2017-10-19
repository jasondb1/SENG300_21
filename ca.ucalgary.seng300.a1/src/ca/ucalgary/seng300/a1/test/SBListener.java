package ca.ucalgary.seng300.a1.test;

import java.util.Observable;

import ca.ucalgary.seng300.a1.hardware.AbstractHardware;
import ca.ucalgary.seng300.a1.hardware.AbstractHardwareListener;
import ca.ucalgary.seng300.a1.hardware.SelectionButton;
import ca.ucalgary.seng300.a1.hardware.SelectionButtonListener;

public class SBListener extends Observable implements SelectionButtonListener {

	private String status = "Listening";

	/* (non-Javadoc)
	 * @see ca.ucalgary.seng300.a1.hardware.AbstractHardwareListener#enabled(ca.ucalgary.seng300.a1.hardware.AbstractHardware)
	 */
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		status = "Enabled";
	    setChanged();
	    notifyObservers();
	}

	/* (non-Javadoc)
	 * @see ca.ucalgary.seng300.a1.hardware.AbstractHardwareListener#disabled(ca.ucalgary.seng300.a1.hardware.AbstractHardware)
	 */
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		status = "Disabled";
	    setChanged();
	    notifyObservers();
	}

	/* (non-Javadoc)
	 * @see ca.ucalgary.seng300.a1.hardware.SelectionButtonListener#pressed(ca.ucalgary.seng300.a1.hardware.SelectionButton)
	 */
	public void pressed(SelectionButton button) {
		status = "Pressed";
	    setChanged();
	    notifyObservers();

	}

	/**
	 * @return the status of the selection button listener
	 */
	public String getStatus(){
		return status;
	}

	/**Sets the status to the default value
	 *
	 */
	public void clearStatus() {
		status = "Listening";
	}
}
