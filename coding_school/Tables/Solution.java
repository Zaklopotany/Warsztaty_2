package coding_school.Tables;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Solution {
	private int id;
	private DateTime created;
	private DateTime updated;
	private String description;
	private int excersise_id;
	private BigInteger usersId;

	//constructor
	public Solution() {
		this.created = new DateTime();
		this.updated = new DateTime();
		setUsersId("0");
	}
	public Solution(String created, String description, int excersise_id, String usersId) {
	//TODO creatory 3 data i opis
		//TODO sprawdzic update
		setCreated(created);
		setUpdated(created);
		setCreated(created); // czy ma być czy docelowo
		setDescription(description);
		setExcersiseId(excersise_id);
		setUsersId(usersId);
	}
	
	public Solution (String description, int excersise_id, String usersId) {
		this.created = new DateTime();
		this.updated = new DateTime();
		setDescription(description);
		setExcersiseId(excersise_id);
		setUsersId(usersId);
	}
	public String getCreated() {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		String created = dtf.print(this.created);
		return created;
	}

	public Solution setCreated(String dateTimeCreated) {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime created = format.parseDateTime(dateTimeCreated);
		this.created = created;
		return this;
	}

	public String getUpdated() {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		String updated = dtf.print(this.updated);
		return updated;
	}

	public Solution setUpdated(String dateTimeUpdated) {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime updated = format.parseDateTime(dateTimeUpdated);
		this.updated = updated;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Solution setDescription(String description) {
		this.description = description;
		return this;
	}

	public int getExcersiseId() {
		return excersise_id;
	}

	public Solution setExcersiseId(int excersise_id) {
		this.excersise_id = excersise_id;
		return this;

	}

	public String getUsersId() {
		return usersId.toString();
	}

	public Solution setUsersId(String usersId) {
		this.usersId = new BigInteger(usersId);
		return this;
	}

	public int getId() {
		return id;
	}

	private Solution setId(int id) {
		this.id = id;
		return this;
	}
	
	// communcation with DB
	// load All excersises
	public static Solution[] loadAll(Connection con) throws SQLException {
		ArrayList<Solution> solution = new ArrayList<Solution>();
		Statement stat = con.createStatement();
		ResultSet rs = stat.executeQuery("Select * from solution;");

		while (rs.next()) {
			Solution tempSol = new Solution();
			tempSol.setId(rs.getInt("id"));
			tempSol.setCreated(rs.getString("created"));
			tempSol.setUpdated(rs.getString("updated"));
			tempSol.setDescription(rs.getString("description"));
			tempSol.setExcersiseId(rs.getInt("excersise_id"));
			tempSol.setUsersId(rs.getString("users_id"));

			solution.add(tempSol);

		}
		Solution[] solArr = new Solution[solution.size()];
		solution.toArray(solArr);

		return solArr;

	}

	// load excersise by id

	public static Solution loadById(Connection con, int id) throws SQLException {
		String sql = "Select * from solution where id = ?;";
		PreparedStatement prepStat = con.prepareStatement(sql);
		prepStat.setInt(1, id);
		ResultSet rs = prepStat.executeQuery();

		Solution tempSol = null;
		while (rs.next()) {
			tempSol = new Solution();
			tempSol.setId(rs.getInt("id"));
			tempSol.setCreated(rs.getString("created"));
			tempSol.setUpdated(rs.getString("updated"));
			tempSol.setDescription(rs.getString("description"));
			tempSol.setExcersiseId(rs.getInt("excersise_id"));
			tempSol.setUsersId(rs.getString("users_id"));

		}

		return tempSol;

	}
	//save or update
	public void SaveToDB(Connection con) throws SQLException {

		if (this.getId() == 0) {
			String sql = "insert into solution (created,updated,description,excersise_id,users_id)"
					+ "values (null,?,?,?,?,?);";
			String[] genCol = { "id" };
			PreparedStatement prepStat = con.prepareStatement(sql, genCol);

			prepStat.setString(1, this.getCreated());
			prepStat.setString(2, this.getUpdated());
			prepStat.setString(3, this.getDescription());
			prepStat.setInt(4, this.getExcersiseId());
			prepStat.setString(5, this.getUsersId());

			prepStat.executeUpdate();
			ResultSet rs = prepStat.getGeneratedKeys();

			if (rs.next()) {
				this.setId(rs.getInt(1));
			}
		} else {
			String sql = "Update solution set updated=?, "
					+ "description=?, excersise_id=?, users_id=? where id = ?";
			PreparedStatement prepStat = con.prepareStatement(sql);
		//TODO MECHANIZM AkTulazcji nowa DATA
			prepStat.setString(1, this.getUpdated());
			prepStat.setString(2, this.getDescription());
			prepStat.setInt(3, this.getExcersiseId());
			prepStat.setString(4, this.getUsersId());
			prepStat.setInt(5, this.getId());
			
			prepStat.executeUpdate();

		}

	}

	public void delete(Connection con) throws SQLException{
		if (this.getId() != 0) {
			String sql = "delete from solution where id = ?";
			PreparedStatement prepStat = con.prepareStatement(sql);
			prepStat.setInt(1, this.getId());
			
			prepStat.executeUpdate();
		}
	}
	
	public static Solution[] loadAllByExcersiseId(Connection con, int id) throws SQLException {
		String sql ="select s.excersise_id, s.users_id, s.created,"
				+ "s.updated,s.description "
				+ "from solution s join excersise e "
				+ "on s.excersise_id = e.id "
				+ "where e.id = ? "
				+ "order by s.created desc";
		
		ArrayList<Solution> tempSolList = new ArrayList<Solution>();
		
		PreparedStatement prepStat = con.prepareStatement(sql);
		prepStat.setInt(1, id);
		ResultSet rs = prepStat.executeQuery();
		
		while (rs.next()) {
			Solution tempSol = new Solution();
			tempSol.setCreated(rs.getString("created"));
			tempSol.setUpdated(rs.getString("updated"));
			tempSol.setDescription(rs.getString("description"));
			tempSol.setExcersiseId(rs.getInt("excersise_id"));
			tempSol.setUsersId(rs.getString("users_id"));
			
			tempSolList.add(tempSol);
		}
		
		Solution[] solArr = new Solution[tempSolList.size()];
		tempSolList.toArray(solArr);
		
		return solArr;
		
	}

	// cnovert DateTime date = DateTime.parse("04/02/2011 20:27:05",
	// DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"));

	// time do date

	// DateTimeFormat.forPattern("dd/MM/yyyy
	// HH:mm:ss").parseDateTime("04/02/2011 20:27:05");

}
