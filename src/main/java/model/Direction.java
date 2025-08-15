package model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Direction {
	public Direction(int x, int y, int distance){
		if(x > 1 || x < -1 || y > 1 || y < -1){
			throw new IllegalArgumentException("Illegal value for direction");
		}
		this.X = x;
		this.Y = y;
		this.distance = distance;
	}
	int X;
	int Y;
	int distance;
}
