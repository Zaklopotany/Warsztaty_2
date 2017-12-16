package warsztat2;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import coding_school.Solution;

public class Main {

	public static void main (String[] args) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime dt = new DateTime();
		String created = dtf.print(dt);
		System.out.println(created);
		Solution sol = new Solution();
		
		
//		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat?useSSL=false","root","coderslab")) {
//			User[] users = User.loadAll(con);
//			
//			for (User u :users) {
//				System.out.println(u.toString());
//			}
//			
////			User user = new User("Lame", "Blame", "kasztan@@ewp,");
////			user.saveToDB(con);
////			System.out.println(user.toString());
//			
//			users[0].setEmail("Nowy@email.pl");
//			users[0].saveToDB(con);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		
	}
	
}
