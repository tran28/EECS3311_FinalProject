package logistics;

import java.util.Comparator;

public class MovieComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie m1, Movie m2) {
        return m1.getTitle().compareToIgnoreCase(m2.getTitle());
    }
}