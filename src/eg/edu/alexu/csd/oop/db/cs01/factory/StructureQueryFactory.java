package eg.edu.alexu.csd.oop.db.cs01.factory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.cs01.OurSql;
import eg.edu.alexu.csd.oop.db.cs01.jdbc.OurLogger;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;
import eg.edu.alexu.csd.oop.db.cs01.queries.CreateDatabase;
import eg.edu.alexu.csd.oop.db.cs01.queries.CreateTable;
import eg.edu.alexu.csd.oop.db.cs01.queries.DropDatabase;
import eg.edu.alexu.csd.oop.db.cs01.queries.DropTable;
import eg.edu.alexu.csd.oop.db.cs01.queries.IQuery;

public class StructureQueryFactory extends OurQueryFactory {

	// Singleton pattern.
	private static StructureQueryFactory instance;

	private StructureQueryFactory() {
		// Creating regexes for queries.
		final String createDataBasePattern = "(?i)\\bcreate\\b (?i)\\bdatabase\\b ([\\w\\"+System.getProperty("file.separator")+":-]+) ?;? ?";
		final String drobDataBasePattern = "(?i)\\bdrop\\b (?i)\\bdatabase\\b ([\\w\\"+System.getProperty("file.separator")+":-]+) ?;? ?";
		final String createTablePattern = "(?i)\\bcreate\\b (?i)\\btable\\b (\\w+) ?\\( ?(( ?\\w+ (int|varchar) ?,?)+) ?\\) ?;? ?";
		final String drobTablePattern = "(?i)\\bdrop\\b (?i)\\btable\\b (\\w+) ?;? ?";
		// Adding regex-es to the ArrayList.
		allPatternStrings.add(createDataBasePattern);// 0.
		allPatternStrings.add(drobDataBasePattern);// 1.
		allPatternStrings.add(createTablePattern);// 2.
		allPatternStrings.add(drobTablePattern);// 3.
	}

	public static StructureQueryFactory getInstance() {
		if (instance == null) {
			instance = new StructureQueryFactory();
		}
		return instance;
	}

	// carry all patterns.
	private ArrayList<String> allPatternStrings = new ArrayList<>();

	public IQuery parse(String theQuery) throws SQLException{
		// ArrayLists for patterns and matchers.
		ArrayList<Pattern> thePatterns = new ArrayList<>();
		ArrayList<Matcher> theMatchers = new ArrayList<>();
		/**
		 * Editing the given string (the Query) to match the regex by replacing multiple
		 * spaces with one space. To CREATE you need names as they were entered. on
		 * DROP, INSERT INTO, SELECT, UPDATE, DELETE queries you need to lower case them
		 * to compare with the lower cased original names. so I will return them as they
		 * were entered.
		 */
		theQuery = theQuery.replaceAll("( +)", " ");
		// Filling patterns and matchers ArrayLists.
		for (int i = 0; i < allPatternStrings.size(); i++) {
			Pattern pattern;
			Matcher matcher;
			pattern = Pattern.compile(allPatternStrings.get(i));
			matcher = pattern.matcher(theQuery);
			thePatterns.add(pattern);
			theMatchers.add(matcher);
		}
		// Creating the right query.
		if (theMatchers.get(0).find()) {// if the query match create data base.
			IQuery createDataBaseQuery = new CreateDatabase(theMatchers.get(0).group(1).toLowerCase());
			// group(1) is the name of the data base.
			setTheMainDataBase(theMatchers.get(0).group(1).toLowerCase());
			OurSql.getInstance().setCurrentDataBase(theMatchers.get(0).group(1).toLowerCase());
			return createDataBaseQuery;
		} else if (theMatchers.get(1).find()) {// if the query match drop data base.
			// group(1) is the name of the data base.
			IQuery drobDataBaseQuery = new DropDatabase(theMatchers.get(1).group(1).toLowerCase());
			setTheMainDataBase(null);
			OurSql.getInstance().setCurrentDataBase(null);
			return drobDataBaseQuery;
		} else if (theMatchers.get(2).find()) {// if the query match create table.
			if (OurSql.getInstance().getCurrentDataBase() == null) {
				OurLogger.error(this.getClass(), "There is no database selected");
				throw new SQLException("There is no database selected");
			}
			String[] columns;
			// group(2) is a string contains all columns beside their types example
			// (column_1 type, ...).
			columns = theMatchers.get(2).group(2).split(" ?, ?");
			ArrayList<String> columnsName = new ArrayList<>();
			ArrayList<String> columnsType = new ArrayList<>();
			Pattern p = Pattern.compile("(\\w+) (\\w+)");
			for (int i = 0; i < columns.length; i++) {
				Matcher columnsNameAndTypeMatcher = p.matcher(columns[i]);
				if (columnsNameAndTypeMatcher.find()) {
					// group(1) is column names.
					columnsName.add((String) columnsNameAndTypeMatcher.group(1));
					// group(2) is column types.
					columnsType.add((String) columnsNameAndTypeMatcher.group(2));
				}
			}
			// group(1) is the name of the table.
			// Table.getInstance(theMatchers.get(2).group(1));
			Table.getInstance(theMatchers.get(2).group(1), OurSql.getInstance().getCurrentDataBase());
			Table.getInstance().setAllColumnNamesAndTypes(columnsName, columnsType);
			IQuery createTableQuery = new CreateTable();
			return createTableQuery;
		} else if (theMatchers.get(3).find()) {// if the query match drop table.
			if (OurSql.getInstance().getCurrentDataBase() == null) {
				OurLogger.error(this.getClass(), "There is no database selected");
				throw new SQLException("There is no database selected");
			}
			// group(1) is the name of the table.
			// Table.getInstance(theMatchers.get(3).group(1));
			Table.getInstance(theMatchers.get(3).group(1), OurSql.getInstance().getCurrentDataBase());
			IQuery dropTableQuery = new DropTable();
			return dropTableQuery;
		}
		setTheMainDataBase("");
		OurSql.getInstance().setCurrentDataBase("");
		return null;
	}
}
