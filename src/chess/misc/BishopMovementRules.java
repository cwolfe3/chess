package chess.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BishopMovementRules extends PieceMovementRules {
	
	private static Map<Location, Map<Location, List<Location>>> validMoves;
	
	static {
		validMoves = new HashMap<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int[] xOff = {-1, 1, 1, -1};
				int[] yOff = {-1, 1, -1, 1};
				validMoves.put(new Location(i, j), new HashMap<>());
				for (int k = 0; k < xOff.length; k++) {
					List<Location> checkLocs = new ArrayList<Location>();
					int testX = i + xOff[k];
					int testY = j + yOff[k];
					while (testX >= 0 && testX < 8 && testY >= 0 && testY < 8) {
						List<Location> checks = new ArrayList<Location>();
						for (Location loc : checkLocs) {
							checks.add(loc);
						}
						validMoves.get(new Location(i, j)).put(new Location(testX, testY), checks);
						checkLocs.add(new Location(testX, testY));
						testX += xOff[k];
						testY += yOff[k];
					}
				}
			}
		}
	}

	@Override
	public boolean isPseudoLegal(Board board, Location from, Location to) {
		if (!validMoves.get(from).containsKey(to)) {
			return false;
		}
		boolean blocked = false;
		for (Location loc : validMoves.get(from).get(to)) {
			blocked |= board.isOccupied(loc);
		}
		return !blocked;
	}
	
	@Override
	public List<Location> getFootprint(Board board, Location loc) {
		Map<Location, List<Location>> locs = validMoves.get(loc);
		List<Location> locs2 = new ArrayList<Location>();
		for (Location to : locs.keySet()) {
			boolean clean = true;
			for (Location check : locs.get(to)) {
				if (board.isOccupied(check)) {
					clean = false;
					break;
				}
			}
			if (clean) {
				locs2.add(to);
			}
		}
		return locs2;
	}

	@Override
	public List<ChessMove> getMoves(Board board, Location loc) {
		Map<Location, List<Location>> locs = validMoves.get(loc);
		List<ChessMove> moves = new ArrayList<ChessMove>();
		for (Location to : locs.keySet()) {
			boolean clean = true;
			for (Location check : locs.get(to)) {
				if (board.isOccupied(check)) {
					clean = false;
					break;
				}
			}
			if (clean) {
				moves.add(new StandardChessMove(loc, to));
			}
		}
		return moves;
	}

}
