package model.pieces;

import lombok.Getter;
import lombok.Setter;
import model.enums.PieceColorEnum;
import model.Position;

public abstract class Piece {
    @Getter @Setter
    private PieceColorEnum color;
    public Piece(PieceColorEnum  color){
        this.color = color;
    }
    public abstract boolean move(Position finalPos);
    public abstract String getImagePath();
}
