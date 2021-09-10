
	import java.util.*; 
	import java.io.BufferedReader;
	import java.io.InputStreamReader;
	import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
	public class LZW {
		private String word; 
		private int size=255; 
		private HashMap <String,Integer> dictionary = new HashMap <String,Integer> (); 
		private ArrayList <String> tracker = new ArrayList <String> (); // this keeps track of the output 
		private ArrayList <Integer> nums = new ArrayList <Integer> (); // to keep track of the ASCII code 
		

		public LZW () throws IOException{
			word=""; // intalizes word
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/lianawadhwani/Desktop/test.txt"))); // this line reads the file
			String line = reader.readLine(); 
			while (line != null) { // reads the file 
				word+=line; 
				line= reader.readLine();
			}
			for (int i=0; i<256; i++) { // creates the dictionary and puts the values in it 
				dictionary.put(""+(char)i,i); // this line takes a number converts in to a char and then corresponds that to the number
				
			}
		}

		public void algorithim () {
			String current=word.substring(0,1); // gets the first word of the String 
			String next=word.substring(1,2); // gets the second word of the String
			while (word.length()>0) { 
				if (dictionary.containsKey(""+current+next)==false && word.length()>1) { // checks to see if current + next is in the dictionary 
					String output=""+current+next; // adds current + next together 
					size++; // increments the size of the dictionary 
					dictionary.put(output,size); // adds the output into the dictionary 
					tracker.add(current); // adds the current into a tracker to be eventually outputed 
					word=word.substring(current.length()); 
					current=word.substring(0,1); 
					if (word.length()>1) {
						next=word.substring(1,2); // create an if statment to deal with
					}
				} else if (word.length()>1 && dictionary.containsKey(""+current+next)){
					current=""+current+next; 
					next=word.substring(current.length(), current.length()+1); 
					//word=word.substring(current.length()); 
				}
				else {
					tracker.add(current);
					word=""; 
				}
			}
				
	}
	
	public String numbers () {
		String num=""; 
		for (int i=0; i<tracker.size(); i++) {
			String temp = tracker.get(i); 
			int id=dictionary.get(temp); 
			num=num+" "+id; 
		}
		return (num); 
	}
		
		public static void main (String [] args) throws IOException {
			LZW word = new LZW (); 
			word.algorithim(); 
			System.out.println(word.numbers()); 
			System.out.println("Hello World"); 
		}
		}
		
