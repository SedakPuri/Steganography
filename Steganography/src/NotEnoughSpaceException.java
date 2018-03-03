/*Sedak Puri
 * Programming Project 4 
 * Steganography
 * Introduction to CS 3
 * March 1st, 2018
 */

public class NotEnoughSpaceException extends Exception{
	
	//Constructor
	public NotEnoughSpaceException(int sizeNeeded, int sizeAvailable) {
		super("NotEnoughSpaceException: Only " + sizeAvailable + " is available and " + sizeNeeded + " is needed");
	}
}
