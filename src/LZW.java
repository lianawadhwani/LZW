
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
		private ArrayList <String> tracker = new ArrayList <String> (); 
		private ArrayList <Integer> nums = new ArrayList <Integer> (); 
		

		public LZW () throws IOException{
			word=""; 
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/lianawadhwani/Desktop/test.txt"))); // this line reads the file
			String line = reader.readLine();
			while (line != null) {
				word+=line; 
				line= reader.readLine();
			}
			for (int i=0; i<256; i++) {
				dictionary.put(""+(char)i,i); // this line takes a number converts in to a char and then corresponds that to the number
				
			}
		}

		public void algorithim () {
			String current=word.substring(0,1); 
			String next=word.substring(1,2); 
			while (word.length()>0) {
				if (dictionary.containsKey(""+current+next)==false && word.length()>1) {
					String output=""+current+next; 
					dictionary.put(output, size+1); 
					tracker.add(current); 
					word=word.substring(current.length()); 
					current=word.substring(0,1); 
					next=word.substring(1,2); // create an if statment to deal with
				} else if (word.length()>1 && dictionary.containsKey(""+current+next)){
					current=""+current+next; 
					if (word.length()>3)
					{
					next=word.substring(current.length(), current.length()+1); 
					//word=word.substring(current.length()); 
				} else {
					next=word.substring(current.length()); 
					word=""; 
				}
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
		
