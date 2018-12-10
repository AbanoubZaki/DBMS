package eg.edu.alexu.csd.oop.db.cs01.queries;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.jdbc.OurLogger;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class DropTable extends OurQuery {
	
	@Override
	public boolean execute() throws SQLException {
		if (Table.getInstance() == null || Table.getInstance().getColumnNamesAsGiven().size() == 0) {
			OurLogger.error(this.getClass(), "Table not found.");
			throw new SQLException("Table not found.");
		}
		if(FileManager.getInstance().dropTable(Table.getInstance())) {
			Table.dropCurrentTable();
			return true;
		}
		return false ;
	}

}
