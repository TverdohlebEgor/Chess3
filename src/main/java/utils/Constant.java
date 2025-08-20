package utils;

import java.util.List;

public record Constant (){
    public static String piecesImageCommonPath = "C:\\Users\\thega\\Desktop\\Chess3\\src\\main\\resources\\pieces\\";
    public static List<String> legalColums = List.of("a","b","c","d","e","f","g","h");
    public static List<String> legalRows = List.of("1","2","3","4","5","6","7","8");
    public static List<String> legalPieces = List.of("B","K","N","Q","R");
    public static List<String> legalPromotablePieces = List.of("B","N","Q","R");
    public static int MAX_DISTANCE = 8;
    public static void init(){
        piecesImageCommonPath = System.getProperty("resources.path");
    }
}
