package HCPtoTSP;

import java.util.*;
import java.lang.Math;

public class Graph {

	public ArrayList<Double[]> cities;
	public double bestCost;
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
		double x = p2[1] - p1[1];
		double y = p2[2] - p1[2];
		x = Math.pow(x, 2.0) + Math.pow(y, 2.0);
		return Math.sqrt(x);		
	}
}