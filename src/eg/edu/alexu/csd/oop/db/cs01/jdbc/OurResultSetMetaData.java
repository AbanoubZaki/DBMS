package eg.edu.alexu.csd.oop.db.cs01.jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class OurResultSetMetaData implements ResultSetMetaData {
	/**
	 * table given by the main.
	 */
	private Table myTable;

	/**
	 * Constructor.
	 */
	public OurResultSetMetaData(Table table) {
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

	@Override
	public String getCatalogName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getColumnClassName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the number of columns in this ResultSet object.
	 */
	@Override
	public int getColumnCount() throws SQLException {
		// TODO Auto-generated method stub
		try {
			return myTable.getSelectedColumns().size();
		} catch (Exception e) {
			OurLogger.error(this.getClass(), "There is no table selected.");
			throw new SQLException("There is no table selected.");
		}

	}

	@Override
	public int getColumnDisplaySize(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets the designated column's suggested title for use in printouts and
	 * displays. The suggested title is usually specified by the SQL AS clause. If a
	 * SQL AS is not specified, the value returned from getColumnLabel will be the
	 * same as the value returned by the getColumnName method.
	 */
	@Override
	public String getColumnLabel(int column) throws SQLException {
		// TODO Auto-generated method stub
		try {
			return getColumnName(column);
		} catch (Exception e) {
			OurLogger.error(this.getClass(), "There is no table selected.");
			throw new SQLException("There is no table selected.");
		}

	}

	/**
	 * Get the designated column's name.
	 */
	@Override
	public String getColumnName(int column) throws SQLException {
		// TODO Auto-generated method stub
		try {
			if (column <= 0 || column > myTable.getSelectedColumns().size()) {
				OurLogger.error(this.getClass(), "Entered index is out of range");
				throw new SQLException("Entered index is out of range");
			}
			return myTable.getSelectedColumns().get(column - 1);
		} catch (Exception e) {
			OurLogger.error(this.getClass(), "There is no table selected.");
			throw new SQLException("There is no table selected.");
		}

	}

	/**
	 * Retrieves the designated column's SQL type.
	 */
	@Override
	public int getColumnType(int column) throws SQLException {
		// TODO Auto-generated method stub

		try {
			if (column <= 0 || column > myTable.getSelectedColumns().size()) {
				throw new SQLException("Entered index is out of range");
			}
			String columnName = myTable.getSelectedColumns().get(column - 1).toLowerCase();
			if (myTable.getColumnTypes().get(columnName) == "int") {
				return java.sql.Types.INTEGER;
			} else if (myTable.getColumnTypes().get(columnName) == "varchar") {
				return java.sql.Types.VARCHAR;
				// } else if (myTable.getColumnTypes().get(columnName) == "float") {
				// return java.sql.Types.FLOAT;
				// } else if (myTable.getColumnTypes().get(columnName) == "date") {
				// return java.sql.Types.DATE;
			} else {
				OurLogger.error(this.getClass(), "DataType is not supported.");
				throw new SQLException("DataType is not supported.");
			}
		} catch (Exception e) {
			OurLogger.error(this.getClass(), "There is no table selected.");
			throw new SQLException("There is no selected table");
		}
	}

	@Override
	public String getColumnTypeName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getPrecision(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getScale(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSchemaName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets the designated column's table name.
	 */
	@Override
	public String getTableName(int column) throws SQLException {
		// TODO Auto-generated method stub
		try {
			return myTable.getTableName();
		} catch (Exception e) {
			OurLogger.error(this.getClass(), "There is no table selected.");
			throw new SQLException("There is no selected table.");
		}
	}

	@Override
	public boolean isAutoIncrement(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCaseSensitive(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCurrency(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDefinitelyWritable(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int isNullable(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isReadOnly(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSearchable(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSigned(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWritable(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}

}
