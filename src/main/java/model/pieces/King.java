package model.pieces;

import model.Direction;
import model.Position;
import model.enums.PieceColorEnum;

import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;
import static model.enums.PieceColorEnum.WHITE;

public class King extends Piece{
    public King(PieceColorEnum color, Position pos) {
        super(color,pos);
    }

    @Override
    public boolean canMove(Position newPos, List<Piece> pieces){
        int yDistance = abs(this.getPosition().getY() - newPos.getY());
        int xDistance = abs(this.getPosition().getX() - newPos.getX());
        if(yDistance > 1 || xDistance > 1) {
            return false;
        }
        boolean newPosInPossibleDirections = false;
        for(List<Position> dirList : positionInDirection()){
            for(Position pos: dirList){
                if(newPos.equals(pos)){
                    newPosInPossibleDirections = true;
                    break;
                }
            }
        }
        if(!newPosInPossibleDirections){
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
        return commonImagePath("whiteKing","blackKing");
    }

    @Override
    public List<Direction> getDirections(){
        return List.of(
            new Direction(0,1,1),
            new Direction(0,-1,1),
            new Direction(1,0,1),
            new Direction(-1,0,1),
            new Direction(1,1,1),
            new Direction(1,-1,1),
            new Direction(-1,1,1),
            new Direction(-1,-1,1)
        );
    }

    @Override
    public String toString(){
        return getColor() == WHITE ? "K" : "k";
    }
}
