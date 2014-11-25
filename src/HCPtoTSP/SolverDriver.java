package HCPtoTSP;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class SolverDriver
{
	public static void main(String[] args) {
		solveHCP();
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

	public static void solveHCP()
	{
		//Parse file
		Parser p = new Parser();
		int[][] edges = p.parseHCP();
		
		try {
			new HCP2TSPWriter(p.file, edges.length, edges);
		} catch (IOException e) {
			System.err.println("File error");
			e.printStackTrace();
		}
		
		p.parseHCPout();
	}
}
