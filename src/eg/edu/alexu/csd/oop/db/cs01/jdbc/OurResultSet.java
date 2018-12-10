package eg.edu.alexu.csd.oop.db.cs01.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import eg.edu.alexu.csd.oop.db.cs01.modules.Row;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class OurResultSet implements ResultSet {

	private int currentRowposition = 0;
	private Row currentRow = null;
	private Table myTable;
	private boolean isClosed;
	private ResultSetMetaData myResultSetMetaData;
	private Statement myStatement;

	public OurResultSet(Table table, ResultSetMetaData resultSetMetaData, Statement statement) {
		myStatement = statement;
		myResultSetMetaData = resultSetMetaData;
		myTable = table;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Moves the cursor to the given row number in this ResultSet object. If the row
	 * number is positive, the cursor moves to the given row number with respect to
	 * the beginning of the result set. The first row is row 1, the second is row 2,
	 * and so on.
	 * 
	 * If the given row number is negative, the cursor moves to an absolute row
	 * position with respect to the end of the result set. For example, calling the
	 * method absolute(-1) positions the cursor on the last row; calling the method
	 * absolute(-2) moves the cursor to the next-to-last row, and so on.
	 * 
	 * If the row number specified is zero, the cursor is moved to before the first
	 * row.
	 * 
	 * An attempt to position the cursor beyond the first/last row in the result set
	 * leaves the cursor before the first row or after the last row.
	 * 
	 * Note: Calling absolute(1) is the same as calling first(). Calling
	 * absolute(-1) is the same as calling last().
	 * 
	 * Parameters: row - the number of the row to which the cursor should move. A
	 * value of zero indicates that the cursor will be positioned before the first
	 * row; a positive number indicates the row number counting from the beginning
	 * of the result set; a negative number indicates the row number counting from
	 * the end of the result set Returns: true if the cursor is moved to a position
	 * in this ResultSet object; false if the cursor is before the first row or
	 * after the last row
	 */
	@Override
	public boolean absolute(int row) throws SQLException {
		// TODO Auto-generated method stub
		throwSqlException();
		try {
			if (row == 0) {
				// 0 indicates before first element.
				currentRowposition = 0;
				currentRow = null;
				return false;
			} else if (row > 0) {
				if (row > myTable.getSelectedRows().size()) {
					currentRowposition = myTable.getSelectedRows().size() + 1;
					currentRow = null;
					return false;
				}
				currentRowposition = row;
				currentRow = myTable.getSelectedRows().get(row - 1);
				return true;
			} else if (row < 0) {// starting from the end.
				if ((row * -1) > myTable.getSelectedRows().size()) {// if required is out of range.
					currentRowposition = 0;
					currentRow = null;
					return false;
				}
				// currentRowPoosition carry the positive value of entered row
				currentRowposition = myTable.getSelectedRows().size() + 1 + row;
				currentRow = myTable.getSelectedRows().get(currentRowposition - 1);
				return true;
			}
		} catch (Exception e) {
			throw new SQLException();
		}
		return false;
	}

	/**
	 * Moves the cursor to the end of this ResultSet object, just after the last
	 * row. This method has no effect if the result set contains no rows.
	 */
	@Override
	public void afterLast() throws SQLException {
		// TODO Auto-generated method stub
		throwSqlException();
		try {
			currentRowposition = myTable.getSelectedRows().size() + 1;
			currentRow = null;
		} catch (Exception e) {
		}

	}

	/**
	 * Moves the cursor to the front of this ResultSet object, just before the first
	 * row. This method has no effect if the result set contains no rows.
	 */
	@Override
	public void beforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		throwSqlException();
		try {
			currentRowposition = 0;
			currentRow = null;
		} catch (Exception e) {
		}
	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Releases this ResultSet object's database and JDBC resources immediately
	 * instead of waiting for this to happen when it is automatically closed Note: A
	 * ResultSet object is automatically closed by the Statement object that
	 * generated it when that Statement object is closed, re-executed, or is used to
	 * retrieve the next result from a sequence of multiple results.
	 * 
	 * Calling the method close on a ResultSet object that is already closed is a
	 * no-op.
	 */
	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		throwSqlException();
		currentRowposition = 0;
		currentRow = null;
		myTable = null;
		isClosed = true;
	}

	@Override
	public void deleteRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Parameters: columnLabel - the label for the column specified with the SQL AS
	 * clause. If the SQL AS clause was not specified, then the label is the name of
	 * the column Returns: the column index of the given column name Throws:
	 * SQLException - if the ResultSet object does not contain a column labeled
	 * columnLabel, a database access error occurs or this method is called on a
	 * closed result set
	 */
	@Override
	public int findColumn(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		throwSqlException();
		try {
			int columnIndex = -1;
			for (int i = 0; i < myTable.getSelectedColumns().size(); i++) {
				if (columnLabel.toLowerCase().equals(myTable.getSelectedColumns().get(i).toLowerCase())) {
					columnIndex = i;
					break;
				}
			}
			if (columnIndex >= 0) {
				return columnIndex;
			} else {
				throw new SQLException();
			}
		} catch (Exception e) {
			throw new SQLException();
		}
	}

	/**
	 * Moves the cursor to the first row in this ResultSet object. Returns: true if
	 * the cursor is on a valid row; false if there are no rows in the result set
	 */
	@Override
	public boolean first() throws SQLException {
		// TODO Auto-generated method stub
		throwSqlException();
		try {
			if (myTable.getSelectedRows().isEmpty()) {
				return false;
			}
			currentRowposition = 1;
			currentRow = myTable.getSelectedRows().get(currentRowposition - 1);
			return true;
		} catch (Exception e) {
			throw new SQLException();
		}
	}

	@Override
	public Array getArray(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Array getArray(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Blob getBlob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clob getClob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clob getClob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getConcurrency() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCursorName() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getHoldability() throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * ResultSet object as an int in the Java programming language. Parameters:
	 * columnIndex - the first column is 1, the second is 2, ... Returns: the column
	 * value; if the value is SQL NULL, the value returned is 0 Throws: SQLException
	 * - if the columnIndex is not valid; if a database access error occurs or this
	 * method is called on a closed result set
	 */
	@Override
	public int getInt(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		throwSqlException();
		try {
			if ((columnIndex < 1) || (columnIndex > myTable.getSelectedColumns().size())) {
				throw new SQLException();
			} else {
				if (currentRow.getCellByColumn(myTable.getSelectedColumns().get(columnIndex - 1)).equals(null)) {
					return 0;
				} else {
					return Integer
							.parseInt(currentRow.getCellByColumn(myTable.getSelectedColumns().get(columnIndex - 1)));
				}
			}
		} catch (Exception e) {
			throw new SQLException();
		}
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * ResultSet object as an int in the Java programming language. Parameters:
	 * columnLabel - the label for the column specified with the SQL AS clause. If
	 * the SQL AS clause was not specified, then the label is the name of the column
	 * Returns: the column value; if the value is SQL NULL, the value returned is 0
	 * Throws: SQLException - if the columnLabel is not valid; if a database access
	 * error occurs or this method is called on a closed result set
	 */
	@Override
	public int getInt(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		try {
			int columnIndex = -1;
			for (int i = 0; i < myTable.getSelectedColumns().size(); i++) {
				if (columnLabel.toLowerCase().equals(myTable.getSelectedColumns().get(i).toLowerCase())) {
					columnIndex = i;
					break;
				}
			}
			if (columnIndex >= 0) {
				if (currentRow.getCellByColumn(myTable.getSelectedColumns().get(columnIndex - 1)).equals(null)) {
					return 0;
				} else {
					return Integer
							.parseInt(currentRow.getCellByColumn(myTable.getSelectedColumns().get(columnIndex - 1)));
				}
			} else {
				throw new SQLException();
			}
		} catch (Exception e) {
			throw new SQLException();
		}
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		try {
			myResultSetMetaData = new OurResultSetMetaData(myTable);
			if (isClosed == true) {
				OurLogger.warn(this.getClass(), "MetaData is closed");
				throw new SQLException("MetaData is closed");
			}
			return myResultSetMetaData;
		} catch (Exception e) {
			OurLogger.error(this.getClass(), "There is no selected table");
			throw new SQLException("There is no selected table");
		}

	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		throwSqlException();
		try {
			String columnName = myTable.getSelectedColumns().get(columnIndex - 1);
			if (myTable.getColumnTypes().get(columnName).equals("int")) {
				if(currentRow.getCellByColumn(columnName)!=null)
					return Integer.parseInt(currentRow.getCellByColumn(columnName));
			}
			return currentRow.getCellByColumn(columnName);
		} catch (Exception e) {
			throw new SQLException();
		}
	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Ref getRef(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Ref getRef(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Statement getStatement() throws SQLException {
		// TODO Auto-generated method stub
		throwSqlException();
		try {
			return myStatement;
		} catch (Exception e) {
			throw new SQLException();
		}
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		try {
			if ((columnIndex < 1) || (columnIndex > myTable.getSelectedColumns().size())) {
				throw new SQLException();
			} else {
				if (currentRow.getCellByColumn(myTable.getSelectedColumns().get(columnIndex - 1)).equals(null)) {
					return null;
				} else {
					return currentRow.getCellByColumn(myTable.getSelectedColumns().get(columnIndex - 1));
				}
			}
		} catch (Exception e) {
			throw new SQLException();
		}
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		try {
			int columnIndex = -1;
			for (int i = 0; i < myTable.getSelectedColumns().size(); i++) {
				if (columnLabel.toLowerCase().equals(myTable.getSelectedColumns().get(i).toLowerCase())) {
					columnIndex = i;
					break;
				}
			}
			if (columnIndex >= 0) {
				if (currentRow.getCellByColumn(myTable.getSelectedColumns().get(columnIndex - 1)).equals(null)) {
					return null;
				} else {
					return currentRow.getCellByColumn(myTable.getSelectedColumns().get(columnIndex - 1));
				}
			} else {
				throw new SQLException();
			}
		} catch (Exception e) {
			throw new SQLException();
		}
	}

	@Override
	public Time getTime(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getType() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getURL(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getURL(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void insertRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Retrieves whether the cursor is after the last row in this ResultSet object.
	 * Note:Support for the isAfterLast method is optional for ResultSets with a
	 * result set type of TYPE_FORWARD_ONLY
	 * 
	 * Returns: true if the cursor is after the last row; false if the cursor is at
	 * any other position or the result set contains no rows
	 */
	@Override
	public boolean isAfterLast() throws SQLException {
		// TODO Auto-generated method stub
		throwSqlException();
		try {
			if (currentRowposition > myTable.getSelectedRows().size()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new SQLException();
		}
	}

	/**
	 * Retrieves whether the cursor is before the first row in this ResultSet
	 * object. Note:Support for the isBeforeFirst method is optional for ResultSets
	 * with a result set type of TYPE_FORWARD_ONLY
	 * 
	 * Returns: true if the cursor is before the first row; false if the cursor is
	 * at any other position or the result set contains no rows
	 */

	@Override
	public boolean isBeforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		throwSqlException();
		try {
			if (currentRowposition < 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new SQLException();
		}
	}

	/**
	 * Retrieves whether this ResultSet object has been closed. A ResultSet is
	 * closed if the method close has been called on it, or if it is automatically
	 * closed.
	 */
	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		try {
			if (isClosed == true) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new SQLException();
		}
	}

	/**
	 * Retrieves whether the cursor is on the first row of this ResultSet object.
	 * Note:Support for the isFirst method is optional for ResultSets with a result
	 * set type of TYPE_FORWARD_ONLY
	 * 
	 * Returns: true if the cursor is on the first row; false otherwise
	 */
	@Override
	public boolean isFirst() throws SQLException {
		// TODO Auto-generated method stub
		throwSqlException();
		try {
			if (currentRowposition == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new SQLException();
		}
	}

	/**
	 * Retrieves whether the cursor is on the last row of this ResultSet object.
	 * Note: Calling the method isLast may be expensive because the JDBC driver
	 * might need to fetch ahead one row in order to determine whether the current
	 * row is the last row in the result set. Note: Support for the isLast method is
	 * optional for ResultSets with a result set type of TYPE_FORWARD_ONLY
	 * 
	 * Returns: true if the cursor is on the last row; false otherwise
	 */
	@Override
	public boolean isLast() throws SQLException {
		// TODO Auto-generated method stub
		throwSqlException();
		try {
			if (currentRowposition == myTable.getSelectedRows().size()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new SQLException();
		}
	}

	/**
	 * Moves the cursor to the last row in this ResultSet object. Returns: true if
	 * the cursor is on a valid row; false if there are no rows in the result set
	 */
	@Override
	public boolean last() throws SQLException {
		// TODO Auto-generated method stub
		throwSqlException();
		try {
			if (myTable.getSelectedRows().isEmpty()) {
				return false;
			}
			currentRowposition = myTable.getSelectedRows().size();
			currentRow = myTable.getSelectedRows().get(currentRowposition - 1);
			return true;
		} catch (Exception e) {
			throw new SQLException();
		}
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void moveToInsertRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Moves the cursor forward one row from its current position. A ResultSet
	 * cursor is initially positioned before the first row; the first call to the
	 * method next makes the first row the current row; the second call makes the
	 * second row the current row, and so on. Returns: true if the new current row
	 * is valid; false if there are no more rows
	 */
	@Override
	public boolean next() throws SQLException {
		// TODO Auto-generated method stub
		throwSqlException();
		try {
			if (currentRowposition != myTable.getSelectedRows().size()) {
				currentRowposition = currentRowposition + 1;
				currentRow = myTable.getSelectedRows().get(currentRowposition - 1);
				return true;
			} else {
				currentRowposition = myTable.getSelectedRows().size() + 1;
				currentRow = null;
				return false;
			}
		} catch (Exception e) {
			throw new SQLException();
		}
	}

	/**
	 * Moves the cursor to the previous row in this ResultSet object. Returns: true
	 * if the cursor is now positioned on a valid row; false if the cursor is
	 * positioned before the first row
	 */
	@Override
	public boolean previous() throws SQLException {
		// TODO Auto-generated method stub
		throwSqlException();
		try {
			if (currentRowposition > 0) {
				currentRowposition -= 1;
				if (currentRowposition == 0) {
					return false;
				}
				currentRow = myTable.getSelectedRows().get(currentRowposition - 1);
				return true;
			} else {
				currentRowposition = 0;
				currentRow = null;
				return false;
			}
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	@Override
	public void refreshRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean relative(int rows) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowInserted() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateArray(int columnIndex, Array x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateArray(String columnLabel, Array x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateByte(int columnIndex, byte x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateByte(String columnLabel, byte x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(int columnIndex, Clob x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(String columnLabel, Clob x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDate(int columnIndex, Date x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDate(String columnLabel, Date x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDouble(int columnIndex, double x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDouble(String columnLabel, double x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateFloat(int columnIndex, float x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateFloat(String columnLabel, float x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateInt(int columnIndex, int x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateInt(String columnLabel, int x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateLong(int columnIndex, long x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateLong(String columnLabel, long x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNString(int columnIndex, String nString) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNString(String columnLabel, String nString) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNull(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNull(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateObject(int columnIndex, Object x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateObject(String columnLabel, Object x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRef(int columnIndex, Ref x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRef(String columnLabel, Ref x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateShort(int columnIndex, short x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateShort(String columnLabel, short x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateString(int columnIndex, String x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateString(String columnLabel, String x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTime(int columnIndex, Time x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTime(String columnLabel, Time x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean wasNull() throws SQLException {
		throw new UnsupportedOperationException();
	}

	private void throwSqlException() throws SQLException {
		if (isClosed())
			throw new SQLException();
	}
}
