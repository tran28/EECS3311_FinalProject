package testSuites;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import credential.Admin;
import credential.Location;
import credential.Login;
import credential.ReadFromCSV;
import credential.User;
import credential.VideoCoSystem;
import logistics.Name;

public class CredentialTests {

	// new system initializes with default 1 admin (head admin) and 0 user
	VideoCoSystem s = new VideoCoSystem();

	Admin a2 = new Admin(new Name("Admin2"), "admin2@videoco.com", "admin2pass");
	Admin a3 = new Admin(new Name("Admin3"), "admin3@videoco.com", "admin3pass");
	Admin a4 = new Admin(new Name("Admin4"), "admin4@videoco.com", "admin4pass");
	User u1 = new User(new Name("User1"), "user1@videoco.com", "user1pass");
	User u2 = new User(new Name("User2"), "user2@videoco.com", "user2pass");
	User u3 = new User(new Name("User3"), "user3@videoco.com", "user3pass");
	User u4 = new User(new Name("User4"), "user4@videoco.com", "user4pass");

	@Test
	public void testSystemCreated() {
		assertTrue(s.getAdmins().size() > 0);
	}

	@Test
	public void testAddAdmin() {
		int prev = s.getAdmins().size();
		s.addAdmin(a2);
		assertEquals(prev + 1, s.getAdmins().size());
	}

	@Test
	public void testRemoveAdmin() {
		s.addAdmin(a2);
		s.addAdmin(a3);
		s.addAdmin(a4);
		int prev = s.getAdmins().size();
		s.removeAdmin(a3);
		assertEquals(prev - 1, s.getAdmins().size());
	}

	@Test
	public void testAddUser() {
		int prev = s.getUsers().size();
		s.addUser(u1);
		assertEquals(prev + 1, s.getUsers().size());
	}

	@Test
	public void testRemoveUser() {
		s.addUser(u1);
		s.addUser(u2);
		s.addUser(u3);
		s.addUser(u4);
		int prev = s.getUsers().size();
		s.removeUser(u4);
		s.removeUser(u1);
		assertEquals(prev - 2, s.getUsers().size());
	}

	@Test
	public void testLoginAdmin() {
		s.addAdmin(a2);
		s.addAdmin(a3);
		s.addAdmin(a4);

		// head admin login
		Login l = new Login(s, "headadmin@videoco.com", "headadminpass", 1);
		assertTrue(l.attempt_login());

		// regular admin login
		l = new Login(s, a4.getEmail(), a4.getPassword(), 1);
		assertTrue(l.attempt_login());

		// admin credentials cannot log in as a user
		l = new Login(s, a4.getEmail(), a4.getPassword(), 2);
		assertFalse(l.attempt_login());
	}

	@Test
	public void testLoginUser() {
		s.addUser(u1);
		s.addUser(u2);
		s.addUser(u3);
		s.addUser(u4);

		// user login
		Login l = new Login(s, u3.getEmail(), u3.getPassword(), 2);
		assertTrue(l.attempt_login());

		// user credentials cannot log in as an admin
		l = new Login(s, u3.getEmail(), u3.getPassword(), 1);
		assertFalse(l.attempt_login());
	}

	@Test
	public void csvUser() {
		ReadFromCSV r = new ReadFromCSV();
		r.readUsersFromCSV("userSample.csv");
		HashMap<Integer, User> users = r.getUsers();

		boolean user1found = false;
		boolean user2found = false;
		boolean user3found = false;

		for (Integer k : users.keySet()) {
			if (users.get(k).getEmail().equals("testemail1@yorku.ca"))
				user1found = true;
			if (users.get(k).getEmail().equals("testemail2@yorku.ca"))
				user2found = true;
			if (users.get(k).getEmail().equals("testemail3@yorku.ca"))
				user3found = true;
		}
		assertTrue(user1found && user2found && user3found);

	}

	@Test
	public void csvAdmin() {
		ReadFromCSV r = new ReadFromCSV();
		r.readAdminsFromCSV("adminSample.csv");
		ArrayList<Admin> admins = r.getAdmins();

		assertEquals("testadmin1@videoco.ca", admins.get(0).getEmail());
		assertEquals("testadmin2@videoco.ca", admins.get(1).getEmail());
		assertEquals("testadmin3@videoco.ca", admins.get(2).getEmail());
	}

	@Test
	public void testLocation() {
		Location l = new Location();
		int i = l.generateRandomUserDistance();
		u1.setLocationDistance(i);
		assertEquals(i, u1.getLocationDistance());
	}
	
	@Test
	public void testGetUserFromID() {
		int id = u1.getUserID();
		s.addUser(u1);
		s.addUser(u2);
		s.addUser(u3);
		s.addUser(u4);
		assertEquals(u1, s.getUserFromUserID(id));
	}
}
