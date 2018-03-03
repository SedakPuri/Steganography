/*Sedak Puri
 * Programming Project 4 
 * Steganography
 * Introduction to CS 3
 * March 1st, 2018
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class Steganography implements Serializable{
	private static final long serialVersionUID = 1L;

	public static int[] getDataLocations(int start, int stop, int numLocations) throws NotEnoughSpaceException{

		//Computing the equal size between each locations
		int sizeBetweenLocations = (stop - start)/numLocations;

		//Checking if there is enough space between the locations and throwing an exception if not
		if (sizeBetweenLocations < 5) {
			int functionalStopLocation = start + 6*numLocations;		   																	
			throw new NotEnoughSpaceException(functionalStopLocation, stop - start); 	   													
		}

		//Setting the first location equal to start and figuring out next locations
		int[] locations = new int[numLocations];
		for (int i = 0; i < numLocations; ++i) {
			locations[i] = start + i*sizeBetweenLocations;
		}

		//Returning the array
		return locations;
	}

	public static void encodeMessage(File inputFile, File outputFile, String message) throws IOException, NotEnoughSpaceException{

		//Copies the file 
		try {
			Files.copy(inputFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e){																											
			System.out.println("IOException thrown: Unable to copy file");
		}

		//Converting the message to an array of chars
		char[] messageChar = message.toCharArray();

		//Computing Size of Message
		int length = message.length();

		//Computing where to put the data in the file
		int[] locations = getDataLocations(54, 10000, length);																			

		//Opening read/write into the copied file
		RandomAccessFile raf = new RandomAccessFile(outputFile, "rw");

		//Writing Initial Length
		raf.seek(50);																													
		raf.writeInt(locations.length);

		//Writing the rest of the characters at their locations
		for(int i = 0; i <= length-1; ++i) {
			//Writing Characters
			raf.seek(locations[i]);																										
			raf.writeChar(messageChar[i]);	

			//Location of integer
			raf.seek(locations[i] + 2);	
			
			//Handles the last iteration of the loop
			if (i == locations.length-1)
				break;
			
			//Writes the int location of the next character
			raf.writeInt(locations[i+1]);
		}
		raf.close();
		System.out.println("\nMessage Encoded Successfully");
	}

	public static String decodeMessage(File inputFile) throws SecretMessageException{
		char[] message = null;

		//If anything goes wrong with decoding, throw a SecretMessageException
		try{
			RandomAccessFile rafDecode = new RandomAccessFile(inputFile, "rw");

			//Finding the length and making array of chars for that length
			rafDecode.seek(50);																													
			int length = rafDecode.readInt();
			message = new char[length];
			
			//Reading the char and int of first iteration (Hard-coded for the first iteration)
			rafDecode.seek(54);	
			message[0] = rafDecode.readChar();
			rafDecode.seek(56);
			int nextLocation = rafDecode.readInt();
			
			//For loop to determine the message by following the location-int values
			for(int i = 1; i < length; i++) {
				rafDecode.seek(nextLocation);																			
				message[i] = rafDecode.readChar();
				rafDecode.seek(nextLocation + 2);
				nextLocation = rafDecode.readInt();
			}
			rafDecode.close();
		} catch (Exception e) {
			throw new SecretMessageException("IO Exception: Secret Message Not Found");									
		}
		
		//Converting the char to a string and returning string 
		String secretMessage1 = new String(message);
		return secretMessage1;
	}
}
