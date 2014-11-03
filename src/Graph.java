import java.util.*;
import java.lang.Math;

public class Graph {

	public ArrayList<Double[]> cities;
	public LinkedList<Double[]> linkCities;
	public double bestCost;
	public int[] bestTour;

	/**
	 * Default constructor
	 */
	public Graph()
	{
		
	}
	
	/**
	 * Permutation generating algorithm 
	 * Pulled from
	 * http://nayuki.eigenstate.org/page/next-lexicographical-permutation-algorithm
	 */
	public boolean permGen(int[] array) {
	    // Find longest non-increasing suffix
	    int i = array.length - 1;
	    while (i > 0 && array[i - 1] >= array[i])
	        i--;
	    // Now i is the head index of the suffix
	    
	    // Are we at the last permutation already?
	    if (i <= 0)
	        return false;
	    
	    // Let array[i - 1] be the pivot
	    // Find rightmost element that exceeds the pivot
	    int j = array.length - 1;
	    while (array[j] <= array[i - 1])
	        j--;
	    // Now the value array[j] will become the new pivot
	    // Assertion: j >= i
	    
	    // Swap the pivot with j
	    int temp = array[i - 1];
	    array[i - 1] = array[j];
	    array[j] = temp;
	    
	    // Reverse the suffix
	    j = array.length - 1;
	    while (i < j) {
	        temp = array[i];
	        array[i] = array[j];
	        array[j] = temp;
	        i++;
	        j--;
	    }
	    
	    // Successfully computed the next permutation
	    return true;
	}
	
	/**
	 * Calculate the distance of a tour
	 * @param tour - Numbers of the cities
	 * @return The total distance traveled when on tour
	 */
	public double distance(int[] tour){
		double sum = 0.0;
		for(int i = 0; i < cities.size(); i++){
			sum += distanceFormula(cities.get(tour[i] - 1), cities.get(tour[((i + 1) % cities.size())] - 1));
		}
		return sum;
	}
	
	/**
	 * Applies the distance formula between 2 arbitrary cities
	 * @param p1 - City A
	 * @param p2 - City B
	 * @return Distance between City A and City B
	 */
	private double distanceFormula(Double[] p1, Double[] p2) {
		double x = p2[0] - p1[0];
		double y = p2[1] - p1[1];
		x = Math.pow(x, 2.0) + Math.pow(y, 2.0);
		return Math.sqrt(x);		
	}

	/**
	 * Calculates total tour cost
	 * @param currCity 
	 * @param unusedCities
	 * @return
	 */
	public double tourCalc(Double[] currCity, LinkedList<Double[]> unusedCities)
	{
		if(unusedCities.size() == 1){
			return distanceFormula(currCity, unusedCities.get(0));
		}
		double shortestDistance = distanceFormula(currCity, unusedCities.get(0));
		Double[] closestCity = unusedCities.get(0);
		for(Double[] c : unusedCities){
			double currDist = distanceFormula(currCity, c);
			if(currDist < shortestDistance){
				shortestDistance = currDist;
				closestCity = c;
			}
		}
		unusedCities.remove(closestCity);
		return shortestDistance + tourCalc(closestCity, unusedCities);
	}
}