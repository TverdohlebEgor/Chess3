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

    public Move copy(String SANMove){ return new Move(SANmove, this.UCImove, this.addPiece);}
    public Move copy(String SANMove, String UCImove){ return new Move(SANmove, UCImove, this.addPiece);}
}
