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

import static model.enums.PieceColorEnum.BLACK;
import static model.enums.PieceColorEnum.WHITE;
import static utils.Channels.RETURN_COMMAND;
import static utils.Channels.SEND_COMMAND;

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
		if ("rotate".equals(inputText)) {
			boardView.rotateAndDraw();
		} else if ("boardStatus".equals(inputText)) {
			writeToTerminal(board.toString());
		} else if ("reset".equals(inputText)) {
			board.reset();
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

	private void solveConflict(List<Move> legalMoves) {
		List<List<Move>> toSolveMatx = new ArrayList<>();
		Map<Move,Boolean> visited = new HashMap<>();
		for (Move move : legalMoves) {
			if(Boolean.TRUE.equals(visited.getOrDefault(move,false))){
				continue;
			}
			List<Move> thisList = new ArrayList<>();
			thisList.add(move);
			visited.put(move,true);
			for (Move move2 : legalMoves) {
				if (!move.equals(move2) && move.SANmove().equals(move2.SANmove())) {
					thisList.add(move2);
					visited.put(move2,true);
				}
			}
			if (thisList.size() > 1) {
				toSolveMatx.add(thisList);
			}
		}

		for (List<Move> toSolve : toSolveMatx) {
			if (toSolve.size() > 2) {
				for (Move move : toSolve) {
					legalMoves.remove(move);
					legalMoves.add(
						new Move(
							move.SANmove().charAt(0) + move.UCImove().substring(0, 2) + move.SANmove().substring(2),
							move.UCImove(),
							null
						)
					);
				}
			} else if (toSolve.size() == 2){
				legalMoves.remove(toSolve.getFirst());
				legalMoves.remove(toSolve.getLast());
				String iniPos1 = toSolve.getFirst().UCImove().substring(0,2);
				String iniPos2 = toSolve.getLast().UCImove().substring(0,2);
				if(iniPos1.charAt(0) == iniPos2.charAt(0)){
					Move first = toSolve.getFirst();
					legalMoves.add(addMoveWithResolvedConflict(iniPos1.charAt(1),first));
					legalMoves.add(addMoveWithResolvedConflict(iniPos2.charAt(1),first));
				} else{
					Move last = toSolve.getLast();
					legalMoves.add(addMoveWithResolvedConflict(iniPos1.charAt(0),last));
					legalMoves.add(addMoveWithResolvedConflict(iniPos2.charAt(0),last));
				}
			}
		}
	}

	private Move addMoveWithResolvedConflict(char divergenceChar, Move move){
		return new Move(
			String.valueOf(move.SANmove().charAt(0)) +
				divergenceChar +
				move.SANmove().substring(1),
			move.UCImove(),
			null
		);
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
		turn = (turn == WHITE ? BLACK : WHITE);
	}
}
