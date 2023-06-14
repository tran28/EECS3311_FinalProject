package testSuites;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import logistics.Category;
import logistics.Movie;
import logistics.Name;
import search.Inventory;

public class LogisticsTests {

	Movie m1 = new Movie(new Name("La La Land"), new Category("Comedy"));

	@Test
	public void testCreateMovie() {
		assertEquals("La La Land", m1.getTitle());
		assertEquals("Comedy", m1.getGenre());
	}

	@Test
	public void testChangeTitleAndGenre() {
		m1.setTitle(new Name("Love, Simon"));
		m1.setGenre(new Category("Romance"));
		assertEquals("Love, Simon", m1.getTitle());
		assertEquals("Romance", m1.getGenre());
	}

	Movie m2 = new Movie(new Name("Shang-Chi"), new Category("Action"));
	Movie m3 = new Movie(new Name("Frozen"), new Category("Animation"));
	Movie m4 = new Movie(new Name("Fantastic Beast"), new Category("Fantasy"));
	Movie m5 = new Movie(new Name("The Favourite"), new Category("Historical"));
	Movie m6 = new Movie(new Name("Spider-Man: Far From Home"), new Category("Action"));
	Movie m7 = new Movie(new Name("Free Guy"), new Category("Comedy"));

	@Test
	public void testAddMovies() {
		Inventory inv = new Inventory();
		inv.addMovieToInventory(m1);
		inv.addMovieToInventory(m2);
		inv.addMovieToInventory(m3);
		inv.addMovieToInventory(m4);
		inv.addMovieToInventory(m5);
		inv.addMovieToInventory(m6);
		inv.addMovieToInventory(m7);

		assertEquals(1, inv.getMoviesMap().get('T').size());
		assertEquals(2, inv.getMoviesMap().get('S').size());
		assertEquals(3, inv.getMoviesMap().get('F').size());

		for (Movie m : inv.getMoviesMap().get('F')) {
			System.out.println(m.getTitle() + ", " + m.getGenre());
		}

	}

}
