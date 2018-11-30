package eg.edu.alexu.csd.oop.db.cs01;

import java.sql.SQLException;

public class OurMain {
	public static void main(String[] args) throws SQLException {

//		GeneralParser.getInstance().parse("");
		
		GeneralParser.getInstance().parse("CREATE DATABASE school;");
		GeneralParser.getInstance().parse("create table s4 (names varchar , coursesNo int);");
		GeneralParser.getInstance().parse("insert into s4 values(bebo, 6);");
		
//		GeneralParser.getInstance().parse("CREATE TABLE S (ID int, FirstN varchar, LastN varchar, City varchar);");
//		GeneralParser.getInstance().parse(
//				"INSERT INTO S (id, LastN ,FirstN ,City) VALUES (4 ,'Asharf', 'Abanoub', 'Alex');");
//		GeneralParser.getInstance().parse("CREATE TABLE S2 (ID int, FirstN varchar, LastN varchar, City varchar);");
//		GeneralParser.getInstance().parse(
//				"INSERT INTO S2 (LastN ,FirstN ,City) VALUES ('mico', 'said', 'Alex');");
//		GeneralParser.getInstance().parse("drop TABLE s2");	
//		GeneralParser.getInstance().parse("UPDATE s SET id = 4, City= 'Frankfurt' WHERE ID >= 2;");
		GeneralParser.getInstance().parse("DELETE FROM s4;");
//		for(int i=0;i<GeneralParser.getInstance().getCurrentData().length;i++) {
//			for(int j=0;j<GeneralParser.getInstance().getCurrentData()[i].length;j++)
//				System.out.println(GeneralParser.getInstance().getCurrentData()[i][j]);
//		}
		/**
		 * CREATE TABLE Persons (PersonID int,LastName varchar,FirstName varchar,City varchar); 
		 * INSERT INTO Persons (PersonID ,LastName ,FirstName ,City) VALUES
		 * (5, 'Asharf', 'Abanoub', 'Alex'); DROP TABLE Persons;
		 */

	}
}
