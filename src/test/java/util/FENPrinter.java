package util;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FENPrinter {
	public static void logErrorFEN(String lastCorrect, String yours, String correct, String incriminatedMove){
		System.out.println("#################################");
		System.out.println("#          LAST CORRECT         #");
		System.out.println("#################################");
		System.out.println("+---+---+---+---+---+---+---+---+");
		int rowNumber = 8;
		for(List<String> row : FENtoMatrx(lastCorrect)){
			System.out.print("|");
			for(String column : row){
				System.out.print(" "+column+" |");
			}
			System.out.println(" "+rowNumber);
			rowNumber-=1;
			System.out.println("+---+---+---+---+---+---+---+---+");
		}

		System.out.println("Incriminated Move:");
		System.out.println(incriminatedMove);

		System.out.println("#################################     #################################");
		System.out.println("#          CORRECT              #     #              YOURS            #");
		System.out.println("#################################     #################################");
		System.out.println("+---+---+---+---+---+---+---+---+     +---+---+---+---+---+---+---+---+");

		var matrixCorrect = FENtoMatrx(correct);
		var matrixYours = FENtoMatrx(yours);
		for(int y = 0; y < 8; y++){
			System.out.print("|");
			for(int x = 0; x < 8 ; ++x){
				System.out.print(" "+matrixCorrect.get(y).get(x)+" |");
			}
			System.out.print("      |");
			for(int x = 0; x < 8 ; ++x){
				System.out.print(" "+matrixYours.get(y).get(x)+" |");
			}
			System.out.println("\n+---+---+---+---+---+---+---+---+     +---+---+---+---+---+---+---+---+");
		}
	}

	private static List<List<String>>  FENtoMatrx(String FEN){
		List<List<String>> result = new ArrayList<>();
		List<String> divededFen = List.of(FEN.split("/"));
		for(String piece : divededFen){
			List<String> tempList = new ArrayList<>();
			for(String s : List.of(piece.split(""))){
				try{
					for(int x = 0; x < Integer.valueOf(s); ++x){
						tempList.add(" ");
					}
				} catch (Exception e){
					tempList.add(s);
				}
			}
			result.add(tempList);
		}
		return result;
	}

	private static String codeToSimbol(String simbol){
		return switch (simbol){
			case "P" -> "♙";
			case "p" -> "♟";
			case "R" -> "♖";
			case "r" -> "♜";
			case "K" -> "♔";
			case "k" -> "♚";
			case "Q" -> "♕";
			case "q" -> "♛";
			case "N" -> "♘";
			case "n" -> "♞";
			case "B" -> "♗";
			case "b" -> "♝";
			default -> "?";
		};
	}
}
