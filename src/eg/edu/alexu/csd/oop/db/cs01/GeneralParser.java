package eg.edu.alexu.csd.oop.db.cs01;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;

public class GeneralParser {

	private static GeneralParser instance;
	private boolean checkerExecute;
	private Object[][] currentData;
	private int updatedRows;
	private Database DB;
	private GeneralParser() {
		checkerExecute = false;
		updatedRows = 0;
		DB = OurSql.getInstance();
	}

	/**
	 * @return the checkerExecute
	 */
	public boolean isCheckerExecute() {
		return checkerExecute;
	}

	/**
	 * @param checkerExecute
	 *            the checkerExecute to set
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
	 * @param currentData
	 *            the currentData to set
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
	 * @param updatedRows
	 *            the updatedRows to set
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

	public void parse(String query) throws SQLException {

		if ((query.toLowerCase().contains("create") || query.toLowerCase().contains("drop"))
				&& (query.toLowerCase().contains("database") || query.toLowerCase().contains("table"))) {

			setCheckerExecute(DB.executeStructureQuery(query));
			if (isCheckerExecute()) {
				System.out.println("You have made changes to the databases.");
				setCheckerExecute(false);
			}

		} else if (query.toLowerCase().contains("select")) {
			setCurrentData(DB.executeQuery(query));
			if (getCurrentData() != null) {
				for (int i = 0; i < Table.getInstance().getColumnNamesAsGiven().size(); i++) {
					System.out.print(Table.getInstance().getColumnNamesAsGiven().get(i) + " ");
				}
				System.out.println();
				for (int i = 0; i < GeneralParser.getInstance().getCurrentData().length; i++) {
					for (int j = 0; j < GeneralParser.getInstance().getCurrentData()[i].length; j++) {
						if (GeneralParser.getInstance().getCurrentData()[i][j] instanceof String) {
							System.out.print(
									((String) GeneralParser.getInstance().getCurrentData()[i][j]).replaceAll("'", "")
											+ " ");
						} else
							System.out.print(GeneralParser.getInstance().getCurrentData()[i][j] + " ");
					}
					System.out.println();
				}
			}
			setCurrentData(null);
		} else if (query.toLowerCase().contains("update") || query.toLowerCase().contains("insert")
				|| query.toLowerCase().contains("delete")) {

			setUpdatedRows(DB.executeUpdateQuery(query));
			System.out.println("You have made changes to " + getUpdatedRows() + " rows.");
		} else {
			throw new SQLException("You entered a rotten query.");
		}
	}

}
