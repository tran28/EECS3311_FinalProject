package search;

import java.util.ArrayList;

import logistics.Movie;

public interface MovieDBVisitable {
	public ArrayList<Movie> accept(Visitor visitor);
}
