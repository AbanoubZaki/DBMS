package eg.edu.alexu.csd.oop.db.cs01.modules;

import java.util.ArrayList;

public class Row {

	private ArrayList<Cell>cells;
	
	public Row() {
	}
	public Row(ArrayList<Cell>cells) {
		this.cells = cells;
	}

	/**
	 * @return the cells
	 */
	public ArrayList<Cell> getCells() {
		return cells;
	}

	/**
	 * @param cells the cells to set
	 */
	public void setCells(ArrayList<Cell> cells) {
		this.cells = cells;
	}
	
	public void updateCell(int index , Cell c ) {
		cells.add(index, c);
	}
	public void deleteCell(int index) {
		updateCell(index, null);
	}
}
