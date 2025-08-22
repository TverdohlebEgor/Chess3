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

	String commonImagePath(String nameWhite, String nameBlack) {
		return new File(
			piecesImageCommonPath,
			(this.getColor() == PieceColorEnum.WHITE ? nameWhite : nameBlack) + ".png")
			.toPath()
			.toString();
	}

	List<List<Position>> positionInDirection() {
		List<List<Position>> result = new ArrayList<>();
		for (Direction dir : getDirections()) {
			Position tempPos = this.getPosition().copy();
			List<Position> directionList = new ArrayList<>();
			int tempDis = 0;
			tempPos.addToX(dir.getX());
			tempPos.addToY(dir.getY());
			while (
				tempPos.getY() >= 0 &&
					tempPos.getX() >= 0 &&
					tempPos.getY() < 8 &&
					tempPos.getX() < 8 &&
					tempDis < dir.getDistance()
			) {
				tempDis += 1;
				directionList.add(tempPos.copy());
				tempPos.addToX(dir.getX());
				tempPos.addToY(dir.getY());
			}
			result.add(directionList);
		}
		return result;
	}
}
