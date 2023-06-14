package credential;

import java.util.ArrayList;
import java.util.HashMap;

import logistics.Movie;
import logistics.Name;
import search.Inventory;

public class VideoCoSystem {
	private ArrayList<Admin> adminList;
	private HashMap<Integer, User> userList;
	private Admin head_admin;
	private Inventory inventory;

	public VideoCoSystem() {
		this.adminList = new ArrayList<Admin>();
		this.userList = new HashMap<Integer, User>();

		// add user database
		ReadFromCSV r = new ReadFromCSV();
		this.userList = r.readUsersFromCSV("user.csv");

		// create head admin
		this.head_admin = new Admin(new Name("Head Admin"), "headadmin@videoco.com", "headadminpass");
		adminList.add(head_admin);

		// add admin database
		for (Admin a : r.readAdminsFromCSV("admin.csv")) {
			this.adminList.add(a);
		}

		this.inventory = new Inventory();
		this.inventory = r.readMoviesFromCSV("movie.csv");
	}

	public ArrayList<Admin> getAdmins() {
		return adminList;
	}

	public void addAdmin(Admin admin) {
		this.adminList.add(admin);
	}

	public void removeAdmin(Admin admin) {
		// check admin exists
		boolean exists = false;
		for (Admin a : this.adminList) {
			if (a.equals(admin)) {
				exists = true;
			}
		}
		if (exists) {
			this.adminList.remove(admin);
		} else {
			System.out.println("Cannot remove admin. Admin does not exist.");
		}
	}

	public HashMap<Integer, User> getUsers() {
		return userList;
	}

	public void addUser(User user) {
		this.userList.put(user.getUserID(), user);
	}

	public void removeUser(User user) {
		// check user exists
		boolean exists = false;
		for (Integer k : this.userList.keySet()) {
			if (this.userList.get(k).equals(user)) {
				exists = true;
			}
		}
		if (exists) {
			this.userList.remove(user.getUserID());
		} else {
			System.out.println("Cannot remove user. User does not exist.");
		}
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void addInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Movie getMovieFromTitle(String name) {
		Movie found = null;
		for (Character k : this.inventory.getMoviesMap().keySet()) {
			for (Movie m : this.inventory.getMoviesMap().get(k)) {
				if (m.getTitle().equals(name)) {
					found = m;
				}
			}
		}
		return found;
	}

	public User getUserFromUserID(int userID) {
		User found = null;
		for (Integer i : this.userList.keySet()) {
			if (userID == this.userList.get(i).getUserID()) {
				found = this.userList.get(i);
			}
		}
		return found;
	}

	public Admin getAdminFromEmail(String email) {
		Admin found = null;
		for (Admin a : this.adminList) {
			if (email.equals(a.getEmail())) {
				found = a;
			}
		}
		return found;
	}

}
