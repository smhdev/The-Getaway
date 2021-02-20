package MessageOfTheDay;
import java.io.*;
import java.net.*;


/**
 * This class is used for getting the message of the day.
 *
 * @author Christian Sanger
 */
public class MessageOfTheDay {
	static final String PUZZLE_URL = "http://cswebcat.swansea.ac.uk/puzzle";
	static final String MESS_URL = "http://cswebcat.swansea.ac.uk/message?solution=";

	
	public MessageOfTheDay() {
		//Nothing to do here
	}
	/**
	 * Gets the message 
	 * @return mess the message of the day
	 * @throws Exception in case the getURL fails
	 */
	static public String puzzle() throws Exception{
		    String mess = "";
		    
		    //First get puzzle
		    try { 
		      String puzzle = getURL(PUZZLE_URL);
			  String key = solvePuzzle(puzzle);
			  mess = getURL(MESS_URL+key);
		    }
		    catch(Exception e) {
		    	throw e;
		    }
			return mess;
		}
		
	/**
	 * Gets the Url that contains the message of the day
	 * @param url the url with the message
	 * @return the result from the call
	 * @throws Exception if a network error occurs
	 */
		static public String getURL(String url) throws Exception{
			String res = "";
			try {
	  		  URL serv = new URL(url);
	  		  BufferedReader in = new BufferedReader(
	  		    new InputStreamReader(serv.openStream()));
	          String inputLine;
	  	      while ((inputLine = in.readLine()) != null) {
	  	        //System.out.println(inputLine);
	  	        res += inputLine;
	  	        
	  	      }
	  	      in.close();
	  	      return res;
			}
			catch(Exception e) {
				throw e;
			}
		}
	/**
	 * Solves the puzzle
	 * @param puz the puzzle
	 * @return the key solution
	 */
		static public String solvePuzzle(String puz) {
			String sol = "";
			char n = ' ';
			
			for(int i=0;i<puz.length();i++) {
			  int shift = i+1;
			  if (i%2==0) { //go backwards
				  n = (char) ( puz.charAt(i) - shift);
				  if(n<'A') {
					  n = (char) ( puz.charAt(i) + (26 - shift));
				  }
			  }
			  else { //go forward
				  n = (char) ( puz.charAt(i) + shift);
				  if(n>'Z') {
					  n = (char) ( puz.charAt(i) - (26 - shift));
				  }
			  }
			  sol+=n;
			}
			//last add the length
			sol = "CS-230"+ sol;
			sol+=(sol.length());
			
			return sol;		
		}


}

