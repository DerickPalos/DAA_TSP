import java.util.ArrayList;
import java.util.LinkedList;

public class SolverDriver
{
	public static void main(String[] args) {
//		solveBruteForce();
		solveNearestNeighbor();
	}
	
	/**
	 * Solves TSP using the Brute Force technique
	 */
	public static void solveBruteForce()
	{
		long startTime = System.nanoTime();
		//Parse file
		Parser p = new Parser();
		p.parse();
		
		Graph g = new Graph();
		
		g.cities = p.out;
		
		//Populate array for first tour
		int[] firstPerm = new int[g.cities.size()];
		for(int i = 0; i < g.cities.size(); i++){
			firstPerm[i] = i + 1;
		}
		g.bestTour = new int[g.cities.size()];
		System.arraycopy(firstPerm, 0, g.bestTour, 0, firstPerm.length);
		g.bestCost = g.distance(g.bestTour);
		
		//Generate permutations
		while(g.permGen(firstPerm)){
			double d = g.distance(firstPerm);
			//Compare tour costs
			if(d < g.bestCost){
				System.arraycopy(firstPerm, 0, g.bestTour, 0, firstPerm.length);
				g.bestCost = d;
			}
		}
		
		//Console output
		for(int i = 0; i < g.bestTour.length; i++)
			System.out.print(g.bestTour[i] + " ");
		System.out.print("\n");
		System.out.println(g.bestCost);
		long endTime = System.nanoTime();
		System.out.println((endTime - startTime) + "ns");
	}

	/**
	 * Solves TSP using the Nearest Neighbor technique
	 */
	public static void solveNearestNeighbor()
	{
		long startTime = System.nanoTime();
		//Parse file
		Parser p = new Parser();
		p.parse();
		
		Graph g = new Graph();
		
		g.cities = p.out;
		g.linkCities = new LinkedList<Double[]>();
		
		g.bestTour = null;
		g.bestCost = Integer.MAX_VALUE;
		
		for(int i = 0; i < g.cities.size(); i++){
			for(int j = 0; j < g.cities.size(); j++)
			{
				g.linkCities.add(g.cities.get(j));
			}
			g.linkCities.remove(g.cities.get(i));
			double cost = g.tourCalc(g.cities.get(i), g.linkCities);
			if(cost < g.bestCost)
				g.bestCost = cost;
			g.linkCities.add(g.cities.get(i));
		}
		System.out.println(g.bestCost);
	}
}
