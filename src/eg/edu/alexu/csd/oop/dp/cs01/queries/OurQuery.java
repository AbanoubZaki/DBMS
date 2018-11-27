package eg.edu.alexu.csd.oop.dp.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.Condition;
import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public abstract class OurQuery implements IQuery {

	private Table table;
	private Condition condition;
	private String column;
	
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
		readTable();
	}	
	
	@Override
	public abstract boolean execute();

	@Override
	public boolean readTable() {
		return FileManager.getInstance().readTable(table);
	}
	
	@Override
	public void setColumn(String column) {
		this.column = column;
	}

	@Override
	public String getColumn() {
		return column;
	}

	@Override
	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	@Override
	public Condition getCondition() {
		return condition;
	}

}
