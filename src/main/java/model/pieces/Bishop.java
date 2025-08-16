package model.pieces;

import model.Direction;
import model.Position;
import model.enums.PieceColorEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static model.enums.PieceColorEnum.WHITE;
import static utils.Constant.MAX_DISTANCE;

public class Bishop extends Piece{
    public Bishop(PieceColorEnum pieceColorEnum, Position pos) {
        super(pieceColorEnum,pos);
    }

    @Override
    public boolean canMove(Position newPos, List<Piece> pieces){
        List<Position> actualDirectionsList = null;
        for(List<Position> dirList : positionInDirection()){
            for(Position pos : dirList){
                if(newPos.equals(pos)){
                    actualDirectionsList = dirList;
                    break;
                }
            }
        }

        //New pos non in possible directions
        if(actualDirectionsList == null){
            return false;
        }

        for(Position pos : actualDirectionsList){
            for(Piece piece : pieces){
                //Trovato ostacolo
                if(piece.getPosition().equals(pos) && piece.getColor() == this.getColor()){
                    return false;
                }
            }
            // trovato mia casella
            if(pos.equals(newPos)){
                return true;
            }
        }

        return true;
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
