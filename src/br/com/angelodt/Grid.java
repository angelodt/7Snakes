package br.com.angelodt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author angelodt
 * Represent the Grid of cells imported from CSV file. 
 */
public class Grid {
	private static Map<Integer, List<Snake>> snkList;
	private boolean twoSnakesFound;
	private final int TOP_LEFT = -1;
	private final int BOTTON_RIGHT = +1;
	private int[][]grid;
	private boolean debug = false;
	/*	 = {
	{227,191,234,67,43,13,48,211,253,243},
	{36,95,229,209,49,230,46,16,190,49},
	{206,130,85,67,104,93,128,243,38,173},
	{234,82,191,153,170,99,124,60,12,31},
	{192,9,24,127,183,201,139,21,244,66},
	{93,200,66,16,189,42,209,113,215,4},
	{182,141,153,64,229,55,115,139,12,187},
	{133,241,35,255,126,39,110,147,24,241},
	{2,202,191,159,223,128,154,109,6,200},
	{173,44,163,196,159,232,135,159,117,175}
};


/*		
private int[][]grid = {{1,2,3,4},
	               {5,6,7,8},
	               {9,10,11,12},
	               {13,14,15,16}
	              };

private int[][]grid = {{1,1,1,1},
    {1,1,1,1},
    {1,1,1,1},
    {1,1,1,1}
   };
	 */

	public Grid(int[][]grid) {
		this.grid = grid;
	}

	/**
	 * Create a grid from CSV file.
	 * @param filePath
	 * @throws Exception 
	 */
	@SuppressWarnings("resource")
	public Grid(String filePath) throws Exception {
		try {
			File csvFile = new File(filePath);
			Scanner sc = new Scanner(csvFile);
			int lineCount = 0;
			while(sc.hasNext()) {
				String[] lineValues = sc.nextLine().split(",");
				if(grid == null) {
					grid = new int[lineValues.length][lineValues.length];
				}
				int colCount = 0;
				for (String s : lineValues) {
					int value = Integer.parseInt(s);
					if(value > 0 && value <= 256) {
						grid[lineCount][colCount] = value;
						colCount ++;
					} else {
						throw new Exception("Invalid range value between 1 and 256.");
					}
				}
				lineCount ++;
			}
		} catch (Exception e) {
			grid = null;
			throw new Exception("ERROR LOADING FILE: "+e.getMessage());
		}
		twoSnakesFound = false;
		snkList = new HashMap<>();
		//debug = true;
		System.out.print("FILE LOADED.\r\n");
	}

	private Cell getCell(int i, int j) {
		Cell c = null;
		if(i >= 0 && i < grid.length && j >= 0 && j < grid.length) {
			c = new Cell(i, j, grid[i][j]);
		}
		return c;
	}

	private Cell getTopCell(Cell cOrigin) {
		return getCell(cOrigin.getLine() + TOP_LEFT, cOrigin.getCol());
	}

	private Cell getLeftCell(Cell cOrigin) {
		return getCell(cOrigin.getLine(), cOrigin.getCol() + TOP_LEFT);
	}	

	private Cell getBottonCell(Cell cOrigin) {
		return getCell(cOrigin.getLine() + BOTTON_RIGHT, cOrigin.getCol());
	}

	private Cell getRightCell(Cell cOrigin) {
		return getCell(cOrigin.getLine(), cOrigin.getCol() + BOTTON_RIGHT);
	}
	
	private void finish(Snake s, Snake found) {
		twoSnakesFound = true;
		System.out.println("TWO SNAKES FOUND!");
		System.out.println(s.toString());
		System.out.println(found.toString());
	}
	
	private void addSnake(Snake s) {
		if(debug) {
			System.out.println("Snake found:"+s.toString());
		}
		List<Snake> found = snkList.get(s.getSum());
		if(found==null) {
			found = new ArrayList<>();
		}
		for (Snake snake : found) {
			if(s.different(snake)) {
				finish(s,snake);				
			}
		}
		found.add(s);
		snkList.put(s.getSum(),found);
	}

	public boolean findSnakes() {
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid.length; j++) {
				Snake s = new Snake();
				Cell c = getCell(i,j);
				if(s.addCell(c)) {
					addSnake(findNext(s,c));
					if(twoSnakesFound) {
						break;
					}
				}
			}
			if(twoSnakesFound) {
				break;
			}
		}
		return twoSnakesFound;
	}

	private Snake findNext(Snake s, Cell actual) {
		if(debug) {
			System.out.println(actual.toString());
		}
		if(s.isFull()) { return s; }
		Cell next = getRightCell(actual);
		if(next != null) {
			if(s.addCell(next)) {
				return findNext(s,next);
			}
		}
		if(s.isFull()) { return s; }
		next = getBottonCell(actual);
		if(next != null) {
			if(s.addCell(next)) {
				return findNext(s,next);
			}
		}
		if(s.isFull()) { return s; }
		next = getLeftCell(actual);
		if(next != null) {
			if(s.addCell(next)) {
				return findNext(s,next);
			}
		}
		if(s.isFull()) { return s; }
		next = getTopCell(actual);
		if(next != null) {
			if(s.addCell(next)) {
				return findNext(s,next);
			}
		}
		return s;
	}
}
