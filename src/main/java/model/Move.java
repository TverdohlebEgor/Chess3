package model;

import model.pieces.Piece;

public record Move(
	String SANmove,
	String UCImove,
	Piece addPiece
) {
}
