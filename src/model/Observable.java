package model;

import java.util.ArrayList;

/**
 * Observable/Observer design pattern.
 * 
 */

public class Observable {
	
	private ArrayList <Observer> observers = new ArrayList<Observer>();
	
	public void attach(Observer o) {
		observers.add(o);
	}

	public void notifyObservers() {
		for(Observer o:observers) {
			o.update(this);
		}
	}
}
