package eg.edu.alexu.csd.oop.db.cs01.modules;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Table {
	
	private String databaseName;
	
	private String tableName;
		
	private ArrayList<String> columnNames;
	
	private ArrayList<String> columnTypes;
	
	private ArrayList<Row> tableRows;
	
	private boolean isRead;
	
	/**
	 * for dropping a table.
	 * @param databaseName
	 * @param tableName
	 */
	public Table(String databaseName, String tableName) {
		this.databaseName = new String(databaseName);
		this.tableName = new String(tableName);
		columnNames = null;
		columnTypes = null;
		tableRows = null;
		isRead = false;
	}
	
	/**
	 * for creating new table.
	 * @param databaseName
	 * @param tableName
	 * @param columnNames
	 * @param columnTypes
	 */
	public Table(String databaseName, String tableName, ArrayList<String> columnNames, ArrayList<String> columnTypes) {
		this.databaseName = new String(databaseName);
		this.tableName = new String(tableName);
		columnNames = new ArrayList<>(columnNames);
		columnTypes = new ArrayList<>(columnTypes);
		tableRows = new ArrayList<>();
		isRead = false;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	
	public String getDatabaseName() {
		return databaseName;
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
	
	public void setColumnTypes(ArrayList<String> columnTypes) {
		this.columnTypes = new ArrayList<>(columnTypes);
	}
	
	public ArrayList<String> getColumnTypes() {
		return columnTypes;
	}
	
	public void addRow(Row row) {
		tableRows.add(row);
	}
	
	public Row getRow(int index) {
		return tableRows.get(index);
	}
	
	/**
	 * @return the rows
	 */
	public ArrayList<Row> getRows() {
		return tableRows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(ArrayList<Row> rows) {
		this.tableRows = rows;
	}

	public void setIsRead (boolean isRead) {
		this.isRead = isRead;
	}
	
	public boolean getIsRead () {
		return isRead;
	}
}
