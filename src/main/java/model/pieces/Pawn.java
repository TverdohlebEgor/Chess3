package model.pieces;

import lombok.Setter;
import model.Direction;
import model.Position;
import model.enums.PieceColorEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;
import static model.enums.PieceColorEnum.BLACK;
import static model.enums.PieceColorEnum.WHITE;

public class Pawn extends Piece{

    public Pawn(PieceColorEnum color, Position pos) {
        super(color, pos);
    }

    @Override
    public boolean canMove(Position newPos, List<Piece> pieces){
        int yDelta = this.getPosition().getY() - newPos.getY();
        int xDelta = this.getPosition().getX() - newPos.getX();
        int yDistance = abs(yDelta);
        int xDistance = abs(xDelta);
        if((yDistance > 2 || yDistance <= 0) || (isHasMoved() && yDistance == 2)) {
            return false;
        }
        if(xDistance > 1){
            return false;
        }
        if(xDistance == 1){
            //CATTURE
            for(Piece piece : pieces){
                if(piece.getPosition().equals(newPos)
                && piece.getColor() != this.getColor()
                && yDistance == 1){
                    return true;
                }
            }
            return false;
        }
        for(Position pos : positionInDirection().getFirst()){
            for(Piece piece : pieces){
                if(piece.getPosition().equals(pos)){
                    return false;
                }
            }
        }
        //LATO SBAGLIATO
        if((yDelta >= 1 && getColor() == WHITE) || (yDelta <= -1 && getColor() == BLACK)){
            return false;
        }
        return true;
    }

    @Override
    public String getImagePath(){
        return commonImagePath("whitePawn","blackPawn");
    }

    @Override
    public List<Direction> getDirections(){
        return List.of(
            new Direction(
                0,
                this.getColor() == WHITE ? 1 : -1,
                isHasMoved() ? 1 : 2
            )
        );
    }
    @Override
    public String toString(){
        return getColor() == WHITE ? "P" : "p";
    }
}
