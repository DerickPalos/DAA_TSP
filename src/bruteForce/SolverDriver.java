package bruteForce;

public class SolverDriver
{
	public static void main(String[] args) {
		solveBruteForce();
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
}
