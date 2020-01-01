package model;

import model.Observable;

/*
 * Observable/Observer design pattern.
 * 
 */

public interface Observer {
	public void update(Observable o);
}
