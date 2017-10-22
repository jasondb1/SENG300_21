package ca.ucalgary.seng300.a1.logic;

import java.util.Observable;

import ca.ucalgary.seng300.a1.hardware.AbstractHardware;
import ca.ucalgary.seng300.a1.hardware.AbstractHardwareListener;
import ca.ucalgary.seng300.a1.hardware.DeliveryChute;
import ca.ucalgary.seng300.a1.hardware.DeliveryChuteListener;

/*
 * Observations
 * 	Door should not matter as of assignment 1
 * 	
 */
public class DCListener extends Observable implements DeliveryChuteListener {

	private String isEmpty;
	private String state = "Idle";
	
	
	@Override
	public void enabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractHardware<? extends AbstractHardwareListener> hardware) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemDelivered(DeliveryChute chute) {
		// TODO Auto-generated method stub
		state = "Item Delivered";
	    setChanged();
	    notifyObservers();
	}

	// For when door is opened, all deliverables are about to be removed
	@Override
	public void doorOpened(DeliveryChute chute) {
		// TODO Auto-generated method stub
	}

	// For when door is closed, delivery chute returned deliverable items removed from chute
	// 
	@Override
	public void doorClosed(DeliveryChute chute) {
		// TODO Auto-generated method stub
	}

	@Override
	public void chuteFull(DeliveryChute chute) {
		// TODO Auto-generated method stub
		
	}
	
	public String getState() {
		return state;
	}

}
