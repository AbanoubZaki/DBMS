package eg.edu.alexu.csd.oop.db.cs01.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
@XmlRootElement
public class Table {
	
	private String databaseName;
	
	private String tableName;
		
	private ArrayList<String> columnNames;
	
	private Map<String, String> columnTypes;
	
	private ArrayList<Row> tableRows;
	
	private boolean isRead;
	
	public Table() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * for dropping a table.
	 * @param databaseName
	 * @param tableName
	 */
	public Table(String databaseName, String tableName) {
		this.databaseName = databaseName;
		this.tableName = tableName;
		columnNames = new ArrayList<>();
		columnTypes = new HashMap<String, String>();
		tableRows = new ArrayList<>();
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
		this.databaseName = databaseName;
		this.tableName = tableName;
		this.columnNames = columnNames;
		this.columnTypes = new HashMap<String, String>();
		for(int i=0;i<columnNames.size();i++) {
			this.columnTypes.put(columnNames.get(i), columnTypes.get(i));
		}
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
		this.columnNames = columnNames;
	}
	
	public ArrayList<String> getColumnNames() {
		return columnNames;
	}
	
	public void setColumnTypes(Map<String, String> columnTypes) {
		this.columnTypes = columnTypes;
	}
	
	public Map<String, String> getColumnTypes() {
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
	
	public Object[][] getData() {
		FileManager.getInstance().readTable(this);
		ArrayList< ArrayList<Object> > data = new ArrayList<>();
		for (int i = 0; i < getRows().size(); i++) {
			//row of data to be filled with objects.
			ArrayList<Object> dataRow = new ArrayList<>();
			Row r = getRows().get(i);
			for (int j = 0; j <getColumnNames().size() ; j++) {
				if (getColumnTypes().get(getColumnNames().get(j)).equals("int")) {
					dataRow.add((Integer.parseInt(r.getCells().get(getColumnNames().get(j)).getValue())));
				} else if (getColumnTypes().get(getColumnNames().get(j)).equals("varchar")) {
					dataRow.add(r.getCells().get(getColumnNames().get(j)).getValue());
				}
			}
			data.add(dataRow);
		}
		return (Object[][]) data.toArray();
	}
}
