package chess.player;

import chess.misc.Board;
import chess.misc.Chess;
import chess.misc.ChessMove;

public interface ChessPlayer {

	public ChessMove nextMove(Board board);
	public Chess.COLORS getColor();
}
