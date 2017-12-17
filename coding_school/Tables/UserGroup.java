package coding_school.Tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserGroup {
	private int id;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}
	
	private UserGroup setId ( int id ) {
		this.id = id;
		return this;
	}
	
	// communcation with DB
		// load All excersises
		public static UserGroup[] loadAll(Connection con) throws SQLException {
			ArrayList<UserGroup> userGroup = new ArrayList<UserGroup>();
			Statement stat = con.createStatement();
			ResultSet rs = stat.executeQuery("Select * from user_group;");

			while (rs.next()) {
				UserGroup tempUsGr = new UserGroup();
				tempUsGr.setId(rs.getInt("id"));
				tempUsGr.setName(rs.getString("name"));

				userGroup.add(tempUsGr);

			}
			UserGroup[] userGroupArr = new UserGroup[userGroup.size()];
			userGroup.toArray(userGroupArr);

			return userGroupArr;

		}

		// load excersise by id

		public static UserGroup loadById(Connection con, int id) throws SQLException {
			String sql = "Select * from user_group where id = ?;";
			PreparedStatement prepStat = con.prepareStatement(sql);
			prepStat.setInt(1, id);
			ResultSet rs = prepStat.executeQuery();

			UserGroup tempUserGroup = null;
			while (rs.next()) {
				tempUserGroup = new UserGroup();
				tempUserGroup.setId(rs.getInt("id"));
				tempUserGroup.setName(rs.getString("name"));

			}

			return tempUserGroup;

		}
		//save or update
		public void SaveToDB(Connection con) throws SQLException {

			if (this.getId() == 0) {
				String sql = "insert into user_group (name) values (?);";
				String[] genCol = { "id" };
				PreparedStatement prepStat = con.prepareStatement(sql, genCol);

				prepStat.setString(1, this.getName());

				prepStat.executeUpdate();
				ResultSet rs = prepStat.getGeneratedKeys();

				if (rs.next()) {
					this.setId(rs.getInt(1));
				}
			} else {
				String sql = "Update user_group set name=? where id = ?";
				PreparedStatement prepStat = con.prepareStatement(sql);
				prepStat.setString(1, this.getName());
				prepStat.setInt(2, this.getId());
				
				prepStat.executeUpdate();

			}

		}

		public void delete(Connection con) throws SQLException{
			if (this.getId() != 0) {
				String sql = "delete from user_group where id = ?";
				PreparedStatement prepStat = con.prepareStatement(sql);
				prepStat.setInt(1, this.getId());
				
				prepStat.executeUpdate();
			}
		}
		

}
