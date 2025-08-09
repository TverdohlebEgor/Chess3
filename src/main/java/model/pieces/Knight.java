package model.pieces;

import model.enums.PieceColorEnum;
import model.Position;

import static utils.Constant.piecesImageCommonPath;

public class Knight extends Piece{
    public Knight(PieceColorEnum color) {
        super(color);
    }

    @Override
    public boolean move(Position newPos){
        return false;
    }

    @Override
    public String getImagePath(){
        return piecesImageCommonPath + (this.getColor() == PieceColorEnum.WHITE ? "whiteKnight" : "blackKnight") + ".png";
    }
}
