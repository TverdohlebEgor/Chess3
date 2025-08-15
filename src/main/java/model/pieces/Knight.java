package model.pieces;

import model.Direction;
import model.Position;
import model.enums.PieceColorEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Knight extends Piece{
    public Knight(PieceColorEnum color, Position pos) {
        super(color,pos);
    }

    @Override
    public boolean canMove(Position newPos, List<Piece> pieces){
        return false;
    }

    @Override
    public String getImagePath(){
        return commonImagePath("whiteKnight","blackKnight");
    }

    @Override
    public List<Direction> getDirections(){
        return new ArrayList<>();
    }
}
