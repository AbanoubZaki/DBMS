package eg.edu.alexu.csd.oop.db.cs01.queries;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;
import eg.edu.alexu.csd.oop.db.cs01.jdbc.OurLogger;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class CreateTable extends OurQuery {

	@Override
	public boolean execute() throws SQLException {
		if (Table.getInstance().getDatabaseName() == null || Table.getInstance().getDatabaseName().equals("")) {
			throw new SQLException("Database not found.");
		}
		if (Table.getInstance() == null || Table.getInstance().getColumnNamesAsGiven().size() == 0) {
			OurLogger.error(this.getClass(), "Table not found.");
			throw new SQLException("Table not found.");
		}
		Set<String> columnsSet = new HashSet<>(Table.getInstance().getColumnNamesToLowerCase()); 
		if(columnsSet.size() != Table.getInstance().getColumnNamesToLowerCase().size()) {
			OurLogger.error(this.getClass(), "Duplicates found in column names.");
			throw new SQLException("Duplicates found in column names.");
		}
		if(!FileManager.getInstance().createTable(Table.getInstance())) {
			FileManager.getInstance().readTable(Table.getInstance());
			return false;
		}
		return true;
	}

}
