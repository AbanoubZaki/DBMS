package eg.edu.alexu.csd.oop.dp.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.condition.RelationalCondition;
import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class DeleteFrom extends OurQuery {

	public DeleteFrom(Table table, RelationalCondition condition) {
		setTable(table);
		setCondition(condition);
	}
	
	@Override
	public int execute2() {
		FileManager.getInstance().readTable(getTable());
		if (getCondition() == null) {
			int effectedRows = getTable().getRows().size(); 
			getTable().setRows(null);
			return effectedRows;
		}else if (getCondition() != null) {
			
		}
		
		return 0;
	}

}
