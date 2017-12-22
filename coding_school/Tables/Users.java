package coding_school.Tables;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

public class Users {
	private BigInteger id;
	private String userName;
	private String email;
	private String password;
	private int personGroupId;
//TODO dodać kreatoryINTEGER
	//incjalizacja BIG 
	public Users() {
		this.id = new BigInteger("0");
	}
	public Users(String userName, String email, String password, int personGroupId) {
		this.id = new BigInteger("0");
		setUserName(userName);
		setEmail(email);
		setPassword(password);
		setPersonGroupId(personGroupId);
	}
	
	//settery getteery
	public String getUserName() {
		return userName;
	}

	public Users setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Users setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public Users setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
		return this;
	}

	public int getPersonGroupId() {
		return personGroupId;
	}

	public Users setPersonGroupId(int personGroupId) {
		this.personGroupId = personGroupId;
		return this;
	}

	public String getId() {
		return id.toString();
	}
	private Users setId (String id) {
		this.id = new BigInteger(id);
		return this;
	}
	
	//database function
	
	public static Users[] loadAll(Connection con) throws SQLException {
		ArrayList<Users> users = new ArrayList<Users>();
		Statement stat = con.createStatement();
		ResultSet rs = stat.executeQuery("Select * from users;");

		while (rs.next()) {
			Users tempUsr = new Users();
			tempUsr.setId(rs.getString("id"));
			tempUsr.setUserName(rs.getString("username"));
			tempUsr.setEmail(rs.getString("email"));
			tempUsr.password = rs.getString("password");
			tempUsr.setPersonGroupId(rs.getInt("person_group_id"));

			users.add(tempUsr);

		}
		Users[] usersArr = new Users[users.size()];
		users.toArray(usersArr);

		return usersArr;

	}

	// load excersise by id

	public static Users loadById(Connection con, String id) throws SQLException {
		String sql = "Select * from users where id = ?;";
		PreparedStatement prepStat = con.prepareStatement(sql);
		prepStat.setString(1, id);
		ResultSet rs = prepStat.executeQuery();

		Users tempUsr = null;
		while (rs.next()) {
			tempUsr = new Users();
			tempUsr.setId(rs.getString("id"));
			tempUsr.setUserName(rs.getString("username"));
			tempUsr.setEmail(rs.getString("email"));
			tempUsr.password = rs.getString("password");
			tempUsr.setPersonGroupId(rs.getInt("person_group_id"));
			
		}

		return tempUsr;

	}
	//save or update
	public void SaveToDB(Connection con) throws SQLException {
		
		if (this.getId().equals("0")) {
			String sql = "insert into users values (null,?,?,?,?);";
			String[] genCol = { "id" };
			PreparedStatement prepStat = con.prepareStatement(sql, genCol);

			prepStat.setString(1, this.getUserName());
			prepStat.setString(2, this.getEmail());
			prepStat.setString(3, this.getPassword());
			prepStat.setInt(4, this.getPersonGroupId());

			prepStat.executeUpdate();
			ResultSet rs = prepStat.getGeneratedKeys();

			if (rs.next()) {
				this.setId(rs.getString(1));
			}
		} else {
			String sql = "Update users set username=?, email=?,"
					+ "password=?, person_group_id=? where id = ?";
			PreparedStatement prepStat = con.prepareStatement(sql);
			prepStat.setString(1, this.getUserName());
			prepStat.setString(2, this.getEmail());
			prepStat.setString(3, this.getPassword());
			prepStat.setInt(4, this.getPersonGroupId());
			prepStat.setString(5, this.getId());
			
			prepStat.executeUpdate();

		}

	}

	public void delete(Connection con) throws SQLException{
		if (!this.getId().equals("0")) {
			String sql = "delete from users where id = ?;";
			PreparedStatement prepStat = con.prepareStatement(sql);
			prepStat.setString(1, this.getId());
			
			prepStat.executeUpdate();
		}
	}
	
	//TODO zastanowić się nad setterami i getteramoi big inta czy zostawić jako strring czy zastosować funkcje result seta
	
	public static Users[] loadAllByGroupId(Connection con, int id) throws SQLException{
		String sql ="Select u.id, u.username, u.email "
				+ "from users u join user_group ug "
				+ "on ug.id = u.person_group_id "
				+ "where ug.id = ?;";
		ArrayList<Users> tempUsersList = new ArrayList<Users>();
		
		PreparedStatement prepStat = con.prepareStatement(sql);
		prepStat.setInt(1, id);
		
		ResultSet rs = prepStat.executeQuery();
		
		while(rs.next()) {
			Users tempUser = new Users();
			tempUser.setId(rs.getString("id"));
			tempUser.setUserName(rs.getString("username"));
			tempUser.setEmail(rs.getString("email"));
			
			tempUsersList.add(tempUser);
		}
		
		Users[] tempUsersArr = new Users[tempUsersList.size()];
		tempUsersList.toArray(tempUsersArr);
		
		return tempUsersArr;
	}
	
	@Override
	public String toString() {
		StringBuilder strB = new StringBuilder();
		strB.append(this.getId()).append(" ").append(this.getUserName())
		.append(" ").append(this.getEmail()).append(" ")
		.append(this.getPersonGroupId());
		
		return strB.toString();
	}
}
