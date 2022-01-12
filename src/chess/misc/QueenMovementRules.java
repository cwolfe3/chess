package chess.misc;

import java.util.List;

public class QueenMovementRules extends PieceMovementRules {
	
	private PieceMovementRules rook;
	private PieceMovementRules bishop;
	
	public QueenMovementRules() {
		rook = new RookMovementRules();
		bishop = new BishopMovementRules();
	}

	@Override
	public boolean isPseudoLegal(Board board, Location from, Location to) {
		return rook.isPseudoLegal(board, from, to) || bishop.isPseudoLegal(board, from, to);
	}

	@Override
	public List<Location> getFootprint(Board board, Location loc) {
		List<Location> rookMoves = rook.getFootprint(board, loc);
		rookMoves.addAll(bishop.getFootprint(board, loc));
		return rookMoves;
	}
	
	@Override
	public List<ChessMove> getMoves(Board board, Location loc) {
		List<ChessMove> rookMoves = rook.getMoves(board, loc);
		rookMoves.addAll(bishop.getMoves(board, loc));
		return rookMoves;
	}

}
