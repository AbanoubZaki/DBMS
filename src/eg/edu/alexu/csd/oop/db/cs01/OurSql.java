package eg.edu.alexu.csd.oop.db.cs01;

import java.io.File;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs01.fileManager.FileManager;

public class OurSql implements Database {

	//Singleton pattern to only have one data manager.
	
	private static OurSql instance;
	private FileManager fileManager;
	private OurSql() {
		File dbs = new File("databases");
		dbs.mkdirs();
		fileManager = FileManager.getInstance();
	}
		
	public static OurSql getInstance() {
		if (instance == null) {
			instance = new OurSql();
		}
		return instance;
	}
	
	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		return fileManager.createDB(databaseName, dropIfExists);
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
