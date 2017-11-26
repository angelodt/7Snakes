/**
 * The Seven Snake program load a grid from a CSV file and search a pair of distinct 7-Snakes.
 * To consider a pair of distinct 7-Snakes they cannot share cells and your sum values must be the same.
 * When snakes are found the program will stop the search and output the snakes cells with your sum values.
 * If no such pair exists the program output FAIL.
 */
package br.com.angelodt;

/**
 * @author angelodt
 *
 */
public class SevenSnakes {
	
	/**
	 * @param args - full file path to csv file
	 */
	public static void main(String[] args) {
		try {
			Grid g = new Grid(args[0].toString());
			if(!g.findSnakes()) {
				System.out.println("FAIL!");
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}