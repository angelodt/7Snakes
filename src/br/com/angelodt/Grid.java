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
	private Map<Integer, List<Snake>> snkList;
	private boolean twoSnakesFound;
	private final int TOP_LEFT = -1;
	private final int BOTTON_RIGHT = +1;
	private int[][]grid;
	private boolean debug;
	private List<Snake> snkListTemp;

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
		snkListTemp = new ArrayList<>();
		debug = true;
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
			System.out.println("Snake found:"+s.getSum());
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

	/**
	 * Recursive function that find a next cell to add snake body.
	 * A valid cell is that one dosen't exist in the snake body.
	 * The order of search is right, botton, left, top.
	 * The next cell is searched if the cell found is not ok. 
	 */	/*
	private Snake findNext(Snake s, Cell current) {
		if(debug && current != null) {
			System.out.println(current.toString());
		}
		if(s.isFull() || current == null) { 
			return s; 
		}
		Cell next = getRightCell(current);
		if( next == null || !s.addCell(next)) {
			next = getBottonCell(current);
			if( next == null || !s.addCell(next)) {
				next = getLeftCell(current);
				if( next == null || !s.addCell(next)) {
					next = getTopCell(current);
					s.addCell(next);
				}
			}
		}
		return findNext(s,next);
	}

	public boolean findSnakes() {
		Snake s = new Snake();
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid.length; j++) {
				Cell c = getCell(i,j);
				if(s.addCell(c)) {
					s = findNext(s,c);
					if(s.isFull()) {
						addSnake(s);
						s = new Snake();
					}
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
	}*/

	public boolean findSnakes() {

		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid.length; j++) {
				Snake s = new Snake();
				addSnake(createSnakes(s, getCell(i,j)));
				if(twoSnakesFound) {
					break;
				}
			}
			if(twoSnakesFound) {
				break;
			}
		}
		return twoSnakesFound;
	}
	
	private Snake createSnakes(Snake s, Cell current) {
		if(debug && current != null) {
			System.out.println(current.toString());
		}
		s.addCell(current);
		if(s.isFull()) {
			return s;
		}			
		List<Cell> cAdj = findAdjacentCell(current);
		for (Cell cell : cAdj) {
			Snake sNew = new Snake();
			sNew.setBody(s.getBody());
			if(sNew.addCell(cell)) {
				sNew = createSnakes(sNew, cell);
			}
			if(sNew.isFull()) {
				return sNew;
			}
		}
		return s;
	}

	private List<Cell> findAdjacentCell(Cell current) {
		List<Cell> cAdj = new ArrayList<>();

		Cell next = getRightCell(current);
		if( next != null) {
			cAdj.add(next);
		}
		next = getBottonCell(current);
		if( next != null) {
			cAdj.add(next);
		}
		next = getLeftCell(current);
		if( next != null) {
			cAdj.add(next);
		}
		next = getTopCell(current);
		if( next != null) {
			cAdj.add(next);
		}
		return cAdj;
	}
	private void addSnake_2(Snake s) {
		if(s.isFull()) {
			addSnake(s);
		} else {
			snkListTemp.add(s);
		}
	}
}
