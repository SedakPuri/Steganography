/*Sedak Puri
 * Programming Project 4 
 * Steganography
 * Introduction to CS 3
 * March 1st, 2018
 */
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class Driver {

	public static void main(String[] args) {
		//Making Objects & Variables
		Steganography sten = new Steganography();
		Scanner keyboard = new Scanner(System.in);
		String hiddenMessage = null;
		
		//File Declarations
		File file1 = new File("DefinitelyNotASecretMessage.wav");
		File file2 = new File("encoded-1.wav");
		File file3 = new File("newFile.wav");
		
		//Decoding the initial message from "encoded-1.wav"
		try {
			System.out.println("The Secret Message is: " + sten.decodeMessage(file2));
		} catch (SecretMessageException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		//System Input
		System.out.println("Hello user! Enter a message to be encoded!");
		String message = keyboard.nextLine();
		keyboard.close();
		
		//Encoding the message of user input and decoding it
		try {
			sten.encodeMessage(file1, file3, message);
			hiddenMessage = sten.decodeMessage(file3);
		} catch (IOException | NotEnoughSpaceException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (SecretMessageException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//System Output
		System.out.println("\nThe decoded hidden message is: " + hiddenMessage);
	}

}



