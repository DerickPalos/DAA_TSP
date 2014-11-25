package nearestNeighbor;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Parser {
	private Scanner in = new Scanner(System.in);
	ArrayList<Double[]> out;
	String file;

	/**
	 * Default constructor
	 * 
	 * Creates ArrayList prepared to receive coordinates
	 */
	public Parser() {
		this.file = "";
		this.out = new ArrayList<Double[]>();
	}
	
	/**
	 * Reads data from user-entered filename
	 */
	public void parse() {
		boolean success = false;
		
		// Keep trying if file not found
		while (!success) {
			System.out.print("Enter filename (or 000 to exit): ");
			this.file = in.nextLine();

			if(file.trim().equals("000"))
			{
				System.out.println("Program Terminated");
				System.exit(0);
			}
			try {
				// Input file
				in = new Scanner(new File("src/TSPFiles/" + this.file + ".tsp"));

				success = true;
				String line = in.nextLine();
				// Skip to Coordinate Data
				while (!(line.equals("NODE_COORD_SECTION"))) {
					line = in.nextLine();
				}

				// Begin reading Coordinate Data
				while (!(line.equals("EOF"))) {
					line = in.nextLine();
					if (line.equals("EOF"))
						break;
					//Split into pieces and store
					String[] parts = line.split(" ");
					Double[] pair = { Double.parseDouble(parts[1]),
							Double.parseDouble(parts[2]) };
					out.add(pair);
				}
			} catch (IOException e) {
				System.err.println("Caught IOException: " + e.getMessage());
				System.out.println();
			}
		}
	}
}
