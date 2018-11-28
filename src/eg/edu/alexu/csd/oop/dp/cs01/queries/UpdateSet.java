package eg.edu.alexu.csd.oop.dp.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.Condition;
import eg.edu.alexu.csd.oop.db.cs01.modules.Cell;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class UpdateSet extends OurQuery {

	private String columnName;

	private String value;
	
	
	public UpdateSet(Table table, String columnName, String value, Condition condition) {
		this.columnName = columnName;
		this.value = value;
		setTable(table);
		setCondition(condition);
	}
	
	@Override
	public boolean execute() {
		if(!getTable().getColumnNames().contains(columnName)) {
			return false;
		}
		int index = 0;
		if(getCondition() == null) {
			for (int j = 0; j < getTable().getColumnNames().size(); j++) {
				if(columnName.equals(getTable().getColumnNames().get(j))) {
					index = j;
					break;
				}
			}
			for (int i = 0; i < getTable().getRows().size(); i++) {
					getTable().getRows().get(index).updateCell(columnName, new Cell(value));
			}
		}else {
			
			
			
		}
		
		
		
		
		return true;
	}

}
