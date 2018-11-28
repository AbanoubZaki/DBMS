package eg.edu.alexu.csd.oop.dp.cs01.queries;

import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs01.condition.RelationalCondition;
import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Cell;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class UpdateSet extends OurQuery {

	private ArrayList<String> columnNames;

	private ArrayList<String> values;
	
	
	public UpdateSet(Table table, ArrayList<String> columnNames, ArrayList<String> values, RelationalCondition condition) {
		this.columnNames = columnNames;
		this.values = values;
		setTable(table);
		setCondition(condition);
	}
	
	@Override
	public int execute2() {
		FileManager.getInstance().readTable(getTable());
		for(int i = 0; i < getTable().getColumnNamesToLowerCase().size(); i++) {
			if(!getTable().getColumnNamesToLowerCase().contains(columnNames.get(i).toLowerCase())) {
				System.out.println("Column names not found.");
				return 0;
			}	
		}
		
		int effectedRows = 0;
		
		for (int k = 0; k < columnNames.size(); k++) {
			int index = -1;
			if(getCondition() == null) {
				for (int j = 0; j < getTable().getColumnNamesToLowerCase().size(); j++) {
					if(columnNames.get(k).equals(getTable().getColumnNamesToLowerCase().get(j).toLowerCase())) {
						index = j;
						break;
					}
				}
				for (int i = 0; i < getTable().getRows().size(); i++) {
						getTable().getRows().get(i).updateCell(getTable().getColumnNamesToLowerCase().get(index), new Cell(values.get(k)));
						effectedRows++;
				}
			}else {
				
			}
		}
		
		return effectedRows;
	}

}
