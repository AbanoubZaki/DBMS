package eg.edu.alexu.csd.oop.db.cs01.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;

@XmlRootElement
public class Table {

	//object pool pattern.
	
	private static Table table;

	private static ArrayList<Table> tables;

	private Table() {
		this.databaseName = new String();
		this.tableName = new String();
		this.currentTable = new Table();
		this.columnNamesMain = new ArrayList<>();
		this.columnNames = new ArrayList<>();
		this.columnTypes = new HashMap<String, String>();
		this.tableRows = new ArrayList<>();
	}
	
	public static Table getInstance(String tableName) {
		if (tables == null) {
			tables = new ArrayList<>();
		}
		for (Table t : tables) {
			if (t.getTableName().equals(tableName)) {
				setCurrentTable(t);
				return t;
			}
		}
		table = new Table();
		table.setTableName(tableName);
		tables.add(table);
		setCurrentTable(table);
		return table;
	}

	public Table getInstance() {
		if (tables == null) {
			tables = null;
		}
		if (getCurrentTable() == null) {
			return null;
		}
		return getCurrentTable();
	}
	
	private String databaseName;

	private String tableName;

	private static Table currentTable;

	private ArrayList<String> columnNames;

	private ArrayList<String> columnNamesMain;

	private Map<String, String> columnTypes;

	private ArrayList<Row> tableRows;

	public void setAllColumnNamesAndTypes (ArrayList<String> columnNames, ArrayList<String> columnTypes) {
		for (int i = 0; i < columnNames.size(); i++) {
			this.columnTypes.put(columnNames.get(i).toLowerCase(), columnTypes.get(i));
			this.columnNamesMain.add(columnNames.get(i));
			this.columnNames.add(columnNames.get(i).toLowerCase());
		}
	}
	
	/**
	 * @return the columnNamesMain
	 */
	public ArrayList<String> getColumnNamesMain() {
		return columnNamesMain;
	}

	/**
	 * @param columnNamesMain
	 *            the columnNamesMain to set
	 */
	public void setColumnNamesMain(ArrayList<String> columnNamesMain) {
		this.columnNamesMain = columnNamesMain;
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

	public Table getCurrentTable() {
		return currentTable;
	}

	public void setCurrentTable(Table currentTable) {
		this.currentTable = currentTable;
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
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(ArrayList<Row> rows) {
		this.tableRows = rows;
	}

	/**
	 * it returns all the data stored in the table.
	 * 
	 * @return
	 */
	public Object[][] getData() {
		FileManager.getInstance().readTable(this);
		ArrayList<ArrayList<Object>> data = new ArrayList<>();
		for (int i = 0; i < getRows().size(); i++) {
			// row of data to be filled with objects.
			ArrayList<Object> dataRow = new ArrayList<>();
			Row r = getRows().get(i);
			for (int j = 0; j < getColumnNames().size(); j++) {
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
