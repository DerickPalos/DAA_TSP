import java.io.*;
/**
 * This class takes the data from an HCP problem and converts
 * it to TSP file format.  HCP2TSPWrtier needs the name of the 
 * file you're writing to, the dimension of the problem, and a 
 * 2D array.  The 2D array should have 1s where there's an edge, 
 * and either null or 2 where there isn't to get the proper output
 */
public class HCP2TSPWriter
{
    private int[][] graph;
    private String path;

    /**
     * Constructor for objects of class TSPWriter. 
     * 
     * @param filePath:  the name of the file to be created
     * @param dimension:  the number of nodes to be written
     * @param graph:  a 2D array storing all data to be written
     */
    public HCP2TSPWriter(String filePath, int dimension, int[][] graph) throws IOException
    {
        this.graph = graph;
        path = filePath.concat(".tsp");
        System.out.println();
        createTSPFile(dimension);
    }

    /**
     * This method writes the TSP file
     * 
     * @param dimension:  the number of nodes that will be written
     */
    
    private void createTSPFile(int dimension) throws IOException
    {
        FileWriter writer = new FileWriter(path, false);
        PrintWriter printer = new PrintWriter(writer);
        printer.printf("%s" + "%n", "NAME: " + path);
        printer.printf("%s" + "%n", "TYPE: TSP");
        printer.printf("%s" + "%n", "DIMENSION: " + dimension);
        printer.printf("%s" + "%n", "EDGE_WEIGHT_TYPE: EXPLICIT");
        printer.printf("%s" + "%n", "EDGE_WEIGHT_FORMAT: LOWER_DIAG_ROW");
        printer.printf("%s" + "%n", "EDGE_WEIGHT_SECTION");
        
        for(int i = 0; i < dimension; i++)
        {
            for(int j = 0; j <= i; j ++)
            {
                try
                {
                    printer.printf("%s" + "%n", graph[i][j]);
                }
                catch (NullPointerException e)
                {
                    printer.printf("%s" + "%n", "2");
                }
            }
        }
        
        printer.printf("%s" + "%n", "EOF");
        printer.flush();
        printer.close();
    }
}