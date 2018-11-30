package eg.edu.alexu.csd.oop.db.cs01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class OurMain {
	public static void main(String[] args) throws SQLException, IOException {

		System.out.println("Enter Queries :");
		
		while (true) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String query = br.readLine();
			if (query.equals("exit")) {
				break;
			}
			GeneralParser.getInstance().parse(query);
			if (GeneralParser.getInstance().getCurrentData() != null) {
				for(int i=0;i<GeneralParser.getInstance().getCurrentData().length;i++) {
					for(int j=0;j<GeneralParser.getInstance().getCurrentData()[i].length;j++)
						System.out.print(GeneralParser.getInstance().getCurrentData()[i][j]+" ");
					System.out.println();
				}
			}
		}
//		GeneralParser.getInstance().parse("");
		
//		GeneralParser.getInstance().parse("CREATE DATABASE school;");
//		GeneralParser.getInstance().parse("create table s4 (names varchar , coursesNo int, creditHours int);");
//		GeneralParser.getInstance().parse("insert into s4 values(bebo, 6 , 18);");
//		
//		GeneralParser.getInstance().parse("DELETE FROM s4;");
		
//		GeneralParser.getInstance().parse("CREATE TABLE S (ID int, FirstN varchar, LastN varchar, City varchar);");
//		GeneralParser.getInstance().parse(
//				"INSERT INTO S (id, LastN ,FirstN ,City) VALUES (4 ,'Asharf', 'Abanoub', 'Alex');");
//		GeneralParser.getInstance().parse("CREATE TABLE S2 (ID int, FirstN varchar, LastN varchar, City varchar);");
//		GeneralParser.getInstance().parse(
//				"INSERT INTO S2 (LastN ,FirstN ,City) VALUES ('mico', 'said', 'Alex');");
//		GeneralParser.getInstance().parse("drop TABLE s2");	
//		GeneralParser.getInstance().parse("UPDATE s SET id = 4, City= 'Frankfurt' WHERE ID >= 2;");
		
//		for(int i=0;i<GeneralParser.getInstance().getCurrentData().length;i++) {
//			for(int j=0;j<GeneralParser.getInstance().getCurrentData()[i].length;j++)
//				System.out.println(GeneralParser.getInstance().getCurrentData()[i][j]);
//		}
		/**
		 * CREATE TABLE Persons (PersonID int,LastName varchar,FirstName varchar,City varchar); 
		 * INSERT INTO Persons (PersonID ,LastName ,FirstName ,City) VALUES
		 * (5, 'Asharf', 'Abanoub', 'Alex'); DROP TABLE Persons;
		 */
//select * from logicalConditions where CustomerID = 2 and City = "alex"
	}
}
