package chess.misc;

public class StandardChessMove extends ChessMove {
	
	private Location from;
	private Location to;
	
	private ChessPiece capture;
	
	public StandardChessMove(Location from, Location to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public void perform(Board board) {
		ChessPiece piece = board.removePiece(from);
		if (board.isOccupied(to)) {
			capture = board.removePiece(to);
		}
		board.placePiece(piece.getColor(), piece.getId(), piece.getType(), to);
	}

	@Override
	public void undo(Board board) {
		ChessPiece piece = board.removePiece(to);
		board.placePiece(piece.getColor(), piece.getId(), piece.getType(), from);
		if (capture != null) {
			board.placePiece(capture.getColor(), capture.getId(), capture.getType(), to);
		}
	}
	
	public String toString() {
		return from + " to " + to;
	}

	@Override
	public boolean isValid(Board board) {
		if (from == to) return false;
		ChessPiece piece = board.getPieceAt(from);
		ChessPiece piece2 = board.getPieceAt(to);
		if (piece != null) {
			if (piece.getRules().isPseudoLegal(board, from, to)) {
				if (piece2 != null) {
					if (piece2.getType() == Chess.TYPES.KING) return false;
					return piece.getColor() != piece2.getColor();
				} else {
					return true;
				}
			}
		}
		return false;
	}

}
