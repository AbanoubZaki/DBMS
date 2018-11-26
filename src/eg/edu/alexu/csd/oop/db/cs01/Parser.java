package eg.edu.alexu.csd.oop.db.cs01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.cs01.modules.Table;
import eg.edu.alexu.csd.oop.dp.cs01.queries.CreateDatabase;
import eg.edu.alexu.csd.oop.dp.cs01.queries.CreateTable;
import eg.edu.alexu.csd.oop.dp.cs01.queries.DropDatabase;
import eg.edu.alexu.csd.oop.dp.cs01.queries.DropTable;
import eg.edu.alexu.csd.oop.dp.cs01.queries.IQuery;
import eg.edu.alexu.csd.oop.dp.cs01.queries.InsertInto;

public class Parser {

	// Singleton pattern.
	private static Parser instance;

	private Parser() {
	}

	public static Parser getInstance() {
		if (instance == null) {
			instance = new Parser();
		}
		return instance;
	}

	private String theMainDataBase = null;

	public IQuery parseQuery(String theQuery) {
		// Creating regex-es for queries.
		final String createDataBasePattern = "create database (\\w+) ?; ?";
		final String drobDataBasePattern = "drob database (\\w+) ?; ?";
		final String createTablePattern = "create table (\\w+) \\( ?(( ?\\w+ (int|varchar) ?,?)+)\\) ?; ?";
		final String drobTablePattern = "drob table (\\w+) ?; ?";
		final String insertIntoTableColumnsAndValues = "insert into (\\w+) (\\( ?(( ?\\w+ ?,?)+)\\)) values \\((( ?\\w+ ?,?)+)\\) ?; ?";
		final String insertIntoTableValuesOnly = "insert into (\\w+) values \\( ?(( ?\\w+ ?,?)+)\\) ?; ?";

		// Adding regex-es to the ArrayList.
		ArrayList<String> allPatternStrings = new ArrayList<>();
		allPatternStrings.add(createDataBasePattern);// 0
		allPatternStrings.add(drobDataBasePattern);// 1
		allPatternStrings.add(createTablePattern);// 2
		allPatternStrings.add(drobTablePattern);// 3
		allPatternStrings.add(insertIntoTableColumnsAndValues);// 4
		allPatternStrings.add(insertIntoTableValuesOnly);// 5

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
		 * spaces with one space and convert it all to lower case.
		 */
		theQuery = theQuery.replaceAll("( +)", " ");
		theQuery = theQuery.toLowerCase();
		
		//Creating the right query.
		if (theQuery.contains(";") && theMatchers.get(0).find()) {// if the query match create data base.
			IQuery createDataBaseQuery = new CreateDatabase(theMatchers.get(0).group(1), false);
			theMainDataBase = theMatchers.get(0).group(1);
			return createDataBaseQuery;
		} else if (theQuery.contains(";") && theMatchers.get(1).find()) {// if the query match drop data base.
			IQuery drobDataBaseQuery = new DropDatabase(theMatchers.get(1).group(1));
			return drobDataBaseQuery;
		} else if (theQuery.contains(";") && theMatchers.get(2).find()) {// if the query match create table.
			if (theMainDataBase == null) {
				System.out.println("There is NO DataBase selected");
				return null;
			}
			String[] coulmns;
			coulmns = theMatchers.get(2).group(2).split(" ?, ?");
			ArrayList<String> coulmnsName = new ArrayList<>();
			ArrayList<String> coulmnsType = new ArrayList<>();
			Pattern p = Pattern.compile("(\\w+) (\\w+)");
			for (int i = 0; i < coulmns.length; i++) {
				Matcher coulmnsNameAndTypeMatcher = p.matcher(coulmns[i]);
				if (coulmnsNameAndTypeMatcher.find()) {
					coulmnsName.add((String) coulmnsNameAndTypeMatcher.group(1));
					coulmnsType.add((String) coulmnsNameAndTypeMatcher.group(2));
				}
			}
			Table createTable = new Table(theMainDataBase, theMatchers.get(2).group(1), coulmnsName, coulmnsType);
			IQuery createTableQuery = new CreateTable(createTable);
			return createTableQuery;
		} else if (theQuery.contains(";") && theMatchers.get(3).find()) {// if the query match drop table.
			if (theMainDataBase == null) {
				System.out.println("There is NO DataBase selected");
				return null;
			}
			Table dropTable = new Table(theMainDataBase, theMatchers.get(3).group(1));
			IQuery dropTableQuery = new DropTable(dropTable);
			return dropTableQuery;
		} else if (theQuery.contains(";") && theMatchers.get(4).find()) {// if the query match insert Into Table Columns And Values.
			if (theMainDataBase == null) {
				System.out.println("There is NO DataBase selected");
				return null;
			}
			String[] coulmnsArray;
			String[] valuesArray;
			coulmnsArray = theMatchers.get(4).group(3).split(" ?, ?");
			valuesArray = theMatchers.get(4).group(5).split(" ?, ?");
			if (coulmnsArray.length != valuesArray.length) {
				System.out.println("could not prepare statement.");
				return null;
			}
			ArrayList<String> columnNames = new ArrayList<String>(Arrays.asList(coulmnsArray));
			ArrayList<String> values = new ArrayList<String>(Arrays.asList(valuesArray));
			Table insertIntoTableColumnsAndValuesTable = new Table(theMainDataBase, theMatchers.get(4).group(1));
			IQuery insertIntoTableColumnsAndValuesQuery = new InsertInto(insertIntoTableColumnsAndValuesTable, columnNames, values);
			return insertIntoTableColumnsAndValuesQuery;
		} else if (theQuery.contains(";") && theMatchers.get(5).find()) {// if the query match insert Into Table Values Only.
			if (theMainDataBase == null) {
				System.out.println("There is NO DataBase selected");
				return null;
			}
			String[] valuesArray;
			valuesArray = theMatchers.get(5).group(2).split(" ?, ?");
			ArrayList<String> values = new ArrayList<String>(Arrays.asList(valuesArray));
			Table insertIntoTableValuesOnlyTable = new Table(theMainDataBase, theMatchers.get(5).group(1));
			IQuery insertIntoTableValuesOnlyQuery = new InsertInto(insertIntoTableValuesOnlyTable, values);
			return insertIntoTableValuesOnlyQuery;
		}
		return null;
	}
}
