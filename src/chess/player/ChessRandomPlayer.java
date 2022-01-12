package chess.player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import chess.misc.Board;
import chess.misc.Chess;
import chess.misc.Chess.COLORS;
import chess.misc.ChessMove;
import chess.misc.Board.MoveIterator;

public class ChessRandomPlayer implements ChessPlayer {
	
	private Object colorId;
	private Random random;
	
	public ChessRandomPlayer(Object colorId) {
		this.colorId = colorId;
		random = new Random(System.currentTimeMillis());
	}

	@Override
	public ChessMove nextMove(Board board) {
		List<ChessMove> moves = board.getMoveList(colorId);
		int index = (int)(random.nextDouble() * moves.size());
		return moves.get(index);
	}
	
	@Override
	public Chess.COLORS getColor() {
		return (COLORS) colorId;
	}

}
