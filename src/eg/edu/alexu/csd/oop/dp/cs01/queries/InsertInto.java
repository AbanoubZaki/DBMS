package eg.edu.alexu.csd.oop.dp.cs01.queries;

import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class InsertInto extends OurQuery{

	ArrayList<String> columnNames;
	
	ArrayList<String> values;
	
	public InsertInto(Table table, ArrayList<String> columnNames, ArrayList<String> values) {
		this.setTableName(table.getTableName());
		
		
	}
	
	@Override
	public boolean execute() {
		
		return false;
	}
	
	
}
