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

	public static boolean pieceIsOfType(Piece piece, String typeName){
		return switch (typeName){
			case "K" -> (piece instanceof King);
			case "B" -> (piece instanceof Bishop);
			case "N" -> (piece instanceof Knight);
			case "Q" -> (piece instanceof Queen);
			case "R" -> (piece instanceof Rook);
			case "a","b","c","d","e","f","g","h" -> (piece instanceof Pawn);
			default -> false;
		};
	}
}
