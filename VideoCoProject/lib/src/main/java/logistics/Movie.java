package logistics;

import java.util.ArrayList;

public class Movie {
	private Name title;
	private Category genre;
	private int stock;
	private double fee;
	private String image;

	private ArrayList<String> directors;
	private ArrayList<String> actors;

	public Movie(Name name, Category category) {
		this.title = name;
		this.genre = category;
		this.image = "images/default.jpg";
		this.directors = new ArrayList<String>();
		this.actors = new ArrayList<String>();
	}

	public ArrayList<String> getDirectors() {
		return directors;
	}

	public void setDirectors(ArrayList<String> directors) {
		this.directors = directors;
	}

	public ArrayList<String> getActors() {
		return actors;
	}

	public void setActors(ArrayList<String> actors) {
		this.actors = actors;
	}

	public String getTitle() {
		return title.getName();
	}

	public String getGenre() {
		return genre.getCategory();
	}

	public void setTitle(Name n1) {
		this.title.setName(n1.getName());
	}

	public void setGenre(Category c1) {
		this.genre.setCategory(c1.getCategory());
	}

	// get and set other logistics about the movie
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		final Movie other = (Movie) obj;
		if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
			return false;
		}

		if ((this.genre == null) ? (other.genre != null) : !this.genre.equals(other.genre)) {
			return false;
		}

		return true;
	}

}
