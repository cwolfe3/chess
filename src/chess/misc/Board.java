package chess.misc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import chess.misc.Chess.COLORS;


public class Board {

	//private StandardBitBoard totalBoard;
	//private Map<Object, StandardBitBoard> boards;
	private Map<Object, PieceMovementRules> rules;
	
	private Map<Object, ChessPiece> pieces;
	private Map<Location, Object> locations;
	
	private boolean whiteToMove = true;
	private LegalBoardChecker legalTest;
	
	public Board(LegalBoardChecker legalTest) {
		//boards = new HashMap<>();
		rules = new HashMap<>();
		pieces = new HashMap<>();
		locations = new HashMap<>();
		//totalBoard = new StandardBitBoard();
		this.legalTest = legalTest;
	}
	
	public void registerPiece(Object id, PieceMovementRules pattern) {
		rules.put(id, pattern);
	}
	
	public ChessPiece getPieceAt(Location loc) {
		return pieces.get(locations.get(loc));
	}
	
	public void placePiece(Object colorId, Object pieceId, Object typeId, Location loc) {
		pieces.put(pieceId, new ChessPiece(pieceId, colorId, typeId, rules.get(typeId), loc));
		locations.put(loc, pieceId);
	}
	
	public ChessPiece removePiece(Location loc) {
		if (isOccupied(loc)) {
			Object pieceId = locations.remove(loc);
			return pieces.remove(pieceId);
		}
		return null;
	}
	
	public boolean isOccupied(Location loc) {
		return locations.get(loc) != null;
	}
	
	public boolean isEmpty(Location loc) {
		return !isOccupied(loc);
	}
	
	public MoveIterator getMoves(Object colorId) {
		return new MoveIterator(this, colorId);
	}
	
	public List<ChessMove> getMoveList(Object colorId) {
		return new MoveIterator(this, colorId).moves;
	}
	
	public boolean isAttacked(Object attackKey) {
		for (ChessPiece attacker : pieces.values()) {
			if (attacker.getColor().equals(pieces.get(attackKey).getColor())) {
				continue;
			}
			if (attacker.getRules().getFootprint(this, attacker.getLocation()).contains(pieces.get(attackKey).getLocation())) {
				return true;
			}
		}
		return false;
	}
	
	
	public class MoveIterator implements Iterator<ChessMove> {
		
		public List<ChessMove> moves;
		private int index;
		
		public MoveIterator(Board board, Object colorId) {			
			moves = new ArrayList<>();

			for (int i = 0; i < pieces.values().size(); i++) {
				ChessPiece piece = pieces.values().toArray(new ChessPiece[] {})[i];
				if (piece.getColor().equals(colorId)) {
					for (ChessMove move : piece.getRules().getMoves(board, piece.getLocation())) {
						if (legalTest.isLegal(move, board, (COLORS) colorId)) {
							moves.add(move);
						}
					}
				}
			}
			
		}

		@Override
		public boolean hasNext() {
			return index < moves.size() - 1;
		}

		@Override
		public ChessMove next() {
			return moves.get(index++);
		}
		
	}
	
	public Object[][] serialize() {
		Object[][] board = new Object[8][8];
		for (Location loc : locations.keySet()) {
			board[loc.getRank()][loc.getFile()] = locations.get(loc);
		}
		return board;
	}
	
}
