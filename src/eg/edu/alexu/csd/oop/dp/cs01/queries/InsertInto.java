package eg.edu.alexu.csd.oop.dp.cs01.queries;

import java.util.ArrayList;

public class InsertInto extends OurQuery{

	private ArrayList<String> columnNames;
	
	private ArrayList<String> values;
	
	public InsertInto(String tableName, ArrayList<String> values) {
		columnNames = null;
		this.values = new ArrayList<>(values);
		
	}
	
	public InsertInto(String tableName, ArrayList<String> columnNames, ArrayList<String> values) {
		this.columnNames = new ArrayList<>(columnNames);
		this.values = new ArrayList<>(values);
	}
	
	@Override
	public boolean execute() {
		if (columnNames == null) {
			//insert into all the row of the table checking if columns exist with those names.
			
		} else if (values != null && columnNames != null) {
			//insert values in the columns listed above only checking if columns exist with those names.
			
		}
		return false;
	}
	
	
}
