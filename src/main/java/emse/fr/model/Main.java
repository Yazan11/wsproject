package emse.fr.model;

import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.ws.rs.core.Response;

import emse.fr.database.DBClass;

//import com.sun.enterprise.module.ModulesRegistry;
//import com.sun.enterprise.module.bootstrap.StartupContext;
//import com.sun.enterprise.module.single.StaticModulesRegistry;

import com.mysql.jdbc.*;

public class Main {

	public static void main(String[] args) {

		// Local Testing
		try {
			DBClass dbClass = new DBClass();
			Connection conn = dbClass.returnConnection();
			Statement statement = conn.createStatement();

			// Add group
			String name = "students4"; // unique ID
			String description = "students that does not have time to chat becasue of projects";
			String admin = "anyone";
			String board = "Hello World";

			// Group group1 = new Group(name, description, admin, board);
			// statement.executeUpdate("INSERT INTO GROUPS (name, description ,
			// admin , board ) " + "VALUES ('"
			// + group1.getName() + "','" + group1.getDescription() + "','" +
			// group1.getAdmin() + "','"
			// + group1.getBoard() + "')");

			String sqlStatement1 = "SELECT * FROM GROUPS;";
			ResultSet rs1 = statement.executeQuery(sqlStatement1);
			while (rs1.next())
				System.out.println(rs1.getString(1) + " | " + rs1.getString(2) + " | " + rs1.getString(3) + " | "
						+ rs1.getString(4));

			// Add user
			String email = "temp@mail.cpom"; // unique ID
			// String first_name = "yazan";
			// String last_name = "mualla";
			// String brief_biography = "loser1";
			// List<String> groups = null;
			// User user1 = new User(email, first_name, last_name,
			// brief_biography);
			//
			// statement.executeUpdate("INSERT INTO USERS (email, first_name,
			// last_name, brief_biography) " + "VALUES ('"
			// + user1.getEmail() + "','" + user1.getFirst_name() + "','" +
			// user1.getLast_name() + "','"
			// + user1.getBrief_biography() + "')");

			String sqlStatement2 = "SELECT * FROM USERS;";
			ResultSet rs2 = statement.executeQuery(sqlStatement2);
			while (rs2.next())
				System.out.println(rs2.getString(1) + " | " + rs2.getString(2) + " | " + rs2.getString(3) + " | "
						+ rs2.getString(4));

			// statement.executeUpdate("INSERT INTO group_user (name, email) " +
			// "VALUES ('group4','user4')");

			String sqlStatement3 = "SELECT * FROM group_user;";
			ResultSet rs3 = statement.executeQuery(sqlStatement3);
			while (rs3.next())
				System.out.println(rs3.getString(1) + " | " + rs3.getString(2));

			statement.executeUpdate("UPDATE USERS SET first_name='" + "changed3" + "',last_name='" + "changed4"
					+ "' WHERE email='" + email + "';");

			statement.executeUpdate(
					"UPDATE USERS SET brief_biography='biography lalalala ' WHERE email='" + email + "';");
			String sqlStatement4 = "SELECT * FROM USERS WHERE email = '" + email + "';";
			ResultSet rs4 = statement.executeQuery(sqlStatement4);
			while (rs4.next())
				System.out.println(rs4.getString(1) + " | " + rs4.getString(2) + " | " + rs4.getString(3) + " | "
						+ rs4.getString(4));

			String sqlStatement5 = "SELECT * FROM group_user WHERE email = 'user2';";

			ResultSet rs5 = statement.executeQuery(sqlStatement5);
			while (rs5.next())
				System.out.println(rs5.getString(1) + " | " + rs5.getString(2));

			if (userExists("temp@mail.cpom")) {
				System.out.println("user");
			} else {
				System.out.println("no user");
			}

			if (groupExists("students2")) {
				System.out.println("group");
			} else {
				System.out.println("no group");
			}

		} catch (Exception e) {
			System.err.println("Error! ");
			System.err.println(e.toString());
		}
	}

	User getUser(String email) throws SQLException {
		DBClass dbClass = new DBClass();
		Connection conn = dbClass.returnConnection();
		Statement statement = conn.createStatement();

		ResultSet rs = statement.executeQuery("SELECT * FROM USERS WHERE email = '" + email + "';");
		User user = new User(email, rs.getString(2), rs.getString(3), rs.getString(4));

		rs = statement.executeQuery("SELECT * FROM group_user WHERE email = '" + email + "';");
		while (rs.next()) {
			user.addGroup(rs.getString(1));
		}
		return user;
	}

	static boolean userExists(String email) {
		DBClass dbClass = new DBClass();
		Connection conn = dbClass.returnConnection();
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM USERS WHERE email = '" + email + "';");
			while (rs.next()) {
				if (rs.getString(1).equals(email)) {
					System.out.println(rs.getString(1));
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

	static boolean groupExists(String name) {
		DBClass dbClass = new DBClass();
		Connection conn = dbClass.returnConnection();
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM GROUPS WHERE name = '" + name + "';");
			while (rs.next()) {
				if (rs.getString(1).equals(name)) {
					System.out.println(rs.getString(1));
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

}
