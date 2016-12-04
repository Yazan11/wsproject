package emse.fr.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import emse.fr.database.DBClass;
import emse.fr.model.Group;
import emse.fr.model.User;

@Controller
@RequestMapping("/html")
public class HTMLController {

	DBClass dbClass = new DBClass();
	Connection conn = dbClass.returnConnection();

	User getUser(String email) throws SQLException {
		Statement statement = conn.createStatement();
		User user = new User(email, " ", " ", " ");

		ResultSet rs = statement.executeQuery("SELECT * FROM USERS WHERE email = '" + email + "';");
		while (rs.next()) {
			user = new User(email, rs.getString(2), rs.getString(3), rs.getString(4));
		}

		rs = statement.executeQuery("SELECT * FROM group_user WHERE email = '" + email + "';");
		while (rs.next()) {
			user.addGroup(rs.getString(1));
		}
		return user;
	}

	Group getGroup(String name) throws SQLException {
		Statement statement = conn.createStatement();
		Group group = new Group(name, " ", " ");
		ResultSet rs = statement.executeQuery("SELECT * FROM GROUPS WHERE name = '" + name + "';");
		while (rs.next()) {
			group = new Group(name, rs.getString(2), rs.getString(3));
		}

		group.getMembers().clear();
		rs = statement.executeQuery("SELECT * FROM group_user WHERE name = '" + name + "';");
		while (rs.next()) {
			group.addMember(rs.getString(2));
		}

		group.getComments().clear();
		rs = statement.executeQuery("SELECT * FROM comments WHERE name = '" + name + "';");
		while (rs.next()) {
			group.getComments().add("User:" + rs.getString(2) + " Comment:" + rs.getString(3));
		}
		return group;
	}
	
