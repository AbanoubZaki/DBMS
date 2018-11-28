package eg.edu.alexu.csd.oop.dp.cs01.queries;

import eg.edu.alexu.csd.oop.db.cs01.condition.RelationalCondition;
import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class OurQuery implements IQuery {

	private Table table;
	private RelationalCondition condition;
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
	public boolean execute1() {
		return false;
	}

	@Override
	public int execute2() {
		return 0;
	}
	
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
	public void setCondition(RelationalCondition condition) {
		this.condition = condition;
	}

	@Override
	public RelationalCondition getCondition() {
		return condition;
	}

	@Override
	public void setSelected(Object[][] selected) {
		
	}

	@Override
	public Object[][] getSelected() {
		return null;
	}

	@Override
	public void setColumnIndexAndType() {
		
	}

	@Override
	public String getColumnType() {
		return null;
	}

	@Override
	public int getColumnIndex() {
		return 0;
	}

	public void setColumnType() {
		// TODO Auto-generated method stub
		
	}

}
