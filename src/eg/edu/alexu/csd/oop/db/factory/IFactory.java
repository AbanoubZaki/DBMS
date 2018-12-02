package eg.edu.alexu.csd.oop.db.factory;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs01.queries.IQuery;

public interface IFactory {
	/**
	 * 
	 * @param theQuery
	 * 				string entered.
	 * @return query object to be executed. 
	 */
	public IQuery parse(String theQuery) throws SQLException;

}
