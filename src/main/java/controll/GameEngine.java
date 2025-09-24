package controll;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.Board;
import model.Move;
import model.enums.MoveType;
import model.enums.PieceColorEnum;
import model.pieces.Piece;

import java.util.*;

import static model.enums.PieceColorEnum.WHITE;
import static utils.LegalMovesAdjuster.*;

@Slf4j
public class GameEngine {
	//private BoardView boardView;
	private final Board board;
	private final List<Move> legalMoves = new ArrayList<>();
    private boolean testMode = false;

	@Getter
	private PieceColorEnum turn = WHITE;

	@Getter
	private List<String> moves = new ArrayList<>();

	public GameEngine() {
		board = new Board();
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
			log.info("Illegal move:"+inputText);
		} else {
			board.move(toMove.get());
			changeTurn();
			return toMove.get();
		}
		return new Move(null,"",null);
	}

	private void changeTurn() {
		turn = turn.enemy();
	}
}
