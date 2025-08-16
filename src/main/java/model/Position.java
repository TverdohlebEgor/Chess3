package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Position {
    private int x;
    private int y;

    public void addToX(int val){
        this.x +=val;
    }

    public void addToY(int val){
        this.y +=val;
    }

    public Position copy(){
        return new Position(x,y);
    }

    public Position modified(int deltax, int deltay){
        return new Position(x+deltax, y+deltay);
    }
}
