package eg.edu.alexu.csd.oop.db.cs01;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.dp.cs01.queries.IQuery;

public class OurMain {
	public static void main(String[] args) throws SQLException {

		GeneralParser.getInstance().parse("CREATE DATABASE school;");
		GeneralParser.getInstance().parse("CREATE TABLE S (ID int, FirstN varchar, LastN varchar, City varchar);");
		GeneralParser.getInstance().parse(
				"INSERT INTO S (ID ,LastName ,FirstName ,City) VALUES (5, 'Asharf', 'Abanoub', 'Alex');");
		GeneralParser.getInstance().parse("");
		GeneralParser.getInstance().parse("");
		GeneralParser.getInstance().parse("");
		
		/**
		 * CREATE TABLE Persons (PersonID int,LastName varchar,FirstName varchar,City varchar); 
		 * INSERT INTO Persons (PersonID ,LastName ,FirstName ,City) VALUES
		 * (5, 'Asharf', 'Abanoub', 'Alex'); DROP TABLE Persons;
		 */

	}
}
