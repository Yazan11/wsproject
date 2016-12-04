package emse.fr.model;

import java.util.ArrayList;
import java.util.List;

public class User{

	String email; // unique ID
	String first_name;
	String last_name;
	String brief_biography;
	List<String> groups = new ArrayList<String>();

	public User(String email, String first_name, String last_name, String brief_biography) {
		super();
		this.email = email;
		this.first_name = first_name;
		this.last_name = last_name;
		this.brief_biography = brief_biography;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getBrief_biography() {
		return brief_biography;
	}

	public void setBrief_biography(String brief_biography) {
		this.brief_biography = brief_biography;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public void addGroup(String newGroup) {
		this.groups.add(newGroup);
	}

	public boolean deleteGroup(String deletedGroup) {
		if (this.groups.contains(deletedGroup)) {
			this.groups.remove(deletedGroup);
			return true;
		} else
			return false;
	}

}
