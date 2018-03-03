/*Sedak Puri
 * Programming Project 4 
 * Steganography
 * Introduction to CS 3
 * March 1st, 2018
 */
public class SecretMessageException extends Exception{
	
	//Constructor
	public SecretMessageException(String message) {
		super("SecretMessageException: " + message);
	}
}
