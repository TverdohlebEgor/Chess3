package model.pieces;

import model.Direction;
import model.Position;
import model.enums.PieceColorEnum;

import java.util.List;

import static model.enums.PieceColorEnum.WHITE;
import static utils.Constant.MAX_DISTANCE;

public class Bishop extends Piece{
    public Bishop(PieceColorEnum pieceColorEnum, Position pos) {
        super(pieceColorEnum,pos);
    }

    @Override
    public String getName(){
        return "B";
    }

    @Override
    public String getImagePath(){
        return commonImagePath("whiteBishop","blackBishop");
    }

    @Override
    public List<Direction> getDirections(){
       return List.of(
           new Direction(1,1, MAX_DISTANCE),
           new Direction(1,-1, MAX_DISTANCE),
           new Direction(-1,1, MAX_DISTANCE),
           new Direction(-1,-1, MAX_DISTANCE)
       );
    }
    @Override
    public String toString(){
        return getColor() == WHITE ? "B" : "b";
    }
}
