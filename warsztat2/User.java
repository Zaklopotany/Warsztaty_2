package warsztat2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

public class User {
	private int id;
	private String username;
	private String password;
	private String email;
	
	//wczytywanie z bazy
	public User () {
		
	}
	//Tworzenie nowego
	public User(String username, String password, String email) {
		super();
		setUsername(username);
		setPassword(password);
		setEmail(email);
	}
	
	
	public String getUsername() {
		return username;
	}
	
	public User setUsername(String username) {
		this.username = username;
		return this;
	}
	
	public String getPassword() {
		return password;
	}
	
	public User setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
		return this;
	}
	
	public String getEmail() {
		return email;
	}
	
	public User setEmail(String email) {
		this.email = email;
		return this;
	}
	
	public int getId() {
		return id;
	}
	private User setId(int id) {
		this.id = id;
		return this;
	}
	
	
	static public User[] loadAll(Connection con) throws SQLException {
		
		ArrayList<User> users = new ArrayList<User>();
		Statement stat = con.createStatement();
		ResultSet rs = stat.executeQuery("Select * from user");
		
		while(rs.next()) {
			User tempUser = new User();
			tempUser.setUsername(rs.getString("username"));
			tempUser.setEmail(rs.getString("email"));
			tempUser.password = rs.getString("password");
			tempUser.setId(rs.getInt("id"));
			users.add(tempUser);
		}
		
		User[] usersArr = new User[users.size()];
		users.toArray(usersArr);
		
		
		return usersArr;
	}
	
	public void saveToDB (Connection con) throws SQLException {
		
		if (this.getId() == 0) {
			//ad to DB
			String sql = "Insert into user ( username, email, password) values (?,?,?);";
			String[] generetedColums = {"id"};
			PreparedStatement prepStat = con.prepareStatement(sql,generetedColums);
			prepStat.setString(1, this.getUsername());
			prepStat.setString(2, this.getEmail());
			prepStat.setString(3, this.getPassword());
			
			prepStat.executeUpdate();
			
			ResultSet rs = prepStat.getGeneratedKeys();
			if (rs.next() ) {
				this.setId(rs.getInt(1));
			}
			
			
			
			
		} else {
//			update
			String sql = "update user set username = ?, email = ?, password = ? where id = ?;";

			PreparedStatement prepStat = con.prepareStatement(sql);
			prepStat.setString(1, this.getUsername());
			prepStat.setString(2, this.getEmail());
			prepStat.setString(3, this.getPassword());
			prepStat.setInt(4, this.getId());
			
			prepStat.executeUpdate();
			
			
		}
	}
	
	
	
	
	@Override
	public String toString() {
		StringBuilder strB = new StringBuilder();
		strB.append(this.getUsername()).append(" ").append(this.getEmail());
		return strB.toString();
	}
	
	

}
