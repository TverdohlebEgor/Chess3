package model.pieces;

import lombok.Getter;
import lombok.Setter;
import model.Board;
import model.Direction;
import model.Move;
import model.Position;
import model.enums.PieceColorEnum;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static model.enums.PieceColorEnum.BLACK;
import static model.enums.PieceColorEnum.WHITE;
import static utils.Constant.piecesImageCommonPath;
import static utils.Util.positionInBound;

public abstract class Piece {
	@Getter
	@Setter
	private boolean hasMoved = false;
	@Getter
	@Setter
	private PieceColorEnum color;
	@Setter
	private Position position;

    public void setPositionFromString(String posName){
       this.position = Position.fromString(posName);
    }

	public Position getPosition() {
		return position.copy();
	}

	public Piece(PieceColorEnum color, Position position) {
		this.color = color;
		this.position = position;
	}

	public abstract List<Direction> getDirections();

	public abstract String getName();

	public List<Move> updateLegalMoves(Board board) {
		List<Move> legalMoves = new ArrayList<>();
		for (Direction dir : getDirections()) {
			for (int distance = 1; distance <= dir.getDistance(); distance++) {
				Position finalPossiblePos = getPosition().modified(dir.getX() * distance, dir.getY() * distance);
				if (!positionInBound(finalPossiblePos)) {
					break;
				}
				if (board.isOccupied(finalPossiblePos)) {
					if (board.pieceIn(finalPossiblePos).getColor() == this.getColor()) {
						break;
					} else {
						legalMoves.add(new Move(
							getName() + "x" + finalPossiblePos,
							getPosition().toString() + finalPossiblePos,
							null
						));
						break;
					}
				} else {
					legalMoves.add(new Move(
						getName() + finalPossiblePos,
						getPosition().toString() + finalPossiblePos,
						null
					));
				}
			}
		}
		return legalMoves;
	}

	public abstract String getImagePath();

    public abstract Piece copy();

	String commonImagePath(String nameWhite, String nameBlack) {
		return new File(
			piecesImageCommonPath,
			(this.getColor() == WHITE ? nameWhite : nameBlack) + ".png")
			.toPath()
			.toString();
	}
}
