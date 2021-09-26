import java.util.*; 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LZW {
	
	private StringBuilder word; 
	private int size=255; 
	private HashMap <String,Integer> dictionary = new HashMap <String,Integer> (); 
	private ArrayList <String> tracker = new ArrayList <String> (); // this keeps track of the output 
	private ArrayList <Integer> nums = new ArrayList <Integer> (); // to keep track of the ASCII code 


	/**
	 * Constructor for LZW object
	 * @param filename
	 * @throws IOException
	 */
	public LZW (String filename) throws IOException{
		word = new StringBuilder();
		word.append(""); // initializes word
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename))); // this line reads the file
		String line = reader.readLine(); 
		while (line != null) { // reads the file 
			word.append(line); 
			line= reader.readLine();
		}
		for (int i=0; i<256; i++) { // creates the dictionary and puts the values in it 
			dictionary.put(""+(char)i,i); // this line takes a number converts in to a char and then corresponds that to the number

		}
	}

	/**
	 * Main method that encodes a message.
	 */
	public void algorithim () {
		
		String current=word.substring(0,1); // gets the first word of the String 
		String next=word.substring(1,2); // gets the second word of the String
		
		while (word.length()>0) { 
			if (dictionary.containsKey(""+current+next)==false && word.length()>1) { // checks to see if current + next is in the dictionary 
				String output=""+current+next; // adds current + next together 
				size++; // increments the size of the dictionary 
				dictionary.put(output,size); // adds the output into the dictionary 
				tracker.add(current); // adds the current into a tracker to be eventually output 
				StringBuilder temp = new StringBuilder();
				temp.append(word.substring(current.length()));
				word=temp; 
				current=word.substring(0,1); 
				if (word.length()>1) {
					next=word.substring(1,2); // create an if statement to deal with
				}
			} else if (word.length()>1 && dictionary.containsKey(""+current+next)){
				current=""+current+next; 
				next=word.substring(current.length(), current.length()+1); 
				//word=word.substring(current.length()); 
			}
			else {
				tracker.add(current);
				StringBuilder temp = new StringBuilder();
				temp.append("");
				word=temp; 
			}
		}

	}

	public String decompress1()
		{
		
		HashMap<Integer, String> newDict = new HashMap<Integer,String>(); 

		String adding = ""; 

		for (int i=0; i<256; i++) 
		{ 
			newDict.put(i, ""+(char)i);
		}

		for(int index = 0; index < nums.size()-1; index ++)

		{

			int currentInt = nums.get(index); 
			if(nums.get(index+1)>newDict.size()-1)
			{
				System.out.println(nums.get(index+1)); 
				String current = newDict.get(nums.get(index)); 
				System.out.println(current); 
				String firstPreviousAdded = ""+current.charAt(0); 
				newDict.put(newDict.size(), current+firstPreviousAdded); 
			}
			else
			{

				String currentString = newDict.get(nums.get(index)); 
				String nextString = newDict.get(nums.get(index+1)); 

				String firstN = ""+nextString.charAt(0); 
				newDict.put(newDict.size(), currentString+firstN);

			}

		}


		for(int index = 0; index<nums.size(); index ++)
		{

			adding+= newDict.get(nums.get(index)); 
		}
		return adding; 


	}

	public String numbers () {
		String num=""; 
		for (int i=0; i<tracker.size(); i++) {
			String temp = tracker.get(i); 
			int id=dictionary.get(temp); 
			num=num+" "+id; 
			nums.add(dictionary.get(temp));
		}
		return (num); 
	}
	
//	public void outputEncoded () {
//		
//	}
//	
//	public void outputDecoded() {
//		
//	}

	public static void main (String [] args) throws IOException {
		
		long startTimeNano = System.nanoTime();
		long startTimeMillis = System.currentTimeMillis();

		LZW word = new LZW ("lzw-file1.txt"); 
		word.algorithim(); 
		
		System.out.println(word.numbers());
		System.out.println(word.nums); //changed nums to public to check this statement, then changed it back to private
		System.out.println(word.decompress1()); 
		System.out.println("Hello World");

		long endTimeNano = System.nanoTime();
		long endTimeMillis = System.currentTimeMillis();

		System.out.println("Total time: " + (endTimeNano-startTimeNano) + " nanoseconds.");
		System.out.println("Total time: " + (endTimeMillis-startTimeMillis) + " milliseconds.");
	}
}

