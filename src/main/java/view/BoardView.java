package view;

import model.Position;
import model.enums.BoardTileColors;
import model.pieces.Piece;
import observer.NotificationHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import static utils.Channels.*;

public class BoardView {
    private final JPanel chessboardPanel = new JPanel(new GridLayout(8, 8));
    private final HashMap<Position, BoardTileView> tiles = new HashMap<>();

    private final JTextArea terminalOutput = new JTextArea();
    private final JTextField terminalInput = new JTextField();

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
        drawBackground();
        terminalInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = terminalInput.getText();
                if (!inputText.isEmpty()) {
                    terminalOutput.append("> " + inputText + "\n");
                    terminalInput.setText("");
                    NotificationHandler.send(SEND_COMMAND,"sendCommand",inputText);
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


    private void drawBackground() {
        chessboardPanel.removeAll();
        tiles.clear();
        for (int i = 0; i < 64; i++) {
            int row = i / 8;
            int col = i % 8;
            BoardTileView square = new BoardTileView(((row + col) % 2 == 0) ? BoardTileColors.lightSquare : BoardTileColors.darkSquare);
            tiles.put(new Position(col, row), square);
            chessboardPanel.add(square);
        }
    }

    private void returnCommand(String returnString) {
        terminalOutput.append(returnString + "\n");
    }

    private void setPiece(Position pos, Piece piece) {
        tiles.get(pos).setPiece(piece.getImagePath());
    }
}
