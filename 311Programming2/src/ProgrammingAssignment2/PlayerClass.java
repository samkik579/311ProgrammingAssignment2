/**
 * 
 */
package ProgrammingAssignment2;
import ProgrammingAssignment2.ArgsProcessor;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

/**
 * @author samikakikkeri
 *
 */
//need to create an enum for the different player types 
enum PTypes{ 
	titForTat, grudger, alwaysCooperate, alwaysDefect
}
public class PlayerClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArgsProcessor ap = new ArgsProcessor (args);
        int n = ap.nextInt ("How many players will there be?"); // n = number of players
        int m = ap.nextInt ("How many games should the players play?"); // m = number of games
        double p = ap.nextInt ("What is the percentage of players that should be eliminated after each generation?"); //p = percentage eliminated
        int k = ap.nextInt ("How mny generations would you like?"); // k = number of generation 
      //System.out.println(n + " " + m + " " + p + " " + k);
        
        //need to create an integer array to hold the payoffs of all the players
        int[] playerPayoff = new int[n];
        //create an array for the 4 different player types and populate it equally
        PTypes[] players = new PTypes[n];
        int[] checker = new int[n];//keeps track of when to do next move
        //int numOfGames = 1;
        
        for (int i = 0; i < n; i++) {
        	if ((i%4) == 0) {
        		players[i] = PTypes.titForTat;
        		checker[i] = 0;
        	}
        	else if ((i%4) == 1) {
        		players[i] = PTypes.grudger;
        		checker[i] = 0;
        	}
        	else if ((i % 4) == 2) {
        		players[i] = PTypes.alwaysCooperate;
        		checker[i] = 0;
        	}
        	else if ((i % 4) == 3) {
        		players[i] = PTypes.alwaysDefect;
        		checker[i] = 1;
        	}
        }
        
        
        //Now we need to assign the payoffs 
        //first we need to set up the outer loop that forces the games to go for k number of generations 
        for (int i = 1; i <= k; i++ ) {
        	//now we need to set up the player loops
        	//in order to pit each player against each player, we need to do 2 nested for loops that oterate through the whole array of players
        	for (int x = 0; x < n; x++) {
        		for (int y = 0; y < n; y++) {
        			//now we need to make sure that we aren't pitting the same players against each other but also play m games
        			if(x!=y) {
        				for (int c = 0; c < n; c++) {//reset relevant players before the each game
        					if ((c%4) == 0) { // need to reset the checker on the titFortat players
        		        		checker[c] = 0;
        		        	}
        		        	else if ((c%4) == 1) {// need to reset the checker on the grudger players 
        		        		checker[c] = 0;
        		        	}
        				}
        				for(int numOfGames = 1; numOfGames <=m; numOfGames++) {
        				//now we need to assign payoffs based on the players that are playing each other and the chart given 
        					if(players[x] == PTypes.alwaysDefect && players[y] == PTypes.alwaysDefect) {
	        					playerPayoff[x] = playerPayoff[x] + 1;
	        					playerPayoff[y] = playerPayoff[y] + 1;
	        				}
	        				else if(((players[x] == PTypes.alwaysCooperate) || (players[x] == PTypes.grudger) || (players[x] == PTypes.titForTat)) && players[y] == PTypes.alwaysDefect) {//one cooperates but one defects
	        					playerPayoff[y] = playerPayoff[y] + 5;
	        				}
	        				else if(players[x] == PTypes.alwaysDefect && ((players[y] == PTypes.alwaysCooperate) || (players[y] == PTypes.grudger) || (players[y] == PTypes.titForTat) )) {//one cooperates and one defects
	        					playerPayoff[x] = playerPayoff[x] + 5;
	        				}
	        				
	        				else if(((players[x] == PTypes.alwaysCooperate) || (players[x] == PTypes.grudger) || (players[x] == PTypes.titForTat)) && ((players[y] == PTypes.alwaysCooperate) || (players[y] == PTypes.grudger) || (players[y] == PTypes.titForTat))) { // this is for the case that both cooperate 
	        					playerPayoff[x] = playerPayoff[x] + 3;
	        					playerPayoff[y] = playerPayoff[y] + 3;
	        				}
	        				
	        				//now we need to set the players next action by setting the checker array respective to their index
	        				
	        				//• Tit-for-Tat (T4T) players, who start by cooperating and play the opponent’s last action in subsequent rounds
	        				if (players[x]== PTypes.titForTat) {
	        					checker[x] = checker[y];
	        					
	        				}
	        				if (players[y] == PTypes.titForTat) {
	        					checker[y] = checker[x];
	        				}
	        				//• Grudger (G) players, who cooperate until the opponent defects, after which it only defects.
	        				if (players[x]==PTypes.grudger) {
	        					if (checker[y] == 1) {
	        						checker[x] = 1;
	        					}
	        				}
	        				if (players[y]==PTypes.grudger) {
	        					if (checker[x] == 1) {
	        						checker[y] = 1;
	        					}
	        				}
	        				
        				}
        				
        				
        			}
        			
        		}
        		
        	}
        	
        	
            int[] sortedPayoffs = new int[n];
            for (int z = 0; z < n; z++) {
            	sortedPayoffs[z] = playerPayoff[z];
            }
            ArrayList<Pair<PTypes, Integer>> mySortedMap = new ArrayList(n);
			for(int z = 0; z < n; z++) {
				mySortedMap.add(new Pair<PTypes, Integer>(players[z],sortedPayoffs[z]));
			}
			Comparator<Pair<PTypes, Integer>> c = (comparing(Pair::getValue));
			Collections.sort(mySortedMap, c);
           
            //System.out.println(mySortedMap);
            
            /**But also need to make sure to sort the player array with it so I am going to create a map to hold everything 
            Map<PTypes, Integer> mySortedMap = new HashMap<PTypes, Integer>();
            for (int j = 0; j < n; j++) {
            	mySortedMap.put((players[j]), Integer.valueOf(sortedPayoffs[j]));
            	System.out.println(mySortedMap);
            }**/
            
            // now I will sort the payoff array
			//Arrays.sort(sortedPayoffs);
            
            //now update the map
            //System.out.println(sorted);
            
            for(int b = 0; b < n; b++) {
            	//players[b] = mySortedMap.get(sortedPayoffs[b]);
            	//System.out.println(players[b] + " ");
            }
            double T4Tsum = 0;
            double Tcheck = 0;
            double  Gsum = 0;
            double Gcheck = 0;
            double ACsum = 0;
            double ACcheck = 0;
            double ADsum = 0;
            double ADcheck = 0;
            double totalsum = 0;
            //System.out.println(Arrays.toString(players));
            //now we need to get the sum of all the players 
            for (int id = 0; id < n; id++) {
            	
        		if (players[id] == PTypes.titForTat) {
            		T4Tsum = T4Tsum + sortedPayoffs[id];
            		Tcheck = Tcheck + 1;
            	}
            	else if (players[id] == PTypes.grudger) {
            		Gsum = Gsum + sortedPayoffs[id];
            		Gcheck = Gcheck + 1;
            	}
            	else if (players[id] == PTypes.alwaysCooperate) {
            		ACsum = ACsum + sortedPayoffs[id];
            		ACcheck = ACcheck + 1;
            	}
            	else if (players[id] == PTypes.alwaysDefect) {
            		ADsum = ADsum + sortedPayoffs[id];
            		ADcheck = ADcheck + 1;
            	}
        	}
        	
        	 totalsum = T4Tsum + Gsum + ADsum + ACsum;
        	 System.out.println("Generation: " + i );
             System.out.println("Sum Payoff =  T4T: " + T4Tsum + " G: " + Gsum + " AC: " + ACsum + " AD: " + ADsum + " Total: " + totalsum);
             System.out.println("Percentage = T4T: " + ((double)(Tcheck/n))*100 + "% G: " + ((double)(Gcheck/n))*100 + "% AC: " + ((double)(ACcheck/n))*100 + "% AD: " + ((double)(ADcheck/n))*100 + "%");
             System.out.println("Average Payoff = T4T: " + ((double)T4Tsum/n) + " G: " + ((double)Gsum/n) + " AC: " + ((double)ACsum/n) + " AD: " + ((double)ADsum/n));
             System.out.println(" -------------------------------------------- ");
             
             T4Tsum = 0;
             Tcheck = 0;
             Gsum = 0;
             Gcheck = 0;
             ACsum = 0;
             ACcheck = 0;
             ADsum = 0;
             ADcheck = 0;
             totalsum = 0;
             //now remove the p percent 
             
             //p = p + 2; //varying p for question 3
             
             double pNum = Math.round((p/100) * n);
             //PTypes[] temp = new PTypes[(int) pNum];
             int pNumCheck = 0;
             
             for (int per = 0; per < n; per++) {
            	 if (per < pNum) {
            		mySortedMap.remove(players[per]);
            	 }
            	 
            	 if (per > (n - pNum)) {
            		 players[per] = mySortedMap.get(per).getKey();
            		 //System.out.println(players[per]);
            		 pNumCheck = pNumCheck + 1;
            	 }
             }
             
           //need to reset the payoff array
           for (int r = 0; r < n; r++) {
        	   playerPayoff[r] = 0;
        	   sortedPayoffs[r] = 0;
           }
        }
        
	}

}
