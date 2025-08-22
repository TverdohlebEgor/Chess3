package model.pieces;

import model.Board;
import model.Direction;
import model.Move;
import model.Position;
import model.enums.PieceColorEnum;

import java.util.List;

import static model.enums.PieceColorEnum.BLACK;
import static model.enums.PieceColorEnum.WHITE;

public class King extends Piece {
	public King(PieceColorEnum color, Position pos) {
		super(color, pos);
	}

	@Override
	public List<Move> updateLegalMoves(Board board) {
		List<Move> legalMoves = super.updateLegalMoves(board);

		int row = 0;
		if (getColor() == BLACK) row = 7;

		if (!getPosition().toString().equals("e"+(row+1)) || isHasMoved()) {
			return legalMoves;
		}
		if (board.pieceIn(new Position(7, row)) instanceof Rook rook
			&& !rook.isHasMoved()
			&& !board.isOccupied(new Position(5, row))
			&& !board.isOccupied(new Position(6, row))
		) {
			legalMoves.add(new Move("O-O", "e"+(row+1)+"g"+(row+1), null));
		} else if (
			board.pieceIn(new Position(0, row)) instanceof Rook rook
				&& !rook.isHasMoved()
				&& !board.isOccupied(new Position(1, row))
				&& !board.isOccupied(new Position(2, row))
				&& !board.isOccupied(new Position(3, row))
		) {
			legalMoves.add(new Move("O-O-O", "e"+(row+1)+"c"+(row+1), null));
		}

		return legalMoves;
	}

	@Override
	public String getName() {
		return "K";
	}

	@Override
	public String getImagePath() {
		return commonImagePath("whiteKing", "blackKing");
	}

	@Override
	public List<Direction> getDirections() {
		return List.of(
			new Direction(0, 1, 1),
			new Direction(0, -1, 1),
			new Direction(1, 0, 1),
			new Direction(-1, 0, 1),
			new Direction(1, 1, 1),
			new Direction(1, -1, 1),
			new Direction(-1, 1, 1),
			new Direction(-1, -1, 1)
		);
	}

	@Override
	public String toString() {
		return getColor() == WHITE ? "K" : "k";
	}
}
