package eg.edu.alexu.csd.oop.db.cs01.queries;

import java.util.ArrayList;

import eg.edu.alexu.csd.oop.db.cs01.condition.RelationalCondition;
import eg.edu.alexu.csd.oop.db.cs01.condition.RelationalSolver;
import eg.edu.alexu.csd.oop.db.cs01.modules.Row;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class DeleteFrom extends OurQuery {

	public DeleteFrom(Table table, RelationalCondition condition) {
		setTable(table);
		setCondition(condition);
	}
	
	@Override
	public int execute2() {
		int effectedRows = 0;
		 effectedRows = getTable().getRows().size();
		if (getCondition().getStringCondition() == null) {
			 effectedRows = getTable().getRows().size();
			getTable().setRows(null);
			return effectedRows;
		}else if (getCondition().getStringCondition() != null) {
			ArrayList<Row> remainingRows = new ArrayList<>();
			for (Row r:getTable().getRows()) {
				if (!RelationalSolver.getInstance().isRowSolvingCondition(r, getCondition())) {
					remainingRows.add(r);
				}
			}
			getTable().setRows(remainingRows);
			 effectedRows -= getTable().getRows().size();
		}
		
		return effectedRows;
	}

}
