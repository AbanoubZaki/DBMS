package eg.edu.alexu.csd.oop.dp.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.Condition;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class SelectFrom extends OurQuery {

	
	
	public SelectFrom(Table table, Condition condition) {
		setTable(table);
		
	}

	public SelectFrom(Table table, String column, Condition condition) {
		setTable(table);
	}

	@Override
	public boolean execute() {
		return false;
	}

}
