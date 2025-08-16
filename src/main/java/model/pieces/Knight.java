package model.pieces;

import model.Direction;
import model.Position;
import model.enums.PieceColorEnum;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static model.enums.PieceColorEnum.WHITE;

public class Knight extends Piece{
    public Knight(PieceColorEnum color, Position pos) {
        super(color,pos);
    }

    @Override
    public boolean canMove(Position newPos, List<Piece> pieces){
        boolean newPosInPossibleMoves = false;
        for(List<Position> dirList : positionInDirection()){
            if(dirList.contains(newPos)){
                newPosInPossibleMoves = true;
                break;
            }
        }

        if(newPosInPossibleMoves == false){
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
        return commonImagePath("whiteKnight","blackKnight");
    }

    @Override
    public List<Direction> getDirections(){
        return new ArrayList<>();
    }

    @Override
    List<List<Position>> positionInDirection(){
        List<List<Position>> result = new ArrayList<>();
        Position tempoPos = this.getPosition().copy();
        for (int deltaX = -2, turn = 0; turn < 4; turn = turn + 1, deltaX = (turn % 2 == 1) ? -deltaX : 1){
            int deltaY = abs(deltaX) == 2 ? 1 : 2;
            if(isLegalPosition(tempoPos.modified(deltaX,deltaY))){
                result.add(List.of(tempoPos.modified(deltaX,deltaY)));
            }
            if(isLegalPosition(tempoPos.modified(deltaX,-deltaY))){
                result.add(List.of(tempoPos.modified(deltaX,-deltaY)));
            }
        }
        return result;
    }

    private boolean isLegalPosition(Position pos){
        return (pos.getX() >= 0 && pos.getY() < 8 && pos.getY() >= 0 && pos.getX() < 8);
    }

    @Override
    public String toString(){
        return getColor() == WHITE ? "N" : "n";
    }
}
