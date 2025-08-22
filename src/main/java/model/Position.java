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

    public static Position fromString(String stringPos){
        int x = switch (stringPos.substring(0,1)){
            case "a" -> 1;
            case "b" -> 2;
            case "c" -> 3;
            case "d" -> 4;
            case "e" -> 5;
            case "f" -> 6;
            case "g" -> 7;
            case "h" -> 8;
            default -> 0;
        };
        int y = Integer.parseInt(stringPos.substring(1,2));
        return new Position(x-1,y-1);
    }

    @Override
    public String toString(){
        String column = switch (this.getX()){
            case 0 -> "a";
            case 1 -> "b";
            case 2 -> "c";
            case 3 -> "d";
            case 4 -> "e";
            case 5 -> "f";
            case 6 -> "g";
            case 7 -> "h";
            default -> "?";
        };
        return column+(this.getY()+1);
    }
}
