package credential;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import logistics.Category;
import logistics.Movie;
import logistics.Name;
import search.Inventory;

public class ReadFromCSV {
	private HashMap<Integer, User> users = new HashMap<Integer, User>();
	private ArrayList<Admin> admins = new ArrayList<Admin>();
	private Inventory movies = new Inventory();

	public HashMap<Integer, User> readUsersFromCSV(String file) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(file);

		Scanner scanner;
		scanner = new Scanner(is);
		scanner.nextLine();
		while (scanner.hasNextLine()) {
			String[] data = scanner.nextLine().split(",");
			User u = new User(new Name(data[0].trim()), data[1].trim(), data[2].trim());
			users.put(u.getUserID(), u);
		}
		scanner.close();

		return users;
	}

	public ArrayList<Admin> readAdminsFromCSV(String file) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(file);

		Scanner scanner;
		scanner = new Scanner(is);
		scanner.nextLine();
		while (scanner.hasNextLine()) {
			String[] data = scanner.nextLine().split(",");
			Admin a = new Admin(new Name(data[0].trim()), data[1].trim(), data[2].trim());
			admins.add(a);
		}
		scanner.close();

		return admins;
	}

	public Inventory readMoviesFromCSV(String file) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(file);

		Scanner scanner;
		scanner = new Scanner(is);
		scanner.nextLine();
		while (scanner.hasNextLine()) {
			String[] data = scanner.nextLine().split(",");
			Movie m = new Movie(new Name(data[0].trim()), new Category(data[1].trim()));
			m.setFee(Double.parseDouble(data[2].trim()));
			m.setImage(data[3].trim());
			m.setStock(Integer.valueOf(data[4].trim()));

			ArrayList<String> directors = new ArrayList<String>();
			ArrayList<String> actors = new ArrayList<String>();

			String[] getDirectors = data[5].trim().split(";");
			for (int i = 0; i < getDirectors.length; i++) {
				directors.add(getDirectors[i].trim());
			}
			m.setDirectors(directors);

			String[] getActors = data[6].trim().split(";");
			for (int i = 0; i < getActors.length; i++) {
				actors.add(getActors[i].trim());
			}
			m.setActors(actors);

			movies.addMovieToInventory(m);
		}
		scanner.close();

		return movies;
	}

	public HashMap<Integer, User> getUsers() {
		return users;
	}

	public ArrayList<Admin> getAdmins() {
		return admins;
	}

	public Inventory getMovies() {
		return movies;
	}

}
