package logistics;

public class Category {
	private String category;

	public Category(String category) {
		this.category = category.trim();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category.trim();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		final Category other = (Category) obj;
		if ((this.category == null) ? (other.category != null) : !this.category.equals(other.category)) {
			return false;
		}

		return true;
	}

}
