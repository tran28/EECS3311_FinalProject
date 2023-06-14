package credential;

import logistics.Name;

public class Admin {
	private Name adminName;
	private String email;
	private String password;
	private int adminID;

	public Admin(Name name, String email, String password) {
		this.adminName = name;
		this.email = email;
		this.password = password;
		this.adminID = name.hashCode();
	}

	public String getAdminName() {
		return adminName.getName();
	}

	public void setAdminName(Name adminName) {
		this.adminName = adminName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAdminID() {
		return adminID;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		final Admin other = (Admin) obj;
		if ((this.adminName == null) ? (other.adminName != null) : !this.adminName.equals(other.adminName)) {
			return false;
		}

		if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
			return false;
		}

		if ((this.password == null) ? (other.password != null) : !this.password.equals(other.password)) {
			return false;
		}

		if (this.adminID != other.adminID) {
			return false;
		}

		return true;
	}
}
