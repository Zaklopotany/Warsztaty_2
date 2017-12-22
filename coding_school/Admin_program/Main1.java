package coding_school.Admin_program;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import coding_school.Tables.Users;

public class Main1 {
	public static String DBname = "coding_school";
	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBname + "?useSSL=false",
				"root", "coderslab")) {
			Users[] usersArr = Users.loadAll(con);
			printArrVer(usersArr);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		optionScanner: while (true) {
			String option = showOptions();
			switch (option) {
			case "edit":
				edit();
				break;
			case "delete":
				 delete();
				break;
			case "add":
				 add();
				break;
			case "quit":
				break optionScanner;

			}

		}

	}

	public static String showOptions() {
		String[] options = { "add - dodanie użytkownika", "edit - edycja użytkownika", "delete - usunięcie użykownika",
				"quit - zakończenie programu" };
		//TODO zoptymalizowac dla dowolnej wartości argument string a towrzy tabele add edit uit itd substring
		String[] optionsShort = { "add", "edit", "quit", "delete" };
		Arrays.sort(optionsShort);

		printArrVer(options);

		String temp = "";

		while (true) {
			temp = scan.nextLine();
			if (Arrays.binarySearch(optionsShort, temp) > -1) {
				break;
			} else {
				printArrVer(options);
			}

		}
		return temp;

	}

	// printing data
	public static void printArrVer(String[] stringArr) {

		System.out.println("Wprowadź wartość: ");
		for (int i = 0; i < stringArr.length; i++) {
			System.out.println(stringArr[i]);
		}
	}

	public static void printArrVer(Object[] objectArr) {

		for (int i = 0; i < objectArr.length; i++) {
			System.out.println(objectArr[i].toString());
		}
	}

	// get data
	public static String[] getData(String[] valueName) {
		String[] operStrArr = new String[valueName.length];

		for (int i = 0; i < valueName.length; i++) {
			System.out.println("insert: " + valueName[i]);
			operStrArr[i] = scan.nextLine();
		}

		return operStrArr;

	}

	// edit add delete
	public static void edit() {
		System.out.println("Wprowadź dane użytkownika : ");
		String[] columns = { "id", "username", "email", "password", "group_id" };
		String[] userValues = getData(columns);

		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBname + "?useSSL=false",
				"root", "coderslab")) {
			Users user = Users.loadById(con, userValues[0]);

			user.setUserName(userValues[1]);
			user.setEmail(userValues[2]);
			user.setPassword(userValues[3]);
			user.setPersonGroupId(Integer.parseInt(userValues[4]));

			user.SaveToDB(con);

			System.out.println("++====================++\nUpdated\n++====================++");

		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("Users Group does not exist");
		} catch (SQLException e) {
			System.out.println(e);
		}

	}
	
	public static void delete() {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBname + "?useSSL=false",
				"root", "coderslab")){
			String[] idValue = {"id"};
			idValue = getData(idValue);
			
			Users tempUser = Users.loadById(con, idValue[0]);
			tempUser.delete(con);
			
			System.out.println("++====================++\nDeleted\n++====================++");
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		} 
	}
	
	public static void add() {
		String[] columns = {"username", "email", "password", "group_id" };
		String[] userValues = getData(columns);

		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBname + "?useSSL=false",
				"root", "coderslab")) {
			Users user = new Users(userValues[0], userValues[1], userValues[2], Integer.parseInt(userValues[3]));
			
			user.SaveToDB(con);

			System.out.println("++====================++\nAdded\n++====================++");

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
