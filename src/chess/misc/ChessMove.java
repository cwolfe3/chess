package chess.misc;

public abstract class ChessMove {

	public abstract void perform(Board board);
	public abstract void undo(Board board);
	public abstract boolean isValid(Board board);
	
	public boolean isReversible() {
		return true;
	}
	
}
