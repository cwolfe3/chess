package chess.misc;
import java.util.HashMap;
import java.util.Map;


public class StandardBitBoard {

	private static Map<Location, Integer> locCode;
	private int board;
	
	static {
		locCode = new HashMap<Location, Integer>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				locCode.put(new Location(i, j),  locMask(new Location(i, j)));
			}
		}
	}
	
	private static int locMask(Location loc) {
		return 1 << (8 * loc.getRank() + loc.getFile());
	}
	
	public boolean isOccupied(Location loc) {
		return (board & locCode.get(loc)) == 1;
	}
	
	public boolean isEmpty(Location loc) {
		return !isOccupied(loc);
	}
	
	public void add(Location loc) {
		board |= locMask(loc);
	}
	
	public void remove(Location loc) {
		board &= ~locMask(loc);
	}
	
	public void move(Location loc1, Location loc2) {
		add(loc2);
		remove(loc1);
	}
}
