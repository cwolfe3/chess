package chess.player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chess.misc.Board;
import chess.misc.Chess.COLORS;
import chess.misc.ChessMove;

public class PieceCounterAI implements ChessPlayer {
	
	private COLORS color;
	private COLORS otherColor;
	
	private Map<String, Double> pieceValues;
	
	private double[][] locValues = {
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1},
			{1, 1.1, 1.4, 1.4, 1.4, 1.4, 1.1, 1},
			{1, 1.1, 1.4, 1.8, 1.8, 1.4, 1.1, 1},
			{1, 1.1, 1.4, 1.8, 1.8, 1.4, 1.1, 1},
			{1, 1.1, 1.4, 1.4, 1.4, 1.4, 1.1, 1},
			{1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1},
	};
	
	public PieceCounterAI(COLORS color) {
		this.color = color;
		this.otherColor = color == COLORS.WHITE ? COLORS.BLACK : COLORS.WHITE;
		
		pieceValues = new HashMap<>();
		pieceValues.put("k", 0.0);
		pieceValues.put("q", 9.0);
		pieceValues.put("r", 5.0);
		pieceValues.put("n", 3.0);
		pieceValues.put("b", 3.0);
		pieceValues.put("p", 1.0);

	}

	@Override
	public ChessMove nextMove(Board board) {
		Node node = negaWithPruning(board, color == COLORS.WHITE ? 1 : -1, 4, -9999999, 99999999);
		System.out.println(node.value);
		return node.move;
	}
	
	private Node nega(Board board, int color, int depth) {
		if (depth == 0) {
			return new Node(value(board), null);
		} else {
			int bestIndex = 0;
			double bestValue = -99999999;
			List<ChessMove> moves = board.getMoveList(color == 1 ? this.color : otherColor);
			if (moves.isEmpty()) {
				return new Node(-99999999 * color, null);
			}
			for (int i = 0; i < moves.size(); i++) {
				moves.get(i).perform(board);
				Node newNode = nega(board, color * -1, depth - 1);
				moves.get(i).undo(board);
				if (newNode.value > bestValue) {
					bestValue = newNode.value;
					bestIndex = i;
				}
			}
			return new Node(bestValue, moves.get(bestIndex));
		}
	}
	
	private Node negaWithPruning(Board board, int color, int depth, double alpha, double beta) {
		List<ChessMove> moves = board.getMoveList(color == 1 ? COLORS.WHITE : COLORS.BLACK);
		Collections.shuffle(moves);

		if (moves.isEmpty()) {
			return new Node(-(99999 + depth), null);
		} else if (depth == 0) {
			return new Node(value(board) * color, null);
		} else {
			int bestIndex = 0;
			double bestValue = -99999999;
			for (int i = 0; i < moves.size(); i++) {
				moves.get(i).perform(board);
				Node newNode = negaWithPruning(board, color * -1, depth - 1, -beta, -alpha);
				newNode.value *= -1;
				moves.get(i).undo(board);
				if (newNode.value > bestValue) {
					bestValue = newNode.value;
					bestIndex = i;
				}
				alpha = Math.max(alpha, newNode.value);
				if (alpha >= beta) {
					break;
				}
			}
			return new Node(bestValue, moves.get(bestIndex));
		}
	}

	@Override
	public COLORS getColor() {
		return color;
	}
	
	private double value(Board board) {
		double result = 0;
		Object[][] board2 = board.serialize();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board2[i][j] != null) {
					if (board2[i][j].equals(((String)board2[i][j]).toLowerCase())) {
						result -= locValues[i][j] * pieceValues.get((((String)board2[i][j]).toLowerCase()).substring(0, 1));
					} else {
						result += locValues[i][j] * pieceValues.get((((String)board2[i][j]).toLowerCase()).substring(0, 1));
					}
				}
			}
		}
		return result;
	}
	
	private class Node {
		public double value;
		public ChessMove move;
		
		public Node(double value, ChessMove move) {
			this.value = value;
			this.move = move;
		}
	}

}
