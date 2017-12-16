package warsztat2;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

	public static void main (String[] args) {
		
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat?useSSL=false","root","coderslab")) {
			User[] users = User.loadAll(con);
			
			for (User u :users) {
				System.out.println(u.toString());
			}
			
//			User user = new User("Lame", "Blame", "kasztan@@ewp,");
//			user.saveToDB(con);
//			System.out.println(user.toString());
			
			users[0].setEmail("Nowy@email.pl");
			users[0].saveToDB(con);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
}
