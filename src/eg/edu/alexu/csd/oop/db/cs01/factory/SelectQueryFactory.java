package eg.edu.alexu.csd.oop.db.cs01.factory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.cs01.OurSql;
import eg.edu.alexu.csd.oop.db.cs01.condition.LogicalCondition;
import eg.edu.alexu.csd.oop.db.cs01.jdbc.OurLogger;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;
import eg.edu.alexu.csd.oop.db.cs01.queries.IQuery;
import eg.edu.alexu.csd.oop.db.cs01.queries.SelectFrom;

public class SelectQueryFactory extends OurQueryFactory {

	// Singleton pattern.
	private static SelectQueryFactory instance;

	private SelectQueryFactory() {
		// Creating regexes for queries.
		final String selectAllFromTablePattern = "(?i)\\bselect\\b \\* (?i)\\bfrom\\b (\\w+) ?(((?i)\\bwhere\\b) ?([\\s\\w()><!='\"]+))? ?;? ?";
		final String selectColumnFromTablePattern = "(?i)\\bselect\\b ([\\w,\\s]+) (?i)\\bfrom\\b (\\w+) ?(((?i)\\bwhere\\b) ?([\\s\\w()><!='\"]+))? ?;? ?";		// Adding regex-es to the ArrayList.
		// Adding regex-es to the ArrayList.
		allPatternStrings.add(selectAllFromTablePattern);// 0.
		allPatternStrings.add(selectColumnFromTablePattern);// 1.
	}

	public static SelectQueryFactory getInstance() {
		if (instance == null) {
			instance = new SelectQueryFactory();
		}
		return instance;
	}

	// carry all patterns.
	private ArrayList<String> allPatternStrings = new ArrayList<>();

	@Override
	public IQuery parse(String theQuery) throws SQLException {
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
		if (theMatchers.get(0).find()) {// select * from tabel_name where condition.
			if (OurSql.getInstance().getCurrentDataBase() == null) {
				OurLogger.error(this.getClass(), "There is no database selected");
				throw new SQLException("There is no database selected");
			}
			// group(1) is the name of the table.
			// Table.getInstance(theMatchers.get(0).group(1));
			Table.getInstance(theMatchers.get(0).group(1), OurSql.getInstance().getCurrentDataBase());
			// group(4) is the condition it may equals null.
			LogicalCondition selectAllFromTableCondition = new LogicalCondition(theMatchers.get(0).group(4));
			IQuery selectAllFromTableQuery = new SelectFrom(selectAllFromTableCondition);
			return selectAllFromTableQuery;
		} else if (theMatchers.get(1).find()) {// select column from tabel_name where
												// condition.
			if (OurSql.getInstance().getCurrentDataBase() == null) {
				OurLogger.error(this.getClass(), "There is no database selected");
				throw new SQLException("There is no database selected");
			}
			// group(2) is the table name.
			// Table.getInstance(theMatchers.get(1).group(2));
			Table.getInstance(theMatchers.get(1).group(2), OurSql.getInstance().getCurrentDataBase());
			// group(5) is the condition it may equals null.
			LogicalCondition selectColumnFromTableCondition = new LogicalCondition(theMatchers.get(1).group(5));
			// group(1) is the column name.
			String columns[] = theMatchers.get(1).group(1).split(" ?, ?");
			ArrayList<String> columnsList = new ArrayList<String>(Arrays.asList(columns));
			IQuery selectColumnFromTableQuery = new SelectFrom(columnsList, selectColumnFromTableCondition);
			return selectColumnFromTableQuery;
		}
		setTheMainDataBase("");
//		OurSql.getInstance().setCurrentDataBase("");
		return null;
	}

}
