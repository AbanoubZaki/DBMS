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
import eg.edu.alexu.csd.oop.db.cs01.queries.DeleteFrom;
import eg.edu.alexu.csd.oop.db.cs01.queries.IQuery;
import eg.edu.alexu.csd.oop.db.cs01.queries.InsertInto;
import eg.edu.alexu.csd.oop.db.cs01.queries.UpdateSet;

public class UpdateQueryFactory extends OurQueryFactory {

	// Singleton pattern.
	private static UpdateQueryFactory instance;

	private UpdateQueryFactory() {
		// Creating regexes for queries.
		final String insertIntoTableColumnsAndValuesPattern = "(?i)\\bINSERT\\b\\s(?i)\\bINTO\\b\\s([\\d\\w]+)\\s?\\(([\\s\\w\\d,]*)\\)\\s?(?i)\\bVALUES\\b\\s?\\(([\\s\\w\\d\\W,']*)\\s?\\) ?;? ?";
		final String insertIntoTableValuesOnlyPattern = "(?i)\\binsert\\b (?i)\\binto\\b (\\w+) (?i)\\bvalues\\b ?\\( ?(( ?['\"]? ?[\\w\\s\\d\\W]+ ?['\"]? ?,?)+)\\) ?;? ?";
		final String updateTableSetColumnPattern = "(?i)\\bupdate\\b (\\w+) (?i)\\bset\\b (( ?(\\w+)( +)?= ?['\"]? ?(\\w)+ ?['\"]? ?,?)+) ?(((?i)\\bwhere\\b) ?([\\s\\w()><!='\"]+))? ?;? ?";
		final String deleteFromTablePattern = "(?i)\\bdelete\\b (?i)\\bfrom\\b (\\w+) ?(((?i)\\bwhere\\b) ?([\\s\\w()><!='\"]+))? ?;? ?";
		// Adding regex-es to the ArrayList.
		allPatternStrings.add(insertIntoTableColumnsAndValuesPattern);// 0.
		allPatternStrings.add(insertIntoTableValuesOnlyPattern);// 1.
		allPatternStrings.add(updateTableSetColumnPattern);// 2.
		allPatternStrings.add(deleteFromTablePattern);// 3.
	}

