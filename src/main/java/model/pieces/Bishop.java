package model.pieces;

import model.enums.PieceColorEnum;
import model.Position;

import static utils.Constant.piecesImageCommonPath;

public class Bishop extends Piece{
    public Bishop(PieceColorEnum pieceColorEnum) {
        super(pieceColorEnum);
    }

    @Override
    public boolean move(Position newPos){
        return false;
    }

    @Override
    public String getImagePath(){
        return piecesImageCommonPath + (this.getColor() == PieceColorEnum.WHITE ? "whiteBishop" : "blackBishop") + ".png";
    }
}
