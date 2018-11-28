package eg.edu.alexu.csd.oop.db.cs01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.cs01.condition.RelationalCondition;
import eg.edu.alexu.csd.oop.db.cs01.modules.Table;
import eg.edu.alexu.csd.oop.dp.cs01.queries.CreateDatabase;
import eg.edu.alexu.csd.oop.dp.cs01.queries.CreateTable;
import eg.edu.alexu.csd.oop.dp.cs01.queries.DeleteFrom;
import eg.edu.alexu.csd.oop.dp.cs01.queries.DropDatabase;
import eg.edu.alexu.csd.oop.dp.cs01.queries.DropTable;
import eg.edu.alexu.csd.oop.dp.cs01.queries.IQuery;
import eg.edu.alexu.csd.oop.dp.cs01.queries.InsertInto;
import eg.edu.alexu.csd.oop.dp.cs01.queries.SelectFrom;
import eg.edu.alexu.csd.oop.dp.cs01.queries.UpdateSet;

public class Parser {

	// Singleton pattern.
	private static Parser instance;

	private Parser() {
		theMainDataBase = null;
	}

	public static Parser getInstance() {
		if (instance == null) {
			instance = new Parser();
		}
		return instance;
	}

	private String theMainDataBase;

	public IQuery parseQuery(String theQuery) {
		// Creating regex-es for queries.
		final String createDataBasePattern = "(?i)\\bcreate\\b (?i)\\bdatabase\\b (\\w+) ?; ?";
		final String drobDataBasePattern = "(?i)\\bdrob\\b (?i)\\bdatabase\\b (\\w+) ?; ?";
		final String createTablePattern = "(?i)\\bcreate\\b (?i)\\btable\\b (\\w+) \\( ?(( ?\\w+ (int|varchar) ?,?)+)\\) ?; ?";
		final String drobTablePattern = "(?i)\\bdrob\\b (?i)\\btable\\b (\\w+) ?; ?";
		final String insertIntoTableColumnsAndValuesPattern = "(?i)\\binsert\\b (?i)\\binto\\b (\\w+) (\\( ?(( ?\\w+ ?,?)+)\\)) (?i)\\bvalues\\b \\((( ?['\"]? ?\\w+ ?['\"]? ?,?)+)\\) ?; ?";
		final String insertIntoTableValuesOnlyPattern = "(?i)\\binsert\\b (?i)\\binto\\b (\\w+) (?i)\\bvalues\\b \\( ?(( ?['\"]? ?\\w+ ?['\"]? ?,?)+)\\) ?; ?";
		final String selectAllFromTablePattern = "(?i)\\bselect\\b \\* (?i)\\bfrom\\b (\\w+) ?(((?i)\\bwhere\\b) ?(((?i)\\bnot\\b)? ?([^;\\s]) ?(([!=><]{1,2}) ?([^;\\s]+))? ?(((?i)\\bor\\b|(?i)\\band\\b) ?((?i)\\bnot\\b)? ?([^;\\s]+) ?(([!=><]{1,2}) ?([^;\\s]+))? ?)?))? ?; ?";
		final String selectColumnFromTablePattern = "(?i)\\bselect\\b (\\w+) (?i)\\bfrom\\b (\\w+) ?(((?i)\\bwhere\\b) ?(((?i)\\bnot\\b)? ?([^;\\s]+) ?(([!=><]{1,2}) ?([^;\\s]+))? ?(((?i)\\bor\\b|(?i)\\band\\b) ?((?i)\\bnot\\b)? ?([^;\\s]+) ?(([!=><]{1,2}) ?([^;\\s]+))? ?)?))? ?; ?";
		final String updateTableSetColumnPattern = "(?i)\\bupdate\\b (\\w+) (?i)\\bset\\b (( ?(\\w+) = ['\"]? ?(\\w)+ ?['\"]? ?,?)+)(((?i)\\bwhere\\b) ?(((?i)\\bnot\\b)? ?([^;\\s]+) ?(([!=><]{1,2}) ?([^;\\s]+))? ?(((?i)\\bor\\b|(?i)\\band\\b) ?((?i)\\bnot\\b)? ?([^;\\s]+) ?(([!=><]{1,2}) ?([^;\\s]+))? ?)?))? ?; ?";
		final String deleteFromTablePattern = "(?i)\\bdelete\\b (?i)\\bfrom\\b (\\w+) ?(((?i)\\bwhere\\b) ?(((?i)\\bnot\\b)? ?([^;\\s]+) ?(([!=><]{1,2}) ?([^;\\s]+))? ?(((?i)\\bor\\b|(?i)\\band\\b) ?((?i)\\bnot\\b)? ?([^;\\s]+) ?(([!=><]{1,2}) ?([^;\\s]+))? ?)?))? ?; ?";

		// Adding regex-es to the ArrayList.
		ArrayList<String> allPatternStrings = new ArrayList<>();
		allPatternStrings.add(createDataBasePattern);// 0.
		allPatternStrings.add(drobDataBasePattern);// 1.
		allPatternStrings.add(createTablePattern);// 2.
		allPatternStrings.add(drobTablePattern);// 3.
		allPatternStrings.add(insertIntoTableColumnsAndValuesPattern);// 4.
		allPatternStrings.add(insertIntoTableValuesOnlyPattern);// 5.
		allPatternStrings.add(selectAllFromTablePattern);// 6.
		allPatternStrings.add(selectColumnFromTablePattern);// 7.
		allPatternStrings.add(updateTableSetColumnPattern);// 8.
		allPatternStrings.add(deleteFromTablePattern);// 9.
		// ArrayLists for patterns and matchers.
		ArrayList<Pattern> thePatterns = new ArrayList<>();
		ArrayList<Matcher> theMatchers = new ArrayList<>();

		// Filling patterns and matchers ArrayLists.
		for (int i = 0; i < allPatternStrings.size(); i++) {
			Pattern pattern;
			Matcher matcher;
			pattern = Pattern.compile(allPatternStrings.get(i));
			matcher = pattern.matcher(theQuery);
			thePatterns.add(pattern);
			theMatchers.add(matcher);
		}

		/**
		 * Editing the given string (the Query) to match the regex by replacing multiple
		 * spaces with one space. To CREATE you need names as they were entered. on
		 * DROP, INSERT INTO, SELECT, UPDATE, DELETE queries you need to lower case them
		 * to compare with the lower cased original names. so I will return them as they
		 * were entered.
		 */
		theQuery = theQuery.replaceAll("( +)", " ");

		// Creating the right query.
		if (theQuery.contains(";") && theMatchers.get(0).find()) {// if the query match create data base.
			IQuery createDataBaseQuery = new CreateDatabase(theMatchers.get(0).group(1).toLowerCase(), false);
			// group(1) is the name of the data base.
			theMainDataBase = theMatchers.get(0).group(1).toLowerCase();
			return createDataBaseQuery;
		} else if (theQuery.contains(";") && theMatchers.get(1).find()) {// if the query match drop data base.
			// group(1) is the name of the data base.
			IQuery drobDataBaseQuery = new DropDatabase(theMatchers.get(1).group(1).toLowerCase());
			theMainDataBase = null;
			return drobDataBaseQuery;
		} else if (theQuery.contains(";") && theMatchers.get(2).find()) {// if the query match create table.
			if (theMainDataBase == null) {
				System.out.println("There is NO DataBase selected");
				return null;
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
			Table.getInstance(theMatchers.get(2).group(1));
			Table.getInstance().setDatabaseName(theMainDataBase);
			Table.getInstance().setAllColumnNamesAndTypes(columnsName, columnsType);
			IQuery createTableQuery = new CreateTable(Table.getInstance());
			return createTableQuery;
		} else if (theQuery.contains(";") && theMatchers.get(3).find()) {// if the query match drop table.
			if (theMainDataBase == null) {
				System.out.println("There is NO DataBase selected");
				return null;
			}
			// group(1) is the name of the table.
			Table.getInstance(theMatchers.get(3).group(1));
			Table.getInstance().setDatabaseName(theMainDataBase);
			IQuery dropTableQuery = new DropTable(Table.getInstance());
			return dropTableQuery;
		} else if (theQuery.contains(";") && theMatchers.get(4).find()) {// if the query match insert Into Table Columns
																			// And Values.
			if (theMainDataBase == null) {
				System.out.println("There is NO DataBase selected");
				return null;
			}
			String[] columnsArray;
			String[] valuesArray;
			// group(3) is column names string.
			columnsArray = theMatchers.get(4).group(3).split(" ?, ?");
			// group(5) is values of each column string.
			valuesArray = theMatchers.get(4).group(5).split(" ?, ?");
			if (columnsArray.length != valuesArray.length) {
				System.out.println("could not prepare statement.");
				return null;
			}
			// converting array to ArrayList.
			ArrayList<String> columnNames = new ArrayList<String>(Arrays.asList(columnsArray));
			// converting array to ArrayList.
			ArrayList<String> values = new ArrayList<String>(Arrays.asList(valuesArray));
			// group(1) is the name of the table.
			Table.getInstance(theMatchers.get(4).group(1));
			Table.getInstance().setDatabaseName(theMainDataBase);
			IQuery insertIntoTableColumnsAndValuesQuery = new InsertInto(Table.getInstance(), columnNames, values);
			return insertIntoTableColumnsAndValuesQuery;
		} else if (theQuery.contains(";") && theMatchers.get(5).find()) {// if the query match insert Into Table Values
																			// Only.
			if (theMainDataBase == null) {
				System.out.println("There is NO DataBase selected");
				return null;
			}
			String[] valuesArray;
			// group(2) is a string contains all values for the columns.
			valuesArray = theMatchers.get(5).group(2).split(" ?, ?");
			// converting array to ArrayList.
			ArrayList<String> values = new ArrayList<String>(Arrays.asList(valuesArray));
			// group(1) is the name of the table.
			Table.getInstance(theMatchers.get(5).group(1));
			Table.getInstance().setDatabaseName(theMainDataBase);
			IQuery insertIntoTableValuesOnlyQuery = new InsertInto(Table.getInstance(), values);
			return insertIntoTableValuesOnlyQuery;
		} else if (theQuery.contains(";") && theMatchers.get(6).find()) {// select * from tabel_name where condition.
			if (theMainDataBase == null) {
				System.out.println("There is NO DataBase selected");
				return null;
			}
			// group(1) is the name of the table.
			Table.getInstance(theMatchers.get(6).group(1));
			Table.getInstance().setDatabaseName(theMainDataBase);
			// group(4) is the condition it may equals null.
			RelationalCondition selectAllFromTableCondition = new RelationalCondition(theMatchers.get(6).group(4));
			IQuery selectAllFromTableQuery = new SelectFrom(Table.getInstance(), selectAllFromTableCondition);
			return selectAllFromTableQuery;
		} else if (theQuery.contains(";") && theMatchers.get(7).find()) {// select column from tabel_name where
																			// condition.
			if (theMainDataBase == null) {
				System.out.println("There is NO DataBase selected");
				return null;
			}
			// group(2) is the table name.
			Table.getInstance(theMatchers.get(7).group(2));
			Table.getInstance().setDatabaseName(theMainDataBase);
			// group(5) is the condition it may equals null.
			RelationalCondition selectColumnFromTableCondition = new RelationalCondition(theMatchers.get(7).group(5));
			// group(1) is the column name.
			IQuery selectColumnFromTableQuery = new SelectFrom(Table.getInstance(), theMatchers.get(7).group(1),
					selectColumnFromTableCondition);
			return selectColumnFromTableQuery;
		} else if (theQuery.contains(";") && theMatchers.get(8).find()) {// update tabel name set c1 = v1, ... where
																			// condition.
			if (theMainDataBase == null) {
				System.out.println("There is NO DataBase selected");
				return null;
			}
			String[] sets;
			ArrayList<String> columnNames = new ArrayList<>();
			ArrayList<String> values = new ArrayList<>();
			// pattern to split column name from the new value.
			String setsPattern = " ?(\\w+) ?= ?(['\"]? ?(\\w+) ?['\"]) ?";
			// group(2) is the all of the sets.
			sets = theMatchers.get(8).group(2).split(" ?, ?");
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
			Table.getInstance(theMatchers.get(8).group(1));
			Table.getInstance().setDatabaseName(theMainDataBase);
			// group(8) is the condition it may equals null.
			RelationalCondition updateTableSetColumnCondition = new RelationalCondition(theMatchers.get(8).group(8));
			IQuery updateTableSetColumnQuery = new UpdateSet(Table.getInstance(), columnNames, values,
					updateTableSetColumnCondition);
			return updateTableSetColumnQuery;
		} else if (theQuery.contains(";") && theMatchers.get(9).find()) {// DELETE FROM table_name WHERE condition.
			if (theMainDataBase == null) {
				System.out.println("There is NO DataBase selected");
				return null;
			}
			// group(1) is the table name.
			Table.getInstance(theMatchers.get(9).group(1));
			Table.getInstance().setDatabaseName(theMainDataBase);
			// group(4) is the condition it may equals null.
			RelationalCondition deleteFromTableCondition = new RelationalCondition(theMatchers.get(9).group(4));
			IQuery deleteFromTableQuery = new DeleteFrom(Table.getInstance(), deleteFromTableCondition);
			return deleteFromTableQuery;
		}
		return null;
	}
}
