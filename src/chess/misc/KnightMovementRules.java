package chess.misc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KnightMovementRules extends PieceMovementRules {

	private static Map<Location, List<Location>> validMoves;
	
	static {
		int[] xOff = {-2, -1, 1, 2, 2, 1, -1, -2};
		int[] yOff = {1, 2, 2, 1, -1, -2, -2, -1};
		validMoves = new HashMap<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				validMoves.put(new Location(i, j), new ArrayList<Location>());
				for (int k = 0; k < xOff.length; k++) {
					int testX = i + xOff[k];
					int testY = j + yOff[k];
					if (testX >= 0 && testX < 8 && testY >= 0 && testY < 8) {
						validMoves.get(new Location(i, j)).add(new Location(testX, testY));
					}
				}
			}
		}
	}

	@Override
	public boolean isPseudoLegal(Board board, Location from, Location to) {
		return validMoves.get(from).contains(to);
	}

	@Override
	public List<Location> getFootprint(Board board, Location loc) {
		List<Location> locs = validMoves.get(loc);
		return locs;
	}
	
	@Override
	public List<ChessMove> getMoves(Board board, Location loc) {
		List<Location> locs = validMoves.get(loc);
		List<ChessMove> moves = new ArrayList<ChessMove>();
		for (Location loc2 : locs) {
			moves.add(new StandardChessMove(loc, loc2));
		}
		return moves;
	}
	
}
