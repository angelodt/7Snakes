package br.com.angelodt;
/**
 * Class the represent grid and snake body cells, with line, column and value attributes;
 * @author angelodt
 *
 */
public class Cell {
	private int line;
	private int col; //column
	private int value;

	public Cell(int line, int col, int value) {
		this.line = line;
		this.col = col;
		this.value = value;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
        	 return false;
        Cell other = (Cell) obj;
        if (line != other.line || col != other.col || value != other.value)
            return false;
        return true;
    }

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" line X col: (");
		sb.append(line);
		sb.append(" : ");
		sb.append(col);
		sb.append(") value: ");
		sb.append(value);
		return sb.toString();
	}
}
