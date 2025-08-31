package utils;

import java.util.List;

public record Constant (){
    public static String piecesImageCommonPath = "C:\\Users\\thega\\Desktop\\Chess3\\src\\main\\resources\\pieces\\";
    public static int MAX_DISTANCE = 8;
    public static void init(){
        piecesImageCommonPath = System.getProperty("resources.path");
    }
}
