package emse.fr.model;

import java.util.ArrayList;
import java.util.List;

public class Group{
	
	private String name; // unique ID
	private String description;
	private String admin;
	private List<String> comments = new ArrayList<String>();
	private List<String> members = new ArrayList<String>();
	
	public static final String COLLECTION_NAME = "Group";

	public Group(String name, String description, String admin) {
		super();
		this.name = name;
		this.description = description;
		this.admin = admin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}
	
	public void addMember(String newMember) {
		this.members.add(newMember);
	}

	public boolean deleteMember(String deleteMember) {
		if (this.members.contains(deleteMember)) {
			this.members.remove(deleteMember);
			return true;
		} else
			return false;
	}
}
