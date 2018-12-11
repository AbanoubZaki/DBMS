package eg.edu.alexu.csd.oop.db.cs01.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.db.cs01.dataChecker;
import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;

public class Table {

	//object pool pattern.
	
	private static Table table;

	/**
	 * @param table the table to set
	 */
	public static void dropCurrentTable() {
		tables.remove(table);
		Table.table = new Table();
	}

	private static ArrayList<Table> tables;
	
	/**
	 * @return the tables
	 */
	public static ArrayList<Table> getTables() {
		return tables;
	}

	/**
	 * @param tables the tables to set
	 */
	public static void setTables(ArrayList<Table> tables) {
		Table.tables = tables;
	}

	private static String currentTableName;

	private String databaseName;

	private String tableName;

	private ArrayList<String> columnNamesToLowerCase;

	private ArrayList<String> columnNamesAsGiven;

	private Map<String, String> columnTypes;

	private ArrayList<Row> tableRows;
	
	private ArrayList<Row> selectedRows;
	
	private ArrayList<String>selectedColumns;
	
	/**
	 * @return the selectedColumns
	 */
	public ArrayList<String> getSelectedColumns() {
		return selectedColumns;
	}

	/**
	 * @param selectedColumns the selectedColumns to set
	 */
	public void setSelectedColumns(ArrayList<String> selectedColumns) {
		this.selectedColumns = selectedColumns;
	}

	/**
	 * @return the selectedRows
	 */
	public ArrayList<Row> getSelectedRows() {
		return selectedRows;
	}

	/**
	 * @param selectedRows the selectedRows to set
	 */
	public void setSelectedRows(ArrayList<Row> selectedRows) {
		this.selectedRows = selectedRows;
	}

	private Table() {
		this.databaseName = new String();
		this.tableName = new String();
		currentTableName = new String();
		this.columnNamesAsGiven = new ArrayList<>();
		this.columnNamesToLowerCase = new ArrayList<>();
		this.columnTypes = new HashMap<String, String>();
		this.tableRows = new ArrayList<>();
	}
	
	public static Table getInstance(String tableName , String currenDataBaseName) {
		if (tables == null) {
			tables = new ArrayList<>();
			table = null;
		}
		for (Table t : tables) {
			if (t.getTableName().equalsIgnoreCase(tableName) && t.getDatabaseName().equalsIgnoreCase(currenDataBaseName)) {
				table = t;
				currentTableName = tableName;
				return t;
			}
		}
		// to save the table before select anther one.
		if(table!=null&&!table.getColumnTypes().isEmpty()&&!table.getRows().isEmpty()) {
			FileManager.getInstance().writeTable(table);
		}else {
			tables.remove(table);
		}
		if (currenDataBaseName == null) {
			return null;
		}
		table = new Table();
		table.setTableName(tableName);
		table.setDatabaseName(currenDataBaseName);
		FileManager.getInstance().readTable(table);
		tables.add(table);
		currentTableName = tableName;
		return table;
	}

	public static Table getInstance() {
		if (tables == null) {
			return null;
		}
		if (currentTableName == null) {
			return null;
		}
		return table;
	}

	public void setAllColumnNamesAndTypes (ArrayList<String> columnNames, ArrayList<String> columnTypes) {
		this.columnNamesAsGiven = new ArrayList<String>();
		this.columnNamesToLowerCase=new ArrayList<String>();
		this.columnTypes = new HashMap<String, String>();
		for (int i = 0; i < columnNames.size(); i++) {
			this.columnTypes.put(columnNames.get(i).toLowerCase(), columnTypes.get(i).toLowerCase());
			this.columnNamesAsGiven.add(columnNames.get(i));
			this.columnNamesToLowerCase.add(columnNames.get(i).toLowerCase());
		}
	}
	
	/**
	 * @return the columnNamesAsGiven
	 */
	public ArrayList<String> getColumnNamesAsGiven() {
		return columnNamesAsGiven;
	}

	/**
	 * @param columnNamesAsGiven
	 *            the columnNamesAsGiven to set
	 */
	public void setColumnNamesAsGiven(ArrayList<String> columnNamesAsGiven) {
		this.columnNamesAsGiven = columnNamesAsGiven;
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

	public void setColumnNamesToLowerCase(ArrayList<String> columnNames) {
		this.columnNamesToLowerCase = columnNames;
	}

	public ArrayList<String> getColumnNamesToLowerCase() {
		return columnNamesToLowerCase;
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
		if(this.tableRows.size()==0)
			return null;
		Object[][]data = new Object[getRows().size()][getColumnNamesToLowerCase().size()];
		for (int i = 0; i < getRows().size(); i++) {
			// row of data to be filled with objects.
			Row r = getRows().get(i);
			for (int j = 0; j < getColumnNamesToLowerCase().size(); j++) {
				if (getColumnTypes().get(getColumnNamesToLowerCase().get(j)).equals("int")) {
					if(r.getCells().get(getColumnNamesToLowerCase().get(j)) != null) {
						if (r.getCells().get(getColumnNamesToLowerCase().get(j)).getValue() != null) {
							data[i][j]=(Integer.parseInt(r.getCells().get(getColumnNamesToLowerCase().get(j)).getValue()));
						} else {
							data[i][j]=null;
						}
					}
					else {
						data[i][j]=null;
					}
				} else if (getColumnTypes().get(getColumnNamesToLowerCase().get(j)).equals("varchar")) {
					if(r.getCells().get(getColumnNamesToLowerCase().get(j)) !=null) {
						if (r.getCells().get(getColumnNamesToLowerCase().get(j)).getValue() != null) {
							data[i][j] = r.getCells().get(getColumnNamesToLowerCase().get(j)).getValue();
						} else {
							data[i][j]=null;
						}
					}
					else {
						data[i][j]=null;
					}
				}
			}
		}
		return data;
	}
	
	public boolean checkDataTypeMatch (ArrayList<String> columnNames, ArrayList<String> values) {
		for (int i = 0; i < columnNames.size(); i++) {
			if (getColumnTypes().get(columnNames.get(i).toLowerCase()).equals("int")) {
				if (dataChecker.getInstance().checkType(values.get(i)).equals("int")) {
				} else {
					return false;
				}
			}
		}
		return true;
	}
	public static class WriteCurrentTable extends Thread {

		@Override
		public void run(){
			if(table!=null&&!table.getColumnTypes().isEmpty()) {
				FileManager.getInstance().writeTable(table);
				System.out.println("Done ;)");
			}
		}
	}

}
