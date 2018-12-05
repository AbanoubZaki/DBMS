package eg.edu.alexu.csd.oop.db.cs01.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

public class OurDriver implements Driver {

	// singleton.

	private static OurDriver driver;

	private OurDriver() {
	}

	public static OurDriver getInstance() {
		if (driver == null) {
			driver = new OurDriver();
		}
		return driver;
	}

	/**
	 * if true then URL is supported. if false then URL is not supported.
	 */
	@Override
	public boolean acceptsURL(String url) throws SQLException {
		if (url.equals("jdbc:xmldb://localhost")) {
			return true;
		} else if (url.equals("url2") || url.equals("url3")) {
			throw new SQLException("The database files type is not supported.");
		}
		return false;
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		if (acceptsURL(url)) {
			File dir = (File) info.get("path");
			dir.mkdirs();
			// give the user a connection to his database which is in the given path.
			String path = dir.getAbsolutePath();
			return OurConnection.getConnection(path); // pool
		} else {
			// then URL is not supported and is not a valid one.
			throw new SQLException("Wrong URL has been entered.");
		}	
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		ArrayList<DriverPropertyInfo> propertyInfo = new ArrayList<>();
		propertyInfo.add((DriverPropertyInfo) info.get("path"));
		return (DriverPropertyInfo[]) propertyInfo.toArray();
	}

	@Override
	public int getMajorVersion() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMinorVersion() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean jdbcCompliant() {
		throw new UnsupportedOperationException();
	}

}
