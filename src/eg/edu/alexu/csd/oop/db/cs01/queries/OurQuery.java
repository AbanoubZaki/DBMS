package eg.edu.alexu.csd.oop.db.cs01.queries;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalCondition;
import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class OurQuery implements IQuery {

	private LogicalCondition condition;
	private String column;
	
	@Override
	public boolean execute1() throws SQLException {
		return false;
	}

	@Override
	public int execute2() throws SQLException {
		return 0;
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
	public void setCondition(LogicalCondition condition2) {
		this.condition = condition2;
	}

	@Override
	public LogicalCondition getCondition() {
		return condition;
	}

	@Override
	public void setSelected(Object[][] selected) {
		
	}

	@Override
	public Object[][] getSelected() {
		return null;
	}

}
