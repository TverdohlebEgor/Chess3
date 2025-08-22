package model.pieces;

import model.Board;
import model.Direction;
import model.Move;
import model.Position;
import model.enums.PieceColorEnum;

import java.util.ArrayList;
import java.util.List;

import static model.enums.PieceColorEnum.WHITE;
import static utils.Constant.MAX_DISTANCE;
import static utils.Util.positionInBound;

public class Queen extends Piece{
    public Queen(PieceColorEnum color, Position pos) {
        super(color, pos);
    }

    @Override
    public String getName(){
        return "Q";
    }

    @Override
    public String getImagePath(){
        return commonImagePath("whiteQueen","blackQueen");
    }

    @Override
    public List<Direction> getDirections(){
        return List.of(
            new Direction(0,1,MAX_DISTANCE),
            new Direction(0,-1,MAX_DISTANCE),
            new Direction(1,0,MAX_DISTANCE),
            new Direction(-1,0,MAX_DISTANCE),
            new Direction(1,1,MAX_DISTANCE),
            new Direction(1,-1,MAX_DISTANCE),
            new Direction(-1,1,MAX_DISTANCE),
            new Direction(-1,-1,MAX_DISTANCE)
        );
    }

    @Override
    public String toString(){
        return getColor() == WHITE ? "Q" : "q";
    }
}
