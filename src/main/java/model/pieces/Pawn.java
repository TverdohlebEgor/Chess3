package model.pieces;

import model.Board;
import model.Direction;
import model.Move;
import model.Position;
import model.enums.PieceColorEnum;

import java.util.ArrayList;
import java.util.List;

import static model.enums.PieceColorEnum.WHITE;
import static utils.Util.positionInBound;

public class Pawn extends Piece {

	public Pawn(PieceColorEnum color, Position pos) {
		super(color, pos);
	}

	@Override
	public String getName() {
		return "P";
	}

	@Override
	public List<Move> updateLegalMoves(Board board) {
		List<Move> legalMoves = new ArrayList<>();
		Direction pawnDir = getDirections().getFirst();
		for (int yDistance = 1; yDistance <= pawnDir.getDistance(); ++yDistance) {
			Position finalPossiblePos = getPosition().modified(0, yDistance * pawnDir.getY());
			if (board.pieceIn(finalPossiblePos) == null) {
				eventuallyAddPromotionAndAddToList(
					legalMoves,
					new Move(
						finalPossiblePos.toString(),
						getPosition().toString() + finalPossiblePos,
						null
					)
				);
			}
		}
		//x = 1 && x = -1
		for (int x = 1; x >= -1; x -= 2) {
			Position finalPossiblePos = getPosition().modified(x, pawnDir.getY());
			if (enemyIn(finalPossiblePos, board)) {
				eventuallyAddPromotionAndAddToList(
					legalMoves,
					new Move(
						getPosition().toString().charAt(0) + "x" + finalPossiblePos,
						getPosition().toString() + finalPossiblePos,
						null
					)
				);
			}
		}
		return legalMoves;
	}

	private void eventuallyAddPromotionAndAddToList(List<Move> legalMoves, Move move) {
		Position finalPos = Position.fromString(move.UCImove().substring(2, 4));
		String finalPosRow = String.valueOf(move.UCImove().charAt(3));
		if ("8".equals(finalPosRow) || "1".equals(finalPosRow)) {
			legalMoves.add(new Move(move.SANmove() + "=Q", move.UCImove() + (getColor() == WHITE ? "Q" : "q"), new Queen(getColor(), finalPos)));
			legalMoves.add(new Move(move.SANmove() + "=N", move.UCImove() + (getColor() == WHITE ? "N" : "n"), new Knight(getColor(), finalPos)));
			legalMoves.add(new Move(move.SANmove() + "=B", move.UCImove() + (getColor() == WHITE ? "B" : "b"), new Bishop(getColor(), finalPos)));
			legalMoves.add(new Move(move.SANmove() + "=R", move.UCImove() + (getColor() == WHITE ? "R" : "r"), new Rook(getColor(), finalPos)));
		} else {
			legalMoves.add(move);
		}
	}

	public boolean enemyIn(Position finalPossiblePos, Board board) {
		return positionInBound(finalPossiblePos) &&
			board.isOccupied(finalPossiblePos) &&
			board.pieceIn(finalPossiblePos).getColor() != this.getColor();
	}

	@Override
	public String getImagePath() {
		return commonImagePath("whitePawn", "blackPawn");
	}

	@Override
	public List<Direction> getDirections() {
		return List.of(
			new Direction(
				0,
				this.getColor() == WHITE ? 1 : -1,
				isHasMoved() ? 1 : 2
			)
		);
	}

	@Override
	public String toString() {
		return getColor() == WHITE ? "P" : "p";
	}
}
