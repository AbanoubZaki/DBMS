package eg.edu.alexu.csd.oop.db.cs01;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs01.factory.SelectQueryFactory;
import eg.edu.alexu.csd.oop.db.cs01.factory.StructureQueryFactory;
import eg.edu.alexu.csd.oop.db.cs01.factory.UpdateQueryFactory;
import eg.edu.alexu.csd.oop.db.cs01.jdbc.OurLogger;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;
import eg.edu.alexu.csd.oop.db.cs01.queries.IQuery;

public class OurSql implements Database {

	//Singleton pattern to only have one data manager.
	
	private static OurSql instance;
	
	
	private String currentDataBase;

	private OurSql() {
	}
		
	public static OurSql getInstance() {
		if (instance == null) {
			instance = new OurSql();
			Runtime.getRuntime().addShutdownHook(new Table.WriteCurrentTable());
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
//		IQuery objectQuery = QueryFactory.getInstance().parseQuery(query);
		IQuery objectQuery = StructureQueryFactory.getInstance().parse(query);
//		currentDataBase = StructureQueryFactory.getInstance().getTheMainDataBase();
		if (objectQuery == null) {
			OurLogger.error(this.getClass(), "Structure Query Failed , syntax error.");
			throw new SQLException("Structure Query Failed , syntax error.");
		}
		return objectQuery.execute();
		
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
//		IQuery selectQuery = QueryFactory.getInstance().parseQuery(query);
		IQuery selectQuery = SelectQueryFactory.getInstance().parse(query);
		if (selectQuery == null) {
			OurLogger.error(this.getClass(), "Selection Query Failed , syntax error.");
			throw new SQLException("Selection Query Failed , syntax error.");
		}
		selectQuery.execute();
		return selectQuery.getSelected();
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
//		IQuery objectQuery = QueryFactory.getInstance().parseQuery(query);
		IQuery objectQuery = UpdateQueryFactory.getInstance().parse(query);
		if (objectQuery == null) {
			OurLogger.error(this.getClass(), "Updating Query  Failed , syntax error.");
			throw new SQLException("Updating Query  Failed , syntax error.");
		}
		objectQuery.execute();
		return objectQuery.getUpdatedRows();
	}

	public String getCurrentDataBase() {
		return currentDataBase;
	}

	public void setCurrentDataBase(String currentDataBase) {
		this.currentDataBase = currentDataBase;
	}

}
