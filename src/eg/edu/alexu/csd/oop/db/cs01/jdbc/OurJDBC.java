package eg.edu.alexu.csd.oop.db.cs01.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class OurJDBC {

	private static OurJDBC instance;
	private Connection connection;
	private ResultSet resultSet;
	private String path;
	
	private OurJDBC() {
	}

	public static OurJDBC getInstance() {
		if (instance == null) {
			instance = new OurJDBC();
		}
		return instance;
	}

	public String run(final String commnd) throws SQLException {
		if (commnd.contains("jdbc") && commnd.contains("localhost")) {
			Properties info = new Properties();
			info.put("path", new File(path).getAbsoluteFile());
			connection = OurDriver.getInstance().connect(commnd, info);
			OurLogger.info(this.getClass(), "Connection is created.");
			return "Connection is created.";
		} else {
			Statement statment;
			try {
				statment = connection.createStatement();
			} catch (Exception e) {
				throw new SQLException("");
			}
			if (statment.execute(commnd)) {
				resultSet = statment.getResultSet();
				if (statment.getUpdateCount() == -1) {
					if (!commnd.toLowerCase().contains("select ")) {
						OurLogger.info(this.getClass(), "You have made changes to the databases.");
						return "You have made changes to the databases.";
					} else {
						OurLogger.info(this.getClass(), "Selection is made successfully.");
						return "Selection is made successfully.";
					}
				} else {
					OurLogger.info(this.getClass(), "You have made changes to " + statment.getUpdateCount() + " rows.");
					return "You have made changes to " + statment.getUpdateCount() + " rows.";
				}
			}
		}
		return "";
	}

	/**
	 * @return the resultSet
	 */
	public ResultSet getResultSet() {
		return resultSet;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
