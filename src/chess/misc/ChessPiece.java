package chess.misc;

public class ChessPiece {

	private Object color;
	private Object type;
	private Object id;
	private PieceMovementRules rules;
	private Location loc;
	
	public ChessPiece(Object id, Object color, Object type, PieceMovementRules rules, Location loc) {
		this.color = color;
		this.type = type;
		this.rules = rules;
		this.loc = loc;
		this.id = id;
	}
	
	public Object getId() {
		return id;
	}
	
	public Object getColor() {
		return color;
	}
	
	public PieceMovementRules getRules() {
		return rules;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public Object getType() {
		return type;
	}
	
}
