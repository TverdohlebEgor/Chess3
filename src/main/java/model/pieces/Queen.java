package model.pieces;

import model.Direction;
import model.Position;
import model.enums.PieceColorEnum;

import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;
import static utils.Constant.MAX_DISTANCE;

public class Queen extends Piece{
    public Queen(PieceColorEnum color, Position pos) {
        super(color, pos);
    }

    @Override
    public boolean canMove(Position newPos, List<Piece> pieces){
        if(!positionInDirection().contains(newPos)){
            return false;
        }
        for(Piece piece : pieces){
          if(piece.getPosition().equals(newPos) && piece.getColor() == this.getColor()){
                return false;
          }
        }
        return true;
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
}
