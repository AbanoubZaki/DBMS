package eg.edu.alexu.csd.oop.dp.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public abstract class OurQuery implements IQuery{

	private Table table;
	public OurQuery() {
	}
	
	public OurQuery(Table table) {
		this.table = table;
	}
	
	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}	
	
	@Override
	public abstract boolean execute();

	@Override
	public boolean readTable() {
		return FileManager.getInstance().readTable(table);
	}

}
