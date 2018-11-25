package eg.edu.alexu.csd.oop.db.cs01.modules;

import java.util.ArrayList;

public class Table {
	
	private String tableName;
	
	private ArrayList<String> columnNames;
	
	private ArrayList<Row> table;
	
	public Table(String tableName) {
		tableName = new String(tableName);
		columnNames = new ArrayList<>();
		table = new ArrayList<>();
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setColumnNames(ArrayList<String> columnNames) {
		this.columnNames = new ArrayList<>(columnNames);
	}
	
	public ArrayList<String> getColumnNames() {
		return columnNames;
	}
	
	public void addRow(Row row) {
		table.add(row);
	}
	
	public Row getRow(int index) {
		return table.get(index);
	}
	
	
}
