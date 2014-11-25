package nearestNeighbor;

import java.util.*;
import java.lang.Math;

public class Graph {

	public ArrayList<Double[]> cities;
	public double bestCost;
	public int[] bestTour;
	public String bestTourString;
	
	/**
	 * Default constructor
	 */
	public Graph()
	{
		
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
	 * @param currCity The current city we are measuring distance from
	 * @param unusedCities The list of unused cities in the tour
	 * @param origin The original starting city
	 * @return A Tour object containing the total distance of a tour and the city IDs in order
	 */
	public Tour tourCalc(Double[] currCity, LinkedList<Double[]> unusedCities, Double[] origin)
	{
		Tour t1 = new Tour();
		//Base case
		if(unusedCities.size() == 0){
			t1.cost = distanceFormula(currCity, origin);
			t1.tour = ((Integer) origin[0].intValue()).toString();
			return t1;
		}
		
		//Set comparison point for all unused cities
		double shortestDistance = distanceFormula(currCity, unusedCities.get(0));
		Double[] closestCity = unusedCities.get(0);
		
		//Compare distances from current city to all cities
		for(Double[] c : unusedCities){
			double currDist = distanceFormula(currCity, c);
			if(currDist < shortestDistance){
				shortestDistance = currDist;
				closestCity = c;
			}
		}
		
		//Remove
		unusedCities.remove(closestCity);
		
		//Initialize Tour
		t1.cost = shortestDistance;
		t1.tour = ((Integer) closestCity[0].intValue()).toString();
		
		//Recursive Call
		return t1.add(tourCalc(closestCity, unusedCities, origin));
	}

}