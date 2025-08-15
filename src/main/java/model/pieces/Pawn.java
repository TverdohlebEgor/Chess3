package model.pieces;

import lombok.Setter;
import model.Direction;
import model.Position;
import model.enums.PieceColorEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;
import static model.enums.PieceColorEnum.WHITE;

public class Pawn extends Piece{

    public Pawn(PieceColorEnum color, Position pos) {
        super(color, pos);
    }

    @Override
    public boolean canMove(Position newPos, List<Piece> pieces){
        int yDistance = abs(this.getPosition().getY() - newPos.getY());
        int xDistance = abs(this.getPosition().getX() - newPos.getX());
        if((yDistance > 2 || yDistance <= 0) || (isHasMoved() && yDistance == 2)) {
            return false;
        }
        if(xDistance > 1){
            return false;
        }
        if(xDistance == 1){
            //TODO Handle captures and checks
            return false;
        }
        for(Position pos : positionInDirection()){
            for(Piece piece : pieces){
                if(piece.getPosition().equals(pos)){
                    return false;
                }
            }
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
}
