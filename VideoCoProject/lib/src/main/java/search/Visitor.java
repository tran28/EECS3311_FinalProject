package search;

import java.util.ArrayList;
import java.util.HashMap;

import logistics.Movie;

public interface Visitor {
	public ArrayList<Movie> visit(HashMap<Character, ArrayList<Movie>> moviesMap);
}
