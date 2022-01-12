package chess.misc;

import chess.misc.Chess.COLORS;

public class LegalChessBoardChecker extends LegalBoardChecker {

	@Override
	public boolean isLegal(Board board, COLORS color) {
		return (color == COLORS.WHITE) ? !board.isAttacked("k") : !board.isAttacked("K");
	}

}
