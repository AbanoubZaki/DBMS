package eg.edu.alexu.csd.oop.db.factory;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.cs01.queries.IQuery;

public abstract class OurQueryFactory implements IFactory {
	/**
	 * current database.
	 */
	private String theMainDataBase;
	
	
	@Override
	public abstract IQuery parse(String theQuery) throws SQLException;

	public String getTheMainDataBase() {
		return theMainDataBase;
	}

	public void setTheMainDataBase(String theMainDataBase) {
		this.theMainDataBase = theMainDataBase;
	}

}
