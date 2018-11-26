package eg.edu.alexu.csd.oop.db.cs01.modules;

import java.util.ArrayList;

public class DataBase {

	private String dataBaseName;
	
	private ArrayList<Table>tables;
	
	public DataBase(String dataBaseName){
		this.dataBaseName = dataBaseName;
		tables = new ArrayList<Table>();
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return dataBaseName;
	}
	
	/**
	 * @return the tables
	 */
	public ArrayList<Table> getTables() {
		return tables;
	}
	
	/**
	 * @param tables the tables to set
	 */
	public void setTables(ArrayList<Table> tables) {
		this.tables = tables;
	}
	
	public Table getTable(String nameTable) {
		for(Table T:tables) {
			if(T.getTableName().equals(nameTable))
				return T;
		}
		return null;
	}
}
