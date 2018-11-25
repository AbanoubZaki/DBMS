package eg.edu.alexu.csd.oop.db.cs01;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs01.modules.DataBase;

public class OurSql implements Database {

	//Singleton pattern to only have one data manager.
	
	private static OurSql instance;
	private DataBase database;
	private OurSql() {
	}
		
	public static OurSql getInstance() {
		if (instance == null) {
			instance = new OurSql();
		}
		return instance;
	}
	
	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		database = new DataBase(databaseName);
		return null;
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		return false;
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		return null;
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		return 0;
	}

}
