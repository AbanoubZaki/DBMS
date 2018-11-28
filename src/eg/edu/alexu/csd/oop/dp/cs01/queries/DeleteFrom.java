package eg.edu.alexu.csd.oop.dp.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.Condition;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class DeleteFrom extends OurQuery {

	public DeleteFrom(Table table, Condition condition) {
		setTable(table);
		setCondition(condition);
	}
	
	@Override
	public boolean execute() {

		
		if (getCondition() == null) {
			getTable().setRows(null);
		}else if (getCondition() != null) {
			
		}
		
		return true;
	}

}
