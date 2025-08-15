package model.pieces;

import lombok.Getter;
import lombok.Setter;
import model.Direction;
import model.Position;
import model.enums.PieceColorEnum;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static utils.Constant.piecesImageCommonPath;

public abstract class Piece {
    @Getter @Setter
    private boolean hasMoved = false;
    @Getter @Setter
    private PieceColorEnum color;
    @Setter
    private Position position;
    public Position getPosition(){
        return position.copy();
    }
    public Piece(PieceColorEnum  color, Position position){
        this.color = color;
        this.position = position;
    }
    public abstract List<Direction> getDirections();
    public abstract boolean canMove(Position finalPos, List<Piece> pieces);
    public abstract String getImagePath();
    String commonImagePath(String nameWhite, String nameBlack){
        return new File(
            piecesImageCommonPath,
            (this.getColor() == PieceColorEnum.WHITE ? nameWhite : nameBlack) + ".png")
            .toPath()
            .toString();
    }

    List<Position> positionInDirection(){
        List<Position> result = new ArrayList<>();
        for(Direction dir : getDirections()){
            Position tempPos = this.getPosition();
            int tempDis = 0;
            while(
                tempPos.getY() >= 0 &&
                tempPos.getX() >= 0 &&
                tempPos.getY() < 8 &&
                tempPos.getX() < 8 &&
                tempDis < dir.getDistance()
            ) {
                tempPos.addToX(dir.getX());
                tempPos.addToY(dir.getY());
                tempDis+=1;
                result.add(tempPos.copy());
            }
        }
        return result;
    }
}
