package chess.misc;

import chess.misc.Chess.COLORS;

public abstract class LegalBoardChecker {

	public abstract boolean isLegal(Board board, COLORS color);
	
	public boolean isLegal(ChessMove move, Board board, COLORS color) {
		boolean result = move.isValid(board);
		if (result) {
			move.perform(board);
			if (color == COLORS.WHITE) {
				result &= isLegal(board, COLORS.BLACK);
			} else {
				result &= isLegal(board, COLORS.WHITE);
			}
			move.undo(board);
		}
		return result;
	}
	
}
