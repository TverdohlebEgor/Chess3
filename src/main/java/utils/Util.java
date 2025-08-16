package utils;

import model.Position;
import model.pieces.*;

public class Util {
	public static Position positionFromRowCol(String column, String row){
		int x = switch (column){
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
		int y = Integer.parseInt(row);
		return new Position(x-1,y-1);
	}

	public static String stringFromPosition(Position pos){
		String column = switch (pos.getX()){
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
		return column+(pos.getY()+1);
	}

	public static boolean pieceIsOfType(Piece piece, String typeName){
		return switch (typeName){
			case "K" -> (piece instanceof King);
			case "B" -> (piece instanceof Bishop);
			case "N" -> (piece instanceof Knight);
			case "Q" -> (piece instanceof Queen);
			case "R" -> (piece instanceof Rook);
			default -> false;
		};
	}
}
