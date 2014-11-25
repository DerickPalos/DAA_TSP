package workingSoFar;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Parser {
	Scanner in = new Scanner(System.in);
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

			if (file.trim().equals("000")) {
				System.out.println("Program Terminated");
				System.exit(0);
			}
			try {
				// Input file
				in = new Scanner(new File("src/Inputs/" + this.file + ".tsp"));

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
					// Split into pieces and store
					String[] parts = line.split(" ");
					Double[] pair = { Double.parseDouble(parts[0]),
							Double.parseDouble(parts[1]),
							Double.parseDouble(parts[2]) };
					out.add(pair);
				}
			} catch (IOException e) {
				System.err.println("Caught IOException: " + e.getMessage());
				System.out.println();
			}
		}
	}

	/**
	 * Reads input HCP file, populates diagonals with proper value
	 * 
	 * @return 2D array of proper values
	 */
	public int[][] parseHCP() {
		boolean success = false;

		// Keep trying if file not found
		while (!success) {
			System.out.print("Enter filename (or 000 to exit): ");
			this.file = in.nextLine();

			if (file.trim().equals("000")) {
				System.out.println("Program Terminated");
				System.exit(0);
			}
			try {
				// Input file
				in = new Scanner(new File("src/Inputs/" + this.file + ".hcp"));

				success = true;
				String line = in.nextLine();

				// Skip to Dimension
				while (!(line.contains("DIMENSION"))) {
					line = in.nextLine();
				}

				String[] parts = line.split(" ");
				int dimension = Integer.parseInt(parts[2]);
				int[][] out = new int[dimension][dimension];

				for (int i = 0; i < dimension; i++)
					for (int j = 0; j < dimension; j++)
						out[i][j] = 2;

				// Skip to Edge Data
				while (!(line.equals("EDGE_DATA_SECTION"))) {
					line = in.nextLine();
				}

				// Begin reading Edge Data
				while (!(line.equals("EOF"))) {
					line = in.nextLine();
					if (line.equals("-1"))
						break;

					// Split into pieces
					parts = line.split(" ");
					out[Integer.parseInt(parts[1]) - 1][Integer
							.parseInt(parts[0]) - 1] = 1;
					out[Integer.parseInt(parts[0]) - 1][Integer
							.parseInt(parts[1]) - 1] = 1;
				}
				return out;
			} catch (IOException e) {
				System.err.println("Caught IOException: " + e.getMessage());
				System.out.println();
			} // Catch
		} // While
		return null;
	} // Parse HCP

	/**
	 * Reads filename for .txt file produced by NEOS tool
	 * Decides whether or not there is a Hamiltonian Circuit
	 */
	public void parseHCPout() {
		in = new Scanner(System.in);
		boolean success = false;

		// Keep trying if file not found
		while (!success) {
			System.out.print("Awaiting NEOS output .txt file (or 000 to exit): ");
			String NEOSfile = in.nextLine();

			if (file.trim().equals("000")) {
				System.out.println("Program Terminated");
				System.exit(0);
			}
			try {
				// Input file
				in = new Scanner(new File("src/Inputs/" + NEOSfile + ".txt"));
				success = true;
				String line = in.nextLine();
				// Skip first line
				line = in.nextLine();

				// Begin reading Edge Data
				while (!(line.equals("EOF"))) {
					line = in.nextLine();
					if (line.equals("EOF")) {
						System.out.println("Yes");
						break;
					}
					// Split into pieces and store
					String[] parts = line.split(" ");
					if (Integer.parseInt(parts[2]) == 2) {
						System.out.println("No");
						break;
					}
				}
			} catch (IOException e) {
				System.err.println("Caught IOException: " + e.getMessage());
				System.out.println();
			}
		}
	}
}