	public static UpdateQueryFactory getInstance() {
		if (instance == null) {
			instance = new UpdateQueryFactory();
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
		if (theMatchers.get(0).find()) {// if the query match insert Into Table Columns
			// And Values.
			if (OurSql.getInstance().getCurrentDataBase() == null) {
				OurLogger.error(this.getClass(), "There is no database selected");
				throw new SQLException("There is no database selected");
			}
			String[] columnsArray;
			String[] valuesArray;
			// group(3) is column names string.
			columnsArray = theMatchers.get(0).group(2).split(" ?, ?");
			// group(5) is values of each column string.
			valuesArray = theMatchers.get(0).group(3).split(" ?, ?");
			if (columnsArray.length != valuesArray.length) {
				OurLogger.error(this.getClass(), "Could not prepare statement.");
				throw new SQLException("Could not prepare statement.");
			}
			// converting array to ArrayList.
			ArrayList<String> columnNames = new ArrayList<String>(Arrays.asList(columnsArray));
			// converting array to ArrayList.
			ArrayList<String> values = new ArrayList<String>(Arrays.asList(valuesArray));
			// group(1) is the name of the table.
			// Table.getInstance(theMatchers.get(0).group(1));
			Table.getInstance(theMatchers.get(0).group(1), OurSql.getInstance().getCurrentDataBase());
			IQuery insertIntoTableColumnsAndValuesQuery = new InsertInto(columnNames, values);
			return insertIntoTableColumnsAndValuesQuery;
		} else if (theMatchers.get(1).find()) {// if the query match insert Into Table Values Only.
			if (OurSql.getInstance().getCurrentDataBase() == null) {
				OurLogger.error(this.getClass(), "There is no database selected");
				throw new SQLException("There is no database selected");
			}
			String[] valuesArray;
			// group(2) is a string contains all values for the columns.
			valuesArray = theMatchers.get(1).group(2).split(" ?, ?");
			// converting array to ArrayList.
			ArrayList<String> values = new ArrayList<String>(Arrays.asList(valuesArray));
			// group(1) is the name of the table.
			// Table.getInstance(theMatchers.get(1).group(1));
			Table.getInstance(theMatchers.get(1).group(1), OurSql.getInstance().getCurrentDataBase());
			IQuery insertIntoTableValuesOnlyQuery = new InsertInto(values);
			return insertIntoTableValuesOnlyQuery;
		} else if (theMatchers.get(2).find()) {// update tabel name set c1 = v1, ... where
			// condition.
			if (OurSql.getInstance().getCurrentDataBase() == null) {
				OurLogger.error(this.getClass(), "There is no database selected");
				throw new SQLException("There is no database selected");
			}
			// theQuery = theQuery.toLowerCase().replace("where", ",where");
			if (theQuery.toLowerCase().contains("where")) {
				String confirmingPatternString = "((?i)\\bUPDATE\\b\\s+)(\\w+)\\s+((?i)\\bSET\\b)\\s+([\\w\\s=,'\"]+)\\s+(((?i)\\bWHERE\\b)(.+))";
				Pattern confirmingPattern;
				Matcher confirmingatcher;
				confirmingPattern = Pattern.compile(confirmingPatternString);
				theQuery = theQuery.replace(";", "");
				confirmingatcher = confirmingPattern.matcher(theQuery);
				if (confirmingatcher.find()) {
					String[] sets;
					ArrayList<String> columnNames = new ArrayList<>();
					ArrayList<String> values = new ArrayList<>();
					// pattern to split column name from the new value.
					String setsPattern = " ?(\\w+) ?= ?(['\"]?([\\w\\s]+)['\"]?)";
					// group(4) is the all of the sets.
					sets = confirmingatcher.group(4).split(" ?, ?");
					// compiling sets pattern
					Pattern p = Pattern.compile(setsPattern);
					// filling ArrayLists columnNames & values.
					for (int i = 0; i < sets.length; i++) {
						Matcher setsMatcher = p.matcher(sets[i]);
						if (setsMatcher.find()) {
							columnNames.add(setsMatcher.group(1));
							values.add(setsMatcher.group(2));
						}
					}
					// group(1) is the table name.
					// Table.getInstance(theMatchers.get(2).group(1));
					Table.getInstance(confirmingatcher.group(2), OurSql.getInstance().getCurrentDataBase());
					// group(8) is the condition it may equals null.
					LogicalCondition updateTableSetColumnCondition = new LogicalCondition(confirmingatcher.group(7));
					IQuery updateTableSetColumnQuery = new UpdateSet(columnNames, values,
							updateTableSetColumnCondition);
					return updateTableSetColumnQuery;
				}
			} else {
				String[] sets;
				ArrayList<String> columnNames = new ArrayList<>();
				ArrayList<String> values = new ArrayList<>();
				// pattern to split column name from the new value.
				String setsPattern = " ?(\\w+) ?= ?(['\"]?([\\w\\s]+)['\"]?)";
				// group(2) is the all of the sets.
				sets = theMatchers.get(2).group(2).split(" ?, ?");
				// compiling sets pattern
				Pattern p = Pattern.compile(setsPattern);
				// filling ArrayLists columnNames & values.
				for (int i = 0; i < sets.length; i++) {
					Matcher setsMatcher = p.matcher(sets[i]);
					if (setsMatcher.find()) {
						columnNames.add(setsMatcher.group(1));
						values.add(setsMatcher.group(2));
					}
				}
				// group(1) is the table name.
				// Table.getInstance(theMatchers.get(2).group(1));
				Table.getInstance(theMatchers.get(2).group(1), OurSql.getInstance().getCurrentDataBase());
				// group(8) is the condition it may equals null.
				LogicalCondition updateTableSetColumnCondition = new LogicalCondition(theMatchers.get(2).group(9));
				IQuery updateTableSetColumnQuery = new UpdateSet(columnNames, values, updateTableSetColumnCondition);
				return updateTableSetColumnQuery;
			}

		} else if (theMatchers.get(3).find()) {// DELETE FROM table_name WHERE condition.
			if (OurSql.getInstance().getCurrentDataBase() == null) {
				OurLogger.error(this.getClass(), "There is no database selected");
				throw new SQLException("There is no database selected");
			}
			// group(1) is the table name.
			// Table.getInstance(theMatchers.get(3).group(1));
			Table.getInstance(theMatchers.get(3).group(1), OurSql.getInstance().getCurrentDataBase());
			// group(4) is the condition it may equals null.
			LogicalCondition deleteFromTableCondition = new LogicalCondition(theMatchers.get(3).group(4));
			IQuery deleteFromTableQuery = new DeleteFrom(deleteFromTableCondition);
			return deleteFromTableQuery;
		}
		setTheMainDataBase("");
//		OurSql.getInstance().setCurrentDataBase("");
		return null;
	}

}