	boolean userExists(String email) {
		DBClass dbClass = new DBClass();
		Connection conn = dbClass.returnConnection();
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM USERS WHERE email = '" + email + "';");
			while (rs.next()) {
				if (rs.getString(1).equals(email)) {
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

	boolean groupExists(String name) {
		DBClass dbClass = new DBClass();
		Connection conn = dbClass.returnConnection();
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM GROUPS WHERE name = '" + name + "';");
			while (rs.next()) {
				if (rs.getString(1).equals(name)) {
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

	@RequestMapping("/newuser")
	public String newUser(Model model, @RequestParam("email") String email,
			@RequestParam("first_name") String first_name, @RequestParam("last_name") String last_name,
			@RequestParam("brief_biography") String brief_biography) throws SQLException {

		if (!userExists(email)) {
			Statement statement = conn.createStatement();
			User user = new User(email, first_name, last_name, brief_biography);
			statement.executeUpdate("INSERT INTO users (email, first_name,last_name, brief_biography) " + "VALUES ('"
					+ user.getEmail() + "','" + user.getFirst_name() + "','" + user.getLast_name() + "','"
					+ user.getBrief_biography() + "')");
			model.addAttribute("user", user);
			model.addAttribute("userGroups", getUser(email).getGroups());
		} else {

			model.addAttribute("user", getUser(email));
			model.addAttribute("userGroups", getUser(email).getGroups());
			return "userexistspage";
		}

		return "userpage";
	}

	@RequestMapping("/changename")
	public String changeName(Model model, @RequestParam("email") String email,
			@RequestParam("first_name") String first_name, @RequestParam("last_name") String last_name)
			throws SQLException {

		if (userExists(email)) {
			Statement statement = conn.createStatement();
			statement.executeUpdate("UPDATE USERS SET first_name='" + first_name + "',last_name='" + last_name
					+ "' WHERE email='" + email + "';");
			model.addAttribute("user", getUser(email));
			model.addAttribute("userGroups", getUser(email).getGroups());
			return "userpage";
		} else {
			return "userdoesnotexist";
		}

	}

	@RequestMapping("/changebiography")
	public String changeBiography(Model model, @RequestParam("email") String email,
			@RequestParam("brief_biography") String brief_biography) throws SQLException {
		if (userExists(email)) {
			Statement statement = conn.createStatement();
			statement
					.executeUpdate("UPDATE USERS SET brief_biography='" + brief_biography + "' WHERE email='" + email + "';");

			model.addAttribute("user", getUser(email));
			model.addAttribute("userGroups", getUser(email).getGroups());
			return "userpage";
		} else {
			return "userdoesnotexist";
		}

	}

	@RequestMapping("/deteleaccount")
	public String deteleAccount(Model model, @RequestParam("email") String email) throws SQLException {
		if (userExists(email)) {
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM USERS WHERE email='" + email + "';");
			statement.executeUpdate("DELETE FROM group_user WHERE email='" + email + "';");
			return "home";
		} else {
			return "userdoesnotexist";
		}
	}

	@RequestMapping("/joingroup")
	public String joinGroup(Model model, @RequestParam("email") String email, @RequestParam("name") String name)
			throws SQLException {
		if (userExists(email)) {
			if (groupExists(name)) {
				Statement statement = conn.createStatement();
				statement.executeUpdate(
						"INSERT INTO group_user (name,email) " + "VALUES ('" + name + "','" + email + "')");

				model.addAttribute("user", getUser(email));
				return "userpage";
			} else {
				return "groupdoesnotexist";
			}
		} else {
			return "userdoesnotexist";
		}
	}

	@RequestMapping("/leavegoup")
	public String leaveGroup(Model model, @RequestParam("email") String email, @RequestParam("name") String name)
			throws SQLException {
		if (userExists(email)) {
			if (groupExists(name)) {
				Statement statement = conn.createStatement();
				statement.executeUpdate("DELETE * from group_user WHERE name='" + name + "',email='" + email + "';");
				model.addAttribute("user", getUser(email));
				return "userpage";
			} else {
				return "groupdoesnotexist";
			}
		} else {
			return "userdoesnotexist";
		}
	}

	@RequestMapping("/checkprofile")
	public String checkProfile(Model model, @RequestParam("email") String email) throws SQLException {
		model.addAttribute("user", getUser(email));
		if (userExists(email)) {
			return "userpage";
		} else {
			return "userdoesnotexist";
		}
	}

	@RequestMapping("/comment")
	public String newComment(Model model, @RequestParam("email") String email, @RequestParam("name") String name,
			@RequestParam("comment") String comment) throws SQLException {
		
		
		if (userExists(email)) {
			if (groupExists(name)) {
		
				Statement statement = conn.createStatement();
				statement.executeUpdate("INSERT INTO comments (name,email,comment) " + "VALUES ('" + name + "','"
						+ email + "','" + comment + "')");

				model.addAttribute("user", getUser(email));
				model.addAttribute("name", name);
				model.addAttribute("comment", comment);
				
				return "useraddedcomment";
			} else {
				return "groupdoesnotexist";
			}
		} else {
			return "userdoesnotexist";
		}
	}
	
	@RequestMapping("/newgroup")
	public String newGroup(Model model, @RequestParam("name") String name,
			@RequestParam("description") String description, @RequestParam("admin") String admin) throws SQLException {

		if (!groupExists(name)) {

			Statement statement = conn.createStatement();

			Group group = new Group(name, description, admin);
			statement.executeUpdate("INSERT INTO GROUPS (name, description , admin ) " + "VALUES ('" + group.getName()
					+ "','" + group.getDescription() + "','" + group.getAdmin() + "')");

			model.addAttribute("group", group);
			return "grouppage";
		} else {
			model.addAttribute("group", getGroup(name));
			return "groupexistspage";

		}

	}

	@RequestMapping("/changedescription")
	public String newGroup(Model model, @RequestParam("name") String name,
			@RequestParam("description") String description) throws SQLException {

		if (groupExists(name)) {
			Statement statement = conn.createStatement();

			statement.executeUpdate("UPDATE GROUPS SET description='" + description + "' WHERE name='" + name + "';");

			model.addAttribute("group", getGroup(name));
			return "grouppage";
		} else {
			return "groupdoesnotexist";

		}
	}

	@RequestMapping("/deletegroup")
	public String deteleGroup(Model model, @RequestParam("name") String name) throws SQLException {
		if (groupExists(name)) {
			Statement statement = conn.createStatement();
			statement.executeUpdate("DELETE FROM GROUPS WHERE name='" + name + "';");
			statement.executeUpdate("DELETE FROM group_user WHERE name='" + name + "';");
			return "home";
		} else {
			return "groupdoesnotexist";
		}
	}
	
	@RequestMapping("/checkgroup")
	public String checkgroup(Model model, @RequestParam("name") String name) throws SQLException {
		if (groupExists(name)) {
			model.addAttribute("group", getGroup(name));
			return "grouppage";
		} else {
			return "groupdoesnotexist";
		}
	}

}
