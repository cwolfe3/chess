package chess.misc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Offset2MovementRules extends PieceMovementRules {

	private static Map<Location, List<Location>> validMoves;
	
	static {
		validMoves = new HashMap<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				validMoves.put(new Location(i, j), new ArrayList<Location>());
				if (j > 0) {
					validMoves.get(new Location(i, j)).add(new Location(i, j - 1));
				}
				if (j == 1) {
					validMoves.get(new Location(i, j)).add(new Location(i, j - 2));
				}
			}
		}
	}

	//@Override
	public boolean isPseudoLegal(Board board, Location from, Location to) {
		return true;
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
			if (board.isEmpty(loc2)) {
				moves.add(new StandardChessMove(loc, loc2));
			}
			if (board.isOccupied(loc2.add(new Location(1, 0)))) {
				moves.add(new StandardChessMove(loc, loc2.add(new Location(1, 0))));
			}
			if (board.isOccupied(loc2.add(new Location(-1, 0)))) {
				moves.add(new StandardChessMove(loc, loc2.add(new Location(-1, 0))));
			}
		}
		return moves;
	}
	
}
