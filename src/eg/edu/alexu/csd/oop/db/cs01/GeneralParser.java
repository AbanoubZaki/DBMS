package eg.edu.alexu.csd.oop.db.cs01;

import java.sql.SQLException;

public class GeneralParser {

	private static GeneralParser instance;
	private boolean checkerExecute;
	private Object[][] currentData;
	private int updatedRows;
	private GeneralParser() {
		checkerExecute = false;
		currentData = null;
		updatedRows=0;
	}

	/**
	 * @return the checkerExecute
	 */
	public boolean isCheckerExecute() {
		return checkerExecute;
	}

	/**
	 * @param checkerExecute the checkerExecute to set
	 */
	private void setCheckerExecute(boolean checkerExecute) {
		this.checkerExecute = checkerExecute;
	}

	/**
	 * @return the currentData
	 */
	public Object[][] getCurrentData() {
		return currentData;
	}

	/**
	 * @param currentData the currentData to set
	 */
	private void setCurrentData(Object[][] currentData) {
		this.currentData = currentData;
	}

	/**
	 * @return the updatedRows
	 */
	public int getUpdatedRows() {
		return updatedRows;
	}

	/**
	 * @param updatedRows the updatedRows to set
	 */
	private void setUpdatedRows(int updatedRows) {
		this.updatedRows = updatedRows;
	}

	// str1.toLowerCase().contains(str2.toLowerCase())
	public static GeneralParser getInstance() {

		if (instance == null) {
			instance = new GeneralParser();
		}
		return instance;
	}

	public void parse (String query) throws SQLException {
		
		if ((query.toLowerCase().contains("create") || query.toLowerCase().contains("drop"))
				&& (query.toLowerCase().contains("database") || query.toLowerCase().contains("table"))) {
			
			setCheckerExecute(OurSql.getInstance().executeStructureQuery(query));
			
		}else if (query.toLowerCase().contains("select")) {
			setCurrentData(	OurSql.getInstance().executeQuery(query));
		}else if (query.toLowerCase().contains("update")
				|| query.toLowerCase().contains("insert") 
				|| query.toLowerCase().contains("delete") ) {
			
			setUpdatedRows(	OurSql.getInstance().executeUpdateQuery(query));
		}	
		
	}

}
