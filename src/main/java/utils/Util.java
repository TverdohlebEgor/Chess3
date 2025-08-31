package utils;

import model.Position;
import model.pieces.*;

public class Util {
	public static boolean positionInBound(Position pos){
		return
			pos.getX() >= 0 &&
			pos.getX() <= 7 &&
			pos.getY() >= 0 &&
			pos.getY() <= 7;
	}
}
