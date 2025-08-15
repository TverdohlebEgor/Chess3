package view;

import model.Position;
import model.enums.BoardTileColors;
import model.enums.PieceColorEnum;
import model.pieces.Piece;
import observer.NotificationHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import static model.enums.PieceColorEnum.BLACK;
import static model.enums.PieceColorEnum.WHITE;
import static utils.Channels.*;

public class BoardView {
	private final JPanel chessboardPanel = new JPanel(new GridLayout(8, 8));
	private final HashMap<Position, BoardTileView> tiles = new HashMap<>();

	private final JTextArea terminalOutput = new JTextArea();
	private final JTextField terminalInput = new JTextField();

	private PieceColorEnum boardPerspective = WHITE;

	public BoardView() {
		NotificationHandler.subscribe(UPDATE_VIEW, this);
		NotificationHandler.subscribe(RETURN_COMMAND, this);
		initPanel();
	}

	private void initPanel() {
		JFrame frame = new JFrame("8x8 Chessboard with Terminal");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel terminalPanel = new JPanel(new BorderLayout());
		terminalPanel.setBorder(BorderFactory.createTitledBorder("Terminal Output"));
		terminalOutput.setEditable(false);
		terminalOutput.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(terminalOutput);
		drawBoard();
		terminalInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String inputText = terminalInput.getText();
				if (!inputText.isEmpty()) {
					terminalOutput.append("> " + inputText + "\n");
					terminalInput.setText("");
					NotificationHandler.send(SEND_COMMAND, "sendCommand", inputText);
				}
			}
		});
		terminalPanel.add(scrollPane, BorderLayout.CENTER);
		terminalPanel.add(terminalInput, BorderLayout.SOUTH);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chessboardPanel, terminalPanel);
		splitPane.setDividerLocation(300);
		frame.add(splitPane);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void updateView(){
		chessboardPanel.revalidate();
		chessboardPanel.repaint();
	}

	public void rotateAndDraw(){
		rotate();
		drawConsideringPerspective();
	}

	public void rotate() {
		boardPerspective = boardPerspective == WHITE ? BLACK : WHITE;
	}

	public void drawConsideringPerspective(){
		chessboardPanel.removeAll();
		if(boardPerspective == WHITE){
			for (int y = 7; y >= 0 ; --y){
				for(int x = 0 ; x < 8 ; ++x){
					chessboardPanel.add(tiles.get(new Position(x,y)));
				}
			}
		} else {
			for (int y = 0; y < 8 ; ++y){
				for(int x = 7 ; x >= 0 ; --x){
					chessboardPanel.add(tiles.get(new Position(x,y)));
				}
			}
		}
		updateView();
	}

	private void drawBoard() {
		tiles.clear();
		for (int i = 63; i >= 0; i--) {
			int row = i / 8;
			int col = i % 8;
			BoardTileView square = new BoardTileView(((row + col) % 2 == 0) ? BoardTileColors.lightSquare : BoardTileColors.darkSquare);
			tiles.put(new Position(col, row), square);
		}
		drawConsideringPerspective();
	}

	private void returnCommand(String returnString) {
		terminalOutput.append(returnString + "\n");
	}

	private void setPiece(Position pos, Piece piece) {
		tiles.get(pos).setPiece(piece.getImagePath());
		updateView();
	}

	private void removePiece(Position pos){
		tiles.get(pos).removePiece();
		updateView();
	}
}
