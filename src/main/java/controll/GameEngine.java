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

import java.util.*;

import static model.enums.PieceColorEnum.WHITE;
import static utils.Channels.RETURN_COMMAND;
import static utils.Channels.SEND_COMMAND;
import static utils.LegalMovesAdjuster.*;

@Slf4j
public class GameEngine {
	private final Map<String, String> specialCommands = new HashMap<>();
	private BoardView boardView;
	private final Board board;
	private final List<Move> legalMoves = new ArrayList<>();
    private boolean testMode = false;

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
		board = new Board(true);
        this.testMode = true;
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
            case "rotate" -> {
                boardView.rotateAndDraw();
                return "";
            }
            case "boardStatus" -> {
                writeToTerminal(board.toString());
                return "";
            }
            case "reset" -> {
                resetBoard();
                return "";
            }
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
        removeDangareousForKingMoves(legalMoves,board);
		solveConflict(legalMoves);
        addChecks(legalMoves,board);
		Optional<Move> toMove = Optional.empty();
		for (Move move : legalMoves) {
			if ((moveType == MoveType.SAN && move.SANmove().equals(inputText))
			||  (moveType == MoveType.UCI && move.UCImove().equals(inputText))) {
				toMove = Optional.of(move);
				break;
			}
		}
		if (toMove.isEmpty()) {
			illegalMove();
		} else {
			board.move(toMove.get());
			changeTurn();
			return toMove.get();
		}
		return new Move(null,"",null);
	}

	private void initSpecialCommands() {
		specialCommands.put("help", """
                 Welcome back to the third season of I reimplement chess when I'm bored \
                rotate
                boardStatus
                reset""");
		specialCommands.put("turn", "It's " + turn.name().toLowerCase() + " turn");
	}

	private void illegalMove() {
		writeToTerminal("Illegal move !");
	}

	private void writeToTerminal(String text) {
        if(!testMode)NotificationHandler.send(RETURN_COMMAND, "returnCommand", text);
	}

	private void changeTurn() {
		turn = turn.enemy();
	}
}
