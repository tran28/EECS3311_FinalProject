package search;

import java.util.ArrayList;
import java.util.HashMap;

import logistics.Movie;

public class SearchVisitor implements Visitor {
	private String search_input;
	private int search_type;

	// search_input is the input from the user
	// search_type:
	// 1: Search by Name
	// 2: Search by Category
	// 3: Search by All
	// ELSE: Not a search type.
	public SearchVisitor(String input, int type) {
		this.search_input = input.toLowerCase();
		this.search_type = type;
	}

	@Override
	public ArrayList<Movie> visit(HashMap<Character, ArrayList<Movie>> moviesMap) {
		ArrayList<Movie> found = new ArrayList<Movie>();
		for (Character k : moviesMap.keySet()) {
			for (Movie m : moviesMap.get(k)) {
				if (this.search_type == 1) {
					if (m.getTitle().toLowerCase().contains(this.search_input)) {
						found.add(m);
					}
				} else if (this.search_type == 2) {
					if (m.getGenre().toLowerCase().contains(this.search_input)) {
						found.add(m);
					}
				} else if (this.search_type == 3) {
					if (m.getTitle().toLowerCase().contains(this.search_input)
							|| m.getGenre().toLowerCase().contains(search_input)) {
						found.add(m);
					}
				} else {
					System.out.println("Not a valid search_type.");
				}
			}
		}
		return found;
	}

}
