package eg.edu.alexu.csd.oop.dp.cs01.queries;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class DropTable extends OurQuery {
	
	public DropTable(Table table) {
		super(table);
	}
	
	@Override
	public boolean execute1()throws SQLException {
		if(getTable()==null)
			return false;
		if(FileManager.getInstance().dropTable(getTable())) {
			Table.dropCurrentTable();
			return true;
		}
		throw new SQLException("no such table: "+getTable().getTableName());
	}

}
