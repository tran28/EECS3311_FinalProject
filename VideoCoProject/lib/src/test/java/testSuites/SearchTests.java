package testSuites;

import org.junit.jupiter.api.Test;

import logistics.Category;
import logistics.Movie;
import logistics.Name;
import search.Inventory;
import search.SearchVisitor;

public class SearchTests {

	Movie m1 = new Movie(new Name("Love, Simon"), new Category("Romance"));
	Movie m2 = new Movie(new Name("Shang-Chi"), new Category("Action"));
	Movie m3 = new Movie(new Name("Frozen"), new Category("Animation"));
	Movie m4 = new Movie(new Name("Fantastic Beast"), new Category("Fantasy"));
	Movie m5 = new Movie(new Name("The Favourite"), new Category("Historical"));
	Movie m6 = new Movie(new Name("Spider-Man: Far From Home"), new Category("Action"));
	Movie m7 = new Movie(new Name("Free Guy"), new Category("Comedy"));

	private Inventory addAllMovies() {
		Inventory inv = new Inventory();
		inv.addMovieToInventory(m1);
		inv.addMovieToInventory(m2);
		inv.addMovieToInventory(m3);
		inv.addMovieToInventory(m4);
		inv.addMovieToInventory(m5);
		inv.addMovieToInventory(m6);
		inv.addMovieToInventory(m7);
		return inv;
	}

	@Test
	public void testSearchByName() {
		Inventory inv1 = addAllMovies();
		SearchVisitor search = new SearchVisitor("Love", 1);

		for (Movie m : inv1.accept(search)) {
			System.out.println(m.getTitle());
		}

	}

	@Test
	public void testSearchByCategory() {
		Inventory inv2 = addAllMovies();
		SearchVisitor search = new SearchVisitor("Action", 2);

		for (Movie m : inv2.accept(search)) {
			System.out.println(m.getTitle());
		}

	}

	@Test
	public void testprint() {
		Inventory inv3 = addAllMovies();
		inv3.printAllMovies();
		System.out.println("=============================");
	}

	@Test
	public void test1() {
		String str = "10245";
		System.out.println(str.matches("[0-9]+") && str.length() > 5);
	}

}
