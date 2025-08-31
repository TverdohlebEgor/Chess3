package model.pieces;

import model.Board;
import model.Direction;
import model.Move;
import model.Position;
import model.enums.PieceColorEnum;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static model.enums.PieceColorEnum.WHITE;
import static utils.Util.positionInBound;

public class Knight extends Piece {
	public Knight(PieceColorEnum color, Position pos) {
		super(color, pos);
	}

	@Override
	public String getName() {
		return "N";
	}

	@Override
	public List<Move> updateLegalMoves(Board board) {
		List<Move> legalMoves = new ArrayList<>();
		for (int deltaX = -2, turn = 0; turn < 4; turn = turn + 1, deltaX = (turn % 2 == 1) ? -deltaX : 1) {
			int deltaY = abs(deltaX) == 2 ? 1 : 2;
			for (int turn2 = 0; turn2 < 2; turn2++, deltaY *= -1) {
				Position finalPossiblePos = getPosition().modified(deltaX, deltaY);
				if (positionInBound(finalPossiblePos)) {
					if (board.isOccupied(finalPossiblePos)) {
						if (board.pieceIn(finalPossiblePos).getColor() != getColor()) {
							legalMoves.add(
								new Move(
									getName() + "x" + finalPossiblePos,
									getPosition().toString() + finalPossiblePos,
									null
								)
							);
						}
					} else {
						legalMoves.add(
							new Move(
								getName() + finalPossiblePos,
								getPosition().toString() + finalPossiblePos,
								null
							)
						);
					}
				}
			}
		}
		return legalMoves;
	}

	@Override
	public String getImagePath() {
		return commonImagePath("whiteKnight", "blackKnight");
	}

    @Override
    public Knight copy(){ return new Knight(getColor(),getPosition().copy());}

	@Override
	public List<Direction> getDirections() {
		return new ArrayList<>();
	}

	@Override
	public String toString() {
		return getColor() == WHITE ? "N" : "n";
	}
}
