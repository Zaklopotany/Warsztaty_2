package coding_school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Excersise {
	private String title;
	private int id;
	private String description;

	// creator
	public Excersise() {

	}
	

	public Excersise(String title, String description) {
		super();
		setTitle(title);
		setDescription(description);

	}

	// getters and setters
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	private Excersise setId(int id) {
		this.id = id;
		return this;
	}

	// communcation with DB
	// load All excersises
	public static Excersise[] loadAll(Connection con) throws SQLException {
		ArrayList<Excersise> excersise = new ArrayList<Excersise>();
		Statement stat = con.createStatement();
		ResultSet rs = stat.executeQuery("Select * from excersise;");

		while (rs.next()) {
			Excersise tempExce = new Excersise();
			tempExce.setId(rs.getInt("id"));
			tempExce.setTitle(rs.getString("title"));
			tempExce.setDescription(rs.getString("description"));

			excersise.add(tempExce);

		}
		Excersise[] exceArr = new Excersise[excersise.size()];
		excersise.toArray(exceArr);

		return exceArr;

	}

	// load excersise by id

	public static Excersise loadById(Connection con, int id) throws SQLException {
		String sql = "Select * from excersise where id = ?;";
		PreparedStatement prepStat = con.prepareStatement(sql);
		prepStat.setInt(1, id);
		ResultSet rs = prepStat.executeQuery();

		Excersise tempExce = null;
		while (rs.next()) {
			tempExce = new Excersise();
			tempExce.setId(rs.getInt("id"));
			tempExce.setDescription(rs.getString("description"));
			tempExce.setTitle(rs.getString("title"));

		}

		return tempExce;

	}
	//save or update
	public void SaveToDB(Connection con) throws SQLException {

		if (this.getId() == 0) {
			String sql = "insert into excersise (title,description) values (?,?);";
			String[] genCol = { "id" };
			PreparedStatement prepStat = con.prepareStatement(sql, genCol);

			prepStat.setString(1, this.getTitle());
			prepStat.setString(2, this.getDescription());

			prepStat.executeUpdate();
			ResultSet rs = prepStat.getGeneratedKeys();

			if (rs.next()) {
				this.setId(rs.getInt(1));
			}
		} else {
			String sql = "Update excersise set title=?, description=? where id = ?";
			PreparedStatement prepStat = con.prepareStatement(sql);
			prepStat.setString(1, this.getTitle());
			prepStat.setString(2, this.getDescription());
			prepStat.setInt(3, this.getId());

			prepStat.executeUpdate();

		}

	}

	public void delete(Connection con) throws SQLException{
		if (this.getId() != 0) {
			String sql = "delete from excersise where id = ?";
			PreparedStatement prepStat = con.prepareStatement(sql);
			prepStat.setInt(1, this.getId());
			
			prepStat.executeUpdate();
		}
	}

}
