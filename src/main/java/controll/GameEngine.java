package controll;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.Board;
import model.Move;
import model.enums.MoveType;
import model.enums.PieceColorEnum;
import model.pieces.Piece;
import observer.NotificationHandler;
import view.BoardView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.enums.PieceColorEnum.WHITE;
import static utils.Channels.RETURN_COMMAND;
import static utils.Channels.SEND_COMMAND;
import static utils.LegalMovesAdjuster.*;

@Slf4j
public class GameEngine {
	private final Map<String, String> specialCommands = new HashMap<>();
	private BoardView boardView;
	private Board board;
	private List<Move> legalMoves = new ArrayList<>();

	@Getter
	private PieceColorEnum turn = WHITE;

	@Getter
	private List<String> moves = new ArrayList<>();

	public GameEngine() {
		boardView = new BoardView();
		board = new Board();
		initSpecialCommands();
		NotificationHandler.subscribe(SEND_COMMAND, this);
	}

	public GameEngine(boolean testMode) {
		board = new Board();
	}

	public String boardStatus() {
		return board.toString();
	}

	public void resetBoard() {
		turn = WHITE;
		moves.clear();
		board.reset();
	}

	public String sendCommand(String inputText) {
		inputText = inputText.trim();
		String returnText = specialCommands.getOrDefault(inputText, null);
		if (returnText != null) {
			writeToTerminal(returnText);
		}
        switch (inputText) {
            case "rotate" -> boardView.rotateAndDraw();
            case "boardStatus" -> writeToTerminal(board.toString());
            case "reset" -> board.reset();
        }
		return handleMove(inputText,MoveType.SAN).UCImove();
	}

	public Move handleMove(String inputText, MoveType moveType) {
		legalMoves.clear();
		for (Piece piece : board.getPieces()) {
			if (piece.getColor() == turn) {
				legalMoves.addAll(piece.updateLegalMoves(board));
			}
		}
		solveConflict(legalMoves);
        addChecks(legalMoves,board);
        removeDangareousForKingMoves(legalMoves,board);
		Move toMove = null;
		for (Move move : legalMoves) {
			if ((moveType == MoveType.SAN && move.SANmove().equals(inputText))
			||  (moveType == MoveType.UCI && move.UCImove().equals(inputText))) {
				toMove = move;
				break;
			}
		}
		if (toMove == null) {
			illegalMove();
		} else {
			board.move(toMove);
			changeTurn();
			return toMove;
		}
		return new Move(null,"",null);
	}

	private void initSpecialCommands() {
		specialCommands.put("help", " WELCOME TO THE DRUNK ONE NIGHTER CHESS GAME " +
			"\n reset -> reset the game TODO" +
			"");
		specialCommands.put("turn", "It's " + turn.name().toLowerCase() + " turn");
	}

	private void illegalMove() {
		writeToTerminal("Illegal move !");
	}

	private void writeToTerminal(String text) {
		NotificationHandler.send(RETURN_COMMAND, "returnCommand", text);
	}

	private void changeTurn() {
		turn = turn.enemy();
	}
}
