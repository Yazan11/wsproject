package emse.fr.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import emse.fr.database.DBClass;
import emse.fr.model.Group;

//@RestController
@Controller
@RequestMapping("/rest")

public class RESTController {

	DBClass dbClass = new DBClass();
	Connection conn = dbClass.returnConnection();

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

	@RequestMapping("/listgroups")
	public List<Group> listGroups(Model model) throws SQLException {
		List<Group> groups = new ArrayList<Group>();
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM GROUPS;");
		while (rs.next()) {
			groups.add(getGroup(rs.getString(1)));
		}
		return groups;
	}

	@RequestMapping("/listjoinedgroups")
	public List<Group> listJoinedGroups(Model model, @RequestParam("email") String email) throws SQLException {
		List<Group> groups = new ArrayList<Group>();
		if (userExists(email)) {
			
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM GROUPS where admin='" + email + "';");
		while (rs.next()) {
			groups.add(getGroup(rs.getString(1)));
		}

		statement = conn.createStatement();
		ResultSet rs2 = statement.executeQuery("SELECT * FROM group_user where email='" + email + "';");
		while (rs2.next()) {
			if (!groups.contains(getGroup(rs2.getString(1)))) {
				groups.add(getGroup(rs2.getString(1)));
			}
		}

		model.addAttribute("groups", groups);
		return groups;
		}else{
			return groups;
		}
	}

	@RequestMapping("/viewmembers")
	public List<String> viewMembers(Model model, @RequestParam("name") String name) throws SQLException {
		if (groupExists(name)) {
			List<String> members = new ArrayList<String>();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT email FROM group_user WHERE name ='" + name + "';");
			while (rs.next()) {
				members.add(rs.getString(1));
			}
			return members;
		} else {
			List<String> members = new ArrayList<String>();
			return members;
		}
	}


}
