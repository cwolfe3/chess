package chess.misc;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.swing.JFrame;

import chess.player.ChessPlayer;
import chess.player.ChessRandomPlayer;
import chess.player.ChessboardPanel;
import chess.player.PieceCounterAI;


public class Chess {

	private ChessPlayer player1;
	private ChessPlayer player2;
	private Board board;
	private LegalBoardChecker legalTest;
	
	private Deque<ChessMove> moves;
	
	private boolean running = false;
	
	public static enum TYPES {KING, KNIGHT, QUEEN, ROOK, BISHOP, PAWN, PAWN_B};
	public static enum COLORS {WHITE, BLACK};
	
	public Chess(ChessPlayer player1, ChessPlayer player2) {
		this.player1 = player1;
		this.player2 = player2;
		moves = new LinkedList<ChessMove>();
		
		legalTest = new LegalChessBoardChecker();
		board = new Board(legalTest);
		
		board.registerPiece(TYPES.KNIGHT, new KnightMovementRules());
		board.registerPiece(TYPES.KING, new KingMovementRules());
		board.registerPiece(TYPES.ROOK, new RookMovementRules());
		board.registerPiece(TYPES.BISHOP, new BishopMovementRules());
		board.registerPiece(TYPES.QUEEN, new QueenMovementRules());
		board.registerPiece(TYPES.PAWN, new OffsetMovementRules());
		board.registerPiece(TYPES.PAWN_B, new Offset2MovementRules());

		board.placePiece(COLORS.WHITE, "QR", TYPES.ROOK, new Location(0, 0));
		board.placePiece(COLORS.WHITE, "QN", TYPES.KNIGHT, new Location(1, 0));
		board.placePiece(COLORS.WHITE, "QB", TYPES.BISHOP, new Location(2, 0));
		board.placePiece(COLORS.WHITE, "Q", TYPES.QUEEN, new Location(3, 0));
		board.placePiece(COLORS.WHITE, "K", TYPES.KING, new Location(4, 0));
		board.placePiece(COLORS.WHITE, "KB", TYPES.BISHOP, new Location(5, 0));
		board.placePiece(COLORS.WHITE, "KN", TYPES.KNIGHT, new Location(6, 0));
		board.placePiece(COLORS.WHITE, "KR", TYPES.ROOK, new Location(7, 0));
		for (int i = 0; i < 8; i++) {
			board.placePiece(COLORS.WHITE, "P" + i, TYPES.PAWN, new Location(i, 1));
		}
		
		board.placePiece(COLORS.BLACK, "qr", TYPES.ROOK, new Location(7, 7));
		board.placePiece(COLORS.BLACK, "qn", TYPES.KNIGHT, new Location(6, 7));
		board.placePiece(COLORS.BLACK, "qb", TYPES.BISHOP, new Location(5, 7));
		board.placePiece(COLORS.BLACK, "k", TYPES.KING, new Location(4, 7));
		board.placePiece(COLORS.BLACK, "q", TYPES.QUEEN, new Location(3, 7));
		board.placePiece(COLORS.BLACK, "kb", TYPES.BISHOP, new Location(2, 7));
		board.placePiece(COLORS.BLACK, "kn", TYPES.KNIGHT, new Location(1, 7));
		board.placePiece(COLORS.BLACK, "kr", TYPES.ROOK, new Location(0, 7));
		for (int i = 0; i < 8; i++) {
			board.placePiece(COLORS.BLACK, "p" + i, TYPES.PAWN_B, new Location(i, 6));
		}
		
		/*for (int i = 3; i < 5; i++) {
			board.placePiece(COLORS.BLACK, "n" + i, TYPES.QUEEN, new Location(7 - i, 6));
		}
		for (int i = 5; i < 5; i++) {
			board.placePiece(COLORS.WHITE, "q" + i, TYPES.KNIGHT, new Location(7 - i, 1));
		}
		board.placePiece(COLORS.WHITE, "K", TYPES.KING, new Location(3, 0));
		//board.placePiece(COLORS.WHITE, "Q", TYPES.QUEEN, new Location(4, 1));
		board.placePiece(COLORS.BLACK, "k", TYPES.KING, new Location(4, 7));*/

	}
	
	public void start() {
		running = true;
		while (running) {
			turn();
		}
	}
	
	public void turn() {
		ply(player1);
		ply(player2);
	}
	
	private void ply(ChessPlayer player) {
		ChessMove move;
		
		if (board.getMoveList(player.getColor()).isEmpty()) {
			running = false;
			return;
		}
		
		boolean success = false;
		do {
			move = player.nextMove(board);
			success = legalTest.isLegal(move, board, player.getColor());
		} while (!success);
		move.perform(board);
		moves.push(move);
	}
	
	public static void main(String[] args) throws InterruptedException {
		ChessboardPanel panel = new ChessboardPanel();
		
		Chess game = new Chess(panel, new PieceCounterAI(COLORS.BLACK));
		
		new Thread() {
			public void run() {
				game.start();
			}
		}.start();	
		
		JFrame window = new JFrame();
		window.setSize(800, 800);
		window.add(panel);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.addMouseListener(panel);
		panel.addMouseMotionListener(panel);
		
	}
	
}
