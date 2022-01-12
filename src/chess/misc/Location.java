package chess.misc;

public class Location {

	private int x;
	private int y;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getRank() {
		return x;
	}
	
	public int getFile() {
		return y;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Location) {
			Location loc = (Location) o;
			return x == loc.x && y == loc.y;
		} else {
			return false;
		}
	}
	
	public int hashCode() {
		return (x + y) * (x + y + 1) / 2 + y;
	}
	
	public String toString() {
		return "(" + getFile() + ", " + getRank() + ")";
	}
	
	public Location add(Location loc) {
		return new Location(x + loc.x, y + loc.y);
	}
	
}
