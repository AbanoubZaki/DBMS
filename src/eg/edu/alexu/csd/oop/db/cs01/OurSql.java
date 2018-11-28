package eg.edu.alexu.csd.oop.db.cs01;

import java.io.File;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.dp.cs01.queries.CreateDatabase;
import eg.edu.alexu.csd.oop.dp.cs01.queries.IQuery;

public class OurSql implements Database {

	//Singleton pattern to only have one data manager.
	
	private static OurSql instance;

	private OurSql() {
		File dbs = new File("databases");
		dbs.mkdirs();
	}
		
	public static OurSql getInstance() {
		if (instance == null) {
			instance = new OurSql();
		}
		return instance;
	}

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		CreateDatabase CDB = new CreateDatabase(databaseName, dropIfExists);
		CDB.execute1();
		return CDB.getDatabasePath();
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		IQuery objectQquery = Parser.getInstance().parseQuery(query);
		return objectQquery.execute1();
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		IQuery selectQuery = Parser.getInstance().parseQuery(query);
		selectQuery.execute1();
		return selectQuery.getSelected();
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		IQuery objectQquery = Parser.getInstance().parseQuery(query);
		return objectQquery.execute2();
	}

}
