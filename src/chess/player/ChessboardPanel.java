package chess.player;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import chess.misc.*;


public class ChessboardPanel extends JPanel implements MouseListener, MouseMotionListener, ChessPlayer {
	
	private Integer lock = new Integer(0);
	
	private Color lightColor = Color.getHSBColor(0.5f, 0.1f, 1);
	private Color darkColor = Color.getHSBColor(0.5f, 1, 0.4f);

	private Location fromPos;
	private Location toPos;
	
	private ChessMove move;
	private Board board;
	
	private boolean moveReady;
	
	private Color selectedColor;
	private Object selectedPiece;
	private int lastX;
	private int lastY;
	
	private Map<Chess.TYPES, Float> colors = new HashMap<>();
	private Map<Chess.TYPES, String> symbols = new HashMap<>();
	
	public ChessboardPanel() {
		colors.put(Chess.TYPES.KING, 0.14f);
		colors.put(Chess.TYPES.QUEEN, 0.28f);
		colors.put(Chess.TYPES.PAWN, 0.42f);
		colors.put(Chess.TYPES.KNIGHT, 0.56f);
		colors.put(Chess.TYPES.BISHOP, 0.70f);
		colors.put(Chess.TYPES.ROOK, 0.84f);
		colors.put(Chess.TYPES.PAWN_B, 0.42f);
		
		symbols.put(Chess.TYPES.KING, "K");
		symbols.put(Chess.TYPES.QUEEN, "Q");
		symbols.put(Chess.TYPES.PAWN, "P");
		symbols.put(Chess.TYPES.KNIGHT, "N");
		symbols.put(Chess.TYPES.BISHOP, "B");
		symbols.put(Chess.TYPES.ROOK, "R");
		symbols.put(Chess.TYPES.PAWN_B, "P");

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
		int size = (int)(Math.min(getWidth(), getHeight()) / 8);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board != null && board.isOccupied(new Location(i, j)) && board.getPieceAt(new Location(i, j)) != selectedPiece) {
					ChessPiece p = board.getPieceAt(new Location(i, j));
					g.setColor(Color.getHSBColor(colors.get(p.getType()), 0.7f, 1));
				} else {
					if (i % 2 == j % 2) {
						g.setColor(lightColor);
					} else {
						g.setColor(darkColor);
					}
				}
				
				g.fillRect(size * i, size * (7 - j), size, size);
				
				if (board.isOccupied(new Location(i, j))) {
					String symbol = String.valueOf(symbols.get(board.getPieceAt(new Location(i, j)).getType())).substring(0, 1);
					g.setColor((board.getPieceAt(new Location(i, j)).getColor() == Chess.COLORS.WHITE) ? Color.WHITE : Color.BLACK);
					g.setFont(new Font("HELVETICA", 0, size));
					g.drawString(symbol.toUpperCase(), (int)(size * (i + 0.15)), (int)(size * ((7 - j) + 0.9)));
				}
				if (selectedColor != null) {
					int r = size / 3;
					g.setColor(selectedColor);
					g.fillOval(lastX - r, lastY - r, 2 * r, 2 * r);
				}
			}
		}
		} catch (Exception e) {
			
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		fromPos = screenToBoard(e.getX(), e.getY());
		//if (board.isValidPosition(fromPos) && board.isOccupied(fromPos)) {
			selectedColor = Color.getHSBColor((float)Math.random(), 0.7f, 1);
			selectedPiece = board.getPieceAt(fromPos);
		//}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		toPos = screenToBoard(e.getX(), e.getY());
		move = new StandardChessMove(fromPos, toPos);
		synchronized (lock) {
			lock.notify();
		}
		selectedColor = null;
		selectedPiece = null;
		repaint();
	}
		
	private Location screenToBoard(int screenX, int screenY) {
		float min = (float)(Math.min(getWidth(), getHeight()));
		screenX = (int)((screenX / min) * 8);
		screenY = (int)((screenY / min) * 8);
		
		screenY = 7 - screenY;
		return new Location(screenX, screenY);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		repaint();
		lastX = e.getX();
		lastY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public Chess.COLORS getColor() {
		return Chess.COLORS.WHITE;
	}

	@Override
	public ChessMove nextMove(Board board) {
		this.board = board;
		repaint();
		ChessMove result;
		synchronized(lock) {
			while (move == null) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		result = move;
		move = null;
		return result;
	}
	
}
