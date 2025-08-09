package model.pieces;

import model.enums.PieceColorEnum;
import model.Position;

import static utils.Constant.piecesImageCommonPath;

public class King extends Piece{
    public King(PieceColorEnum color) {
        super(color);
    }

    @Override
    public boolean move(Position newPos){
        return false;
    }

    @Override
    public String getImagePath(){
        return piecesImageCommonPath + (this.getColor() == PieceColorEnum.WHITE ? "whiteKing" : "blackKing") + ".png";
    }
}
