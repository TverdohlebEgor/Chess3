package model;

import model.pieces.Piece;

public record Move(
	String SANmove,
	String UCImove,
	Piece addPiece
) {
    public Position initialPosition(){
        return Position.fromString(UCImove.substring(0,2));
    }

    public Position finalPosition(){
        return Position.fromString(UCImove.substring(2,4));
    }
}
