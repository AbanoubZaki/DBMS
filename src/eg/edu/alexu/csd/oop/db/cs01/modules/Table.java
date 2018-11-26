package eg.edu.alexu.csd.oop.db.cs01.modules;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Table {
	
	private String tableName;
	
	private String dataBaseName;
	
	private ArrayList<String> columnNames;
	
	private ArrayList<Row> rows;
	
	private boolean isRead;
	
	public Table() {
		
	}
	
	public Table(String tableName) {
		this.tableName = new String(tableName);
		columnNames = new ArrayList<>();
		rows = new ArrayList<>();
		isRead = false;
	}
	
	/**
	 * @return the dataBaseName
	 */
	public String getDataBaseName() {
		return dataBaseName;
	}

	/**
	 * @param dataBaseName the dataBaseName to set
	 */
	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
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
		rows.add(row);
	}
	
	public Row getRow(int index) {
		return rows.get(index);
	}
	
	/**
	 * @return the rows
	 */
	public ArrayList<Row> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(ArrayList<Row> rows) {
		this.rows = rows;
	}

	public void setIsRead (boolean isRead) {
		this.isRead = isRead;
	}
	
	public boolean getIsRead () {
		return isRead;
	}
}
