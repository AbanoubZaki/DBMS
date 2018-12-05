package eg.edu.alexu.csd.oop.db.cs01.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class OurConnection implements Connection {

	/**
	 * all connections created.
	 */
	public static ArrayList<Connection> connectionPool;
	public static ArrayList<Connection> connectionsUsed;
	private static int MAX_NO_OF_CONNECTIONS = 15;

	/**
	 * attributes of each database connection. information of connection. statements
	 * to be executed. is the connection available or not to be used again if
	 * available (isClosed = true).
	 */
	private Properties info;
	private ArrayList<Statement> statements;

	public OurConnection() {
		info = new Properties();
		statements = new ArrayList<>();
	}

	public static Connection getConnection(Properties info) throws SQLException {
		/**
		 * if pool is empty create a pool and array list of used connections then put
		 * one in it and return it.
		 */
		Connection c;
		if (connectionPool == null) {
			connectionPool = new ArrayList<>(MAX_NO_OF_CONNECTIONS);
			connectionsUsed = new ArrayList<>(MAX_NO_OF_CONNECTIONS);
			for (int i = 0; i < MAX_NO_OF_CONNECTIONS; i++) {
				connectionPool.add(new OurConnection());
			}
			// put last connection in used array list.
			connectionsUsed.add(connectionPool.get(connectionPool.size() - 1));
			// remove last connection.
			connectionPool.remove(connectionPool.size() - 1);
			c = connectionsUsed.get(connectionsUsed.size() - 1);
			c.setClientInfo(info);
			return c;
		}
		// check if there exist connections in the pool.
		if (connectionPool.size() > 0) {
			connectionsUsed.add(connectionPool.get(connectionPool.size() - 1));
			c = connectionsUsed.get(connectionsUsed.size() - 1);
			c.setClientInfo(info);
			return c;
		} else {
			throw new SQLException("Access denied, Maximum number of connections has been reached.");
		}

	}
	
	public ArrayList<Statement> getStatements() {
		return statements;
	}

	public void setStatements(ArrayList<Statement> statements) {
		this.statements = statements;
	}
	
	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void abort(Executor executor) throws SQLException {

		throw new UnsupportedOperationException();

	}

	@Override
	public void clearWarnings() throws SQLException {

		throw new UnsupportedOperationException();

	}

	@Override
	public void close() throws SQLException {
		for (int i = 0; i < connectionsUsed.size(); i++) {
			if (connectionsUsed.get(i).equals(this)) {
				this.statements.clear();
				this.info.clear();
				connectionPool.add(this);
				connectionsUsed.remove(i);
				return;
			}
		}
		//if the compiler reached here then the connection is already closed.
		throw new SQLException("Connection is already closed");
	}
	
	@Override
	public void commit() throws SQLException {

		throw new UnsupportedOperationException();

	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Blob createBlob() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Clob createClob() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public NClob createNClob() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Statement createStatement() throws SQLException {
		Statement st = new OurStatement(this);
		statements.add(st);
		return st;
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getAutoCommit() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public String getCatalog() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		return this.info;
	}

	@Override
	public String getClientInfo(String name) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public int getHoldability() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public int getNetworkTimeout() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public String getSchema() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public int getTransactionIsolation() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isClosed() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isReadOnly() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {

	}

	@Override
	public void rollback() throws SQLException {

	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {

	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {

	}

	@Override
	public void setCatalog(String catalog) throws SQLException {

	}

	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		this.info = properties;
	}

	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		
	}

	@Override
	public void setHoldability(int holdability) throws SQLException {

	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {

	}

	@Override
	public Savepoint setSavepoint() throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void setSchema(String schema) throws SQLException {

	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {

	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

	}

}
