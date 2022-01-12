package chess.misc;
import java.util.List;


public abstract class PieceMovementRules {

	public abstract boolean isPseudoLegal(Board board, Location from, Location to);
	public abstract List<Location> getFootprint(Board board, Location loc);
	public abstract List<ChessMove> getMoves(Board board, Location loc);
	
}
