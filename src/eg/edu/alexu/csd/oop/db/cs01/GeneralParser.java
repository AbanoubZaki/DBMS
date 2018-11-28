package eg.edu.alexu.csd.oop.db.cs01;

import java.sql.SQLException;

public class GeneralParser {

	private static GeneralParser instance;

	private GeneralParser() {

	}

	// str1.toLowerCase().contains(str2.toLowerCase())
	public static GeneralParser getInstance() {

		if (instance == null) {
			instance = new GeneralParser();
		}
		return instance;
	}

	public void parser (String query) throws SQLException {
		
		if ((query.toLowerCase().contains(" create ") || query.toLowerCase().contains(" drop "))
				&& (query.toLowerCase().contains(" database ") || query.toLowerCase().contains(" table "))) {
			
			OurSql.getInstance().executeStructureQuery(query);
			
		}else if (query.toLowerCase().contains(" select ")) {
			OurSql.getInstance().executeQuery(query);
			
		}else if (query.toLowerCase().contains(" update ")
				|| query.toLowerCase().contains(" insert ") 
				|| query.toLowerCase().contains(" delete ") ) {
			
			OurSql.getInstance().executeUpdateQuery(query);
		}	
		
	}

}
