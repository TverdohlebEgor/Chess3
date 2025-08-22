package model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.enums.PieceColorEnum;
import model.pieces.*;
import observer.NotificationHandler;

import java.util.ArrayList;
import java.util.List;

import static model.enums.PieceColorEnum.BLACK;
import static model.enums.PieceColorEnum.WHITE;
import static utils.Channels.UPDATE_VIEW;

@Slf4j
public class Board {
	@Getter
	private List<Piece> pieces = new ArrayList<>();


	public Board() {
		addInitialPieces(WHITE);
		addInitialPieces(BLACK);
	}

	private void addInitialPieces(PieceColorEnum color) {
		setPiece(new Rook(color, new Position(0, 7 - (color == WHITE ? 7 : 0))));
		setPiece(new Rook(color, new Position(7, 7 - (color == WHITE ? 7 : 0))));
		setPiece(new Knight(color, new Position(1, 7 - (color == WHITE ? 7 : 0))));
		setPiece(new Knight(color, new Position(6, 7 - (color == WHITE ? 7 : 0))));
		setPiece(new Bishop(color, new Position(2, 7 - (color == WHITE ? 7 : 0))));
		setPiece(new Bishop(color, new Position(5, 7 - (color == WHITE ? 7 : 0))));
		setPiece(new King(color, new Position(4, 7 - (color == WHITE ? 7 : 0))));
		setPiece(new Queen(color, new Position(3, 7 - (color == WHITE ? 7 : 0))));
		for (int x = 0; x < 8; x++) {
			setPiece(new Pawn(color, new Position(x, 7 - (color == WHITE ? 6 : 1))));
		}
	}

	public void reset() {
		for (int i = 0; i < 64; i++) {
			int x = i / 8;
			int y = i % 8;
			NotificationHandler.send(UPDATE_VIEW, "removePiece", new Position(x, y));
		}
		pieces.clear();
		addInitialPieces(WHITE);
		addInitialPieces(BLACK);
	}

	private void setPiece(Piece piece) {
		pieces.add(piece);
		NotificationHandler.send(UPDATE_VIEW, "setPiece", piece.getPosition(), piece);
	}

	public void removePiece(Position pos) {
		NotificationHandler.send(UPDATE_VIEW, "removePiece", pos);
		for (Piece piece : pieces) {
			if (piece.getPosition().equals(pos)) {
				pieces.remove(piece);
				break;
			}
		}
	}

	public boolean isOccupied(Position pos) {
		for (Piece piece : pieces) {
			if (piece.getPosition().equals(pos)) {
				return true;
			}
		}
		return false;
	}

	public Piece pieceIn(Position pos) {
		for (Piece piece : pieces) {
			if (piece.getPosition().equals(pos)) {
				return piece;
			}
		}
		return null;
	}

	public King getKing(PieceColorEnum kingColor) {
		for (Piece piece : pieces) {
			if (piece.getColor() == kingColor && piece instanceof King king) {
				return king;
			}
		}
		return null;
	}


	public void move(Move move) {
		Position initialPos = Position.fromString(move.UCImove().substring(0, 2));
		Position finalPos = Position.fromString(move.UCImove().substring(2, 4));
		if (isOccupied(finalPos)) {
			removePiece(finalPos);
		}
		if (move.addPiece() != null) {
			removePiece(initialPos);
			setPiece(move.addPiece());
		} else {
			handlingCastling(move);
			NotificationHandler.send(UPDATE_VIEW, "removePiece", initialPos);
			Piece pieceToMove = pieceIn(initialPos);
			pieceToMove.setPosition(finalPos);
			pieceToMove.setHasMoved(true);
		}
		updateView();
	}

	private void handlingCastling(Move move) {
		Rook rook;
		if (move.UCImove().equals("e1c1")) {
			rook = (Rook) pieceIn(new Position(0, 0));
			NotificationHandler.send(UPDATE_VIEW, "removePiece", rook.getPosition());
			rook.setPosition(Position.fromString("d1"));
			rook.setHasMoved(true);
		} else if (move.UCImove().equals("e1g1")) {
			rook = (Rook) pieceIn(new Position(7, 0));
			NotificationHandler.send(UPDATE_VIEW, "removePiece", rook.getPosition());
			rook.setPosition(Position.fromString("f1"));
			rook.setHasMoved(true);
		} else if (move.UCImove().equals("e8c8")) {
			rook = (Rook) pieceIn(new Position(0, 7));
			NotificationHandler.send(UPDATE_VIEW, "removePiece", rook.getPosition());
			rook.setPosition(Position.fromString("d8"));
			rook.setHasMoved(true);
		} else if (move.UCImove().equals("e8g8")) {
			rook = (Rook) pieceIn(new Position(7, 7));
			NotificationHandler.send(UPDATE_VIEW, "removePiece", rook.getPosition());
			rook.setPosition(Position.fromString("f8"));
			rook.setHasMoved(true);
		}
	}

	private void updateView() {
		NotificationHandler.send(UPDATE_VIEW, "drawBoard");
		for (Piece piece : pieces) {
			NotificationHandler.send(UPDATE_VIEW, "setPiece", piece.getPosition(), piece);
		}
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		int counter = 0;
		for (int y = 7; y >= 0; --y) {
			for (int x = 0; x < 8; ++x) {
				boolean pieceFounded = false;
				Position tempPos = new Position(x, y);
				for (Piece piece : pieces) {
					if (piece.getPosition().equals(tempPos)) {
						if (counter > 0) {
							stringBuilder.append(counter);
							counter = 0;
						}
						pieceFounded = true;
						stringBuilder.append(piece);
					}
				}
				if (!pieceFounded) {
					counter += 1;
				}
			}
			if (counter != 0) {
				stringBuilder.append(counter);
			}
			stringBuilder.append("/");
			counter = 0;
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		return stringBuilder.toString();
	}
}
