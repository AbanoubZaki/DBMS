package eg.edu.alexu.csd.oop.db.cs01;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.dp.cs01.queries.IQuery;

public class OurSql implements Database {

	//Singleton pattern to only have one data manager.
	
	private static OurSql instance;

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
		String query = "CREATE DATABASE "+databaseName.toLowerCase();
		try {
			if(executeStructureQuery(query)&&dropIfExists) {
				query = query.replace("CREATE", "drop");
				executeStructureQuery(query);
				query = query.replace("drop","CREATE");
				executeStructureQuery(query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(databaseName.contains(System.getProperty("file.separator")))
			return databaseName;
		return "databases"+System.getProperty("file.separator")+databaseName;
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		IQuery objectQuery = Parser.getInstance().parseQuery(query);
		if (objectQuery == null) {
			throw new SQLException("Query syntax error.");
		}
		return objectQuery.execute1();
		
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		IQuery selectQuery = Parser.getInstance().parseQuery(query);
		if (selectQuery == null) {
			throw new SQLException("Selection Failed Query, syntax error.");
		}
		selectQuery.execute1();
		return selectQuery.getSelected();
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		IQuery objectQuery = Parser.getInstance().parseQuery(query);
		if (objectQuery == null) {
			throw new SQLException("Query syntax error.");
		}
		return objectQuery.execute2();
	}

}
