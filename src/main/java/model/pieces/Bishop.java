package model.pieces;

import model.Direction;
import model.Position;
import model.enums.PieceColorEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static utils.Constant.MAX_DISTANCE;

public class Bishop extends Piece{
    public Bishop(PieceColorEnum pieceColorEnum, Position pos) {
        super(pieceColorEnum,pos);
    }

    @Override
    public boolean canMove(Position newPos, List<Piece> pieces){
        return false;
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
}
