package eg.edu.alexu.csd.oop.db.cs01;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.dp.cs01.queries.IQuery;

public class OurMain {
	public static void main(String[] args) throws SQLException {
		
		IQuery testQueries = Parser.getInstance().parseQuery("CREATE DATABASE bebo;");
		System.out.println(testQueries.execute1());
//		testQueries = Parser.getInstance().parseQuery(
//				"CREATE TABLE Persons (PersonID int,LastName varchar,FirstName varchar,City varchar);");
//		testQueries.execute1();
//		//write table.
//		FileManager.getInstance().createTable(Table.getInstance());
//		testQueries = Parser.getInstance().parseQuery("INSERT INTO Persons (PersonID ,LastName ,FirstName ,City) VALUES (5, 'Asharf', 'Abanoub', 'Alex');");
//		System.out.println(testQueries.execute2());
//		FileManager.getInstance().writeTable(Table.getInstance());
		
		testQueries = Parser.getInstance().parseQuery("CREATE DATABASE bony;");
		System.out.println(testQueries.execute1());
		testQueries = Parser.getInstance().parseQuery("DROP TABLE Persons;");
		System.out.println(testQueries.execute1());
//		
//		Row row = new Row(Table.getInstance());
//		RelationalCondition rc = new RelationalCondition("c=5");
//		RelationalSolver.getInstance().isRowSolvingCondition(row, rc);
	}
}
