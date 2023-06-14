package credential;

import java.util.Random;

public class Location {
	private int distance;

	public Location() {
		this.distance = 0;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int generateRandomUserDistance() {
		Random r = new Random();
		// generate a random distance from 0-2999
		int random = r.nextInt(3000);
		return random;
	}
}
