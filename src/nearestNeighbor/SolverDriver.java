package nearestNeighbor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

public class SolverDriver
{
	public static void main(String[] args) {
		solveNearestNeighbor();
	}

	/**
	 * Solves TSP using the Nearest Neighbor technique
	 */
	public static void solveNearestNeighbor()
	{		
		//Parse file
		Parser p = new Parser();
		p.parse();
		
		long startTime = System.nanoTime();
		
		Graph g = new Graph();
		
		//All cities
		g.cities = p.out;
		
		//Copy of cities
		LinkedList<Double[]> linkCities = new LinkedList<Double[]>();
		
		g.bestTour = null;
		g.bestCost = Integer.MAX_VALUE;
		
		for(int i = 0; i < g.cities.size(); i++){
			
			//Add all cities to copy
			for(int j = 0; j < g.cities.size(); j++)
				linkCities.add(g.cities.get(j));
			
			//Save and remove starting city
			Double[] start = g.cities.get(i);
			linkCities.remove(start);
			
			//Calculate tour cost
			Tour t1 = g.tourCalc(start, linkCities, start);
			//Compare to best cost found
			if(t1.cost < g.bestCost){
				g.bestCost = t1.cost;
				g.bestTourString = t1.tour;
			}
		}
		
		// By nature, starting city was at the end of bestTourString
		// This places the starting city at the beginning instead
		String lastNum = g.bestTourString.substring(g.bestTourString.lastIndexOf(","));
		g.bestTourString = (lastNum + "," + g.bestTourString.substring(0, g.bestTourString.length()-lastNum.length())).substring(1);
		System.out.println(g.bestCost);
		System.out.println(g.bestTourString);
		
		long endTime = System.nanoTime();
		System.out.println(endTime - startTime);
		
		try{
			generateTourFile(p, g, endTime-startTime);
		}
		catch(FileNotFoundException FNFE)
		{
			System.err.println("Caught FileNotFoundException: " + FNFE.getMessage());
			System.out.println();
		}
		catch(UnsupportedEncodingException UEE)
		{
			System.err.println("Caught UnsupportedEncodingException: " + UEE.getMessage());
			System.out.println();
		}
		

	}

	/**
	 * Generates tour file
	 * @param p Parser for file information
	 * @param g Graph for tour information
	 * @param time Measured time
	 * @throws FileNotFoundException deals with output
	 * @throws UnsupportedEncodingException deals with output
	 */
	public static void generateTourFile(Parser p, Graph g, long time) throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer = new PrintWriter("src/TourFiles/" + p.file + ".tour", "UTF-8");
		writer.println("NAME: " + p.file + ".tour");
		writer.println("TYPE: TOUR");
		writer.println("COMMENT: Cost = " + g.bestCost + " Time = " + time/1000000.0 + " ms");
		writer.println("DIMENSION: " + g.cities.size());
		writer.println("TOUR_SECTION");
		for(int i = 0; i < g.bestTourString.length(); i++)
		{
			if(!(g.bestTourString.charAt(i) == ','))
				writer.print(g.bestTourString.charAt(i));
			else
				writer.println();
		}
		writer.println();
		writer.print("-1");
		writer.close();
	}

}
