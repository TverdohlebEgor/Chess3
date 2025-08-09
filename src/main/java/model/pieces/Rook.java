package model.pieces;

import model.enums.PieceColorEnum;
import model.Position;

import static utils.Constant.piecesImageCommonPath;

public class Rook extends Piece{
    public Rook(PieceColorEnum pieceColorEnum) {
        super(pieceColorEnum);
    }

    @Override
    public boolean move(Position newPos){
        return false;
    }

    @Override
    public String getImagePath(){
        return piecesImageCommonPath + (this.getColor() == PieceColorEnum.WHITE ? "whiteRook" : "blackRook") + ".png";
    }
}
