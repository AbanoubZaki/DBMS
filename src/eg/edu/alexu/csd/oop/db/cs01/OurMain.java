package eg.edu.alexu.csd.oop.db.cs01;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.dp.cs01.queries.IQuery;

public class OurMain {
	public static void main(String[] args) throws SQLException {

		GeneralParser.getInstance().parse("CREATE DATABASE school;");
		GeneralParser.getInstance().parse("CREATE TABLE S (ID int, FirstN varchar, LastN varchar, City varchar);");
//		GeneralParser.getInstance().parse(
//				"INSERT INTO S (LastN ,FirstN ,City) VALUES ('Asharf', 'Abanoub', 'Alex');");
//		GeneralParser.getInstance().parse("CREATE TABLE S2 (ID int, FirstN varchar, LastN varchar, City varchar);");
//		GeneralParser.getInstance().parse(
//				"INSERT INTO S2 (ID ,LastN ,FirstN ,City) VALUES (5, 'mico', 'said', 'Alex');");
//		GeneralParser.getInstance().parse("drop TABLE s2");	
		GeneralParser.getInstance().parse("SELECT * FROM s2;");
		for(int i=0;i<GeneralParser.getInstance().getCurrentData().length;i++) {
			for(int j=0;j<GeneralParser.getInstance().getCurrentData()[i].length;j++)
				System.out.println(GeneralParser.getInstance().getCurrentData()[i][j]);
		}
		/**
		 * CREATE TABLE Persons (PersonID int,LastName varchar,FirstName varchar,City varchar); 
		 * INSERT INTO Persons (PersonID ,LastName ,FirstName ,City) VALUES
		 * (5, 'Asharf', 'Abanoub', 'Alex'); DROP TABLE Persons;
		 */

	}
}
