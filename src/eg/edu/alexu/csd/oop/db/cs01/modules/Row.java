package eg.edu.alexu.csd.oop.db.cs01.modules;

import java.util.HashMap;
import java.util.Map;

public class Row {

	private Map<String, Cell>cells;
	
	private Table table;
	
	public Row() {
	}
	public Row(Table table) {
		this.cells = new HashMap<String, Cell>();
		for(String s:table.getColumnNames()) {
			cells.put(s, null);
		}
		this.table=table;
	}

	/**
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}
	/**
	 * @param table the table to set
	 */
	public void setTable(Table table) {
		this.table = table;
	}
	/**
	 * @return the cells
	 */
	public Map<String, Cell> getCells() {
		return cells;
	}

	/**
	 * @param cells the cells to set
	 */
	public void setCells(Map<String, Cell> cells) {
		this.cells = cells;
	}
	
	public void updateCell(String columnName , Cell cell) {
		cells.put(columnName, cell);
	}
	public void deleteCell(String columnName) {
		updateCell(columnName, null);
	}
}
