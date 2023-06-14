package credential;

public class Login {
	private VideoCoSystem system;
	private String email;
	private String password;
	private int login_type;

	public Login(VideoCoSystem system, String email, String password, int login_type) {
		this.system = system;
		this.email = email;
		this.password = password;
		this.login_type = login_type;
	}

	public boolean attempt_login() {
		boolean success = false;
		if (this.login_type == 1 && this.isValidAdmin()) {
			success = true;

		} else if (this.login_type == 2 && this.isValidUser()) {
			success = true;
		} else {
			System.out.println("Login unsuccessful.");
		}
		return success;
	}

	private boolean isValidAdmin() {
		boolean valid = false;
		for (Admin a : system.getAdmins()) {
			if (this.email.equals(a.getEmail()) && this.password.equals(a.getPassword())) {
				valid = true;
			}
		}
		return valid;
	}

	private boolean isValidUser() {
		boolean valid = false;
		for (Integer k : this.system.getUsers().keySet()) {
			if (this.email.equals(this.system.getUsers().get(k).getEmail())
					&& this.password.equals(this.system.getUsers().get(k).getPassword())) {
				valid = true;
			}
		}
		return valid;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public int getLogin_type() {
		return login_type;
	}
}
