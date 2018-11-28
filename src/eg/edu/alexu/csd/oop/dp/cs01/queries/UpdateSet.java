package eg.edu.alexu.csd.oop.dp.cs01.queries;

import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs01.Condition;
import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Cell;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class UpdateSet extends OurQuery {

	private ArrayList<String> columnNames;

	private ArrayList<String> values;
	
	
	public UpdateSet(Table table, ArrayList<String> columnNames, ArrayList<String> values, Condition condition) {
		this.columnNames = columnNames;
		this.values = values;
		setTable(table);
		setCondition(condition);
	}
	
	@Override
	public boolean execute() {
		FileManager.getInstance().readTable(getTable());
		for(int i = 0; i < getTable().getColumnNames().size(); i++) {
			if(!getTable().getColumnNames().contains(columnNames.get(i))) {
				return false;
			}	
		}
		
		int index = -1;
		for (int k = 0; k < columnNames.size(); k++) {
			if(getCondition() == null) {
				for (int j = 0; j < getTable().getColumnNames().size(); j++) {
					if(columnNames.get(index).equals(getTable().getColumnNames().get(j))) {
						index = j;
						break;
					}
				}
				for (int i = 0; i < getTable().getRows().size(); i++) {
						getTable().getRows().get(index).updateCell(columnNames.get(k), new Cell(values.get(k)));
				}
			}else {
				
				
				
			}
		
		}
		
		
		return true;
	}

}
