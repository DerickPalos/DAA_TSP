package workingSoFar;


public class Tour {
	public String tour;
	public double cost;
	
	/**
	 * Default constructor
	 */
	public Tour(){
		
	}
	
	/**
	 * Constructor
	 * @param t tour
	 * @param c cost
	 */
	private Tour(String t, double c){
		this.tour = t;
		this.cost = c;
	}
	
	//Needs editing
	/**
	 * Add tour
	 * @param t tour
	 * @return new tour
	 */
	public Tour add(Tour t){
		Tour n = new Tour(this.tour + "," + t.tour, this.cost + t.cost);
		return n;
	}
	
	/**
	 * toString to return tour and cost
	 */
	public String toString(){
		return tour + " " + cost;
	}
}
