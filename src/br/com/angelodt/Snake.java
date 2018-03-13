package br.com.angelodt;

import java.util.ArrayList;
import java.util.List;
/**
 * Class that represent the snake.
 * A snake has a body with 7 cells maximum.
 * 
 * @author angelodt
 *
 */
public class Snake {
	private List<Cell> body;

	public Snake() {
		this.body = new ArrayList<Cell>();
	}
	
	public boolean addCell(Cell c) {
		if(c != null && !isFull() && this.body.indexOf(c)==-1) {
			this.body.add(c);
			return true;
		}
		return false;
	}
	
	public boolean isFull() {
		return body.size()>6;
	}
	
	public List<Cell> getBody() {
		return body;
	}
	
	public void setBody(List<Cell> body) {
		this.body = body;
	}

	public Cell getLastCell() {
		return body.get(body.size()-1);
	}
	
	public int getSum() {
		return body.stream().mapToInt(c -> c.getValue()).sum();
	}
	
	/**
	 * 7-Snakes are distinct when they do not share grid cells.
	 * @param other
	 * @return Snake is different or not
	 */
    public boolean different(Snake other) {
		boolean result = true;
        for (Cell cell : other.body) {
        	if(this.body.indexOf(cell) > -1) {
        		result = false;
        		break;
        	}
		}        
        return result;
    }
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Snake sum: ").append(getSum()).append(" cells:").append("\r\n");
		for (Cell cell : body) {
			sb.append(cell.toString()).append("\r\n");
		}
		return sb.toString();
	}
}
