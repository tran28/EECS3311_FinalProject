package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import logistics.Movie;
import logistics.MovieComparator;

public class Inventory implements MovieDBVisitable {
	private HashMap<Character, ArrayList<Movie>> moviesMap;

	public Inventory() {
		this.moviesMap = new HashMap<Character, ArrayList<Movie>>();
	}

	public void addMovieToInventory(Movie m1) {
		// adding movies to inventory using the first letter of the title (in upper
		// case) as the key
		// titles have been trim() --> no case of a white space as the first letter
		Character key = m1.getTitle().toUpperCase().charAt(0);
		if (!this.moviesMap.containsKey(key)) {
			this.moviesMap.put(key, new ArrayList<Movie>());
		}
		this.moviesMap.get(key).add(m1);
		Collections.sort(this.moviesMap.get(key), new MovieComparator());
	}

	public void removeMovieFromInventory(Movie m1) {
		for (Character key : this.moviesMap.keySet()) {
			for (int i = 0; i < this.moviesMap.get(key).size(); i++) {
				if (this.moviesMap.get(key).get(i).getTitle().equals(m1.getTitle())) {
					this.moviesMap.get(key).remove(i);
				}
			}
		}
	}

	public HashMap<Character, ArrayList<Movie>> getMoviesMap() {
		return moviesMap;
	}

	public void setMoviesMap(HashMap<Character, ArrayList<Movie>> moviesMap) {
		this.moviesMap = moviesMap;
	}

	public void printAllMovies() {
		for (Character c : this.moviesMap.keySet()) {
			System.out.println("For " + c + ": ");
			for (Movie m : this.moviesMap.get(c)) {
				System.out.print(m.getTitle() + ", ");
			}
			System.out.println();
		}
	}

	@Override
	public ArrayList<Movie> accept(Visitor visitor) {
		return visitor.visit(this.moviesMap);
	}
}
