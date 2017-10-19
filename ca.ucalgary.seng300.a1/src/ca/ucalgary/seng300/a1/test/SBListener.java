package ca.ucalgary.seng300.a1.test;

import java.util.Observable;

import ca.ucalgary.seng300.a1.hardware.AbstractHardware;
import ca.ucalgary.seng300.a1.hardware.AbstractHardwareListener;
import ca.ucalgary.seng300.a1.hardware.SelectionButton;
import ca.ucalgary.seng300.a1.hardware.SelectionButtonListener;

public class SBListener extends Observable implements SelectionButtonListener {

	@Override
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pressed(SelectionButton button) {
		// TODO Auto-generated method stub

	}

	//get
}
