package eg.edu.alexu.csd.oop.db.cs01;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;

public class OurSql implements Database {

	//Singleton pattern to only have one data manager.
	
	private static OurSql instance;
	
	private OurSql() {
		dataBasePath = new String();
	}
		
	public static OurSql getInstance() {
		if (instance == null) {
			instance = new OurSql();
		}
		return instance;
	}
	
	private String dataBasePath;
	
	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
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
