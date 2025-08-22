package util;

import com.github.bhlangonijr.chesslib.move.MoveList;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PressoWrapper {
    private String moveString;
    private MoveList moveList;

    public PressoWrapper() {
        moveString = "";
        moveList = new MoveList();
    }

    public void sendCommand(String sanCommand) {
        if(moveString.isEmpty()) moveString+=sanCommand;
        else moveString += " " + sanCommand;
        moveList.clear();
        moveList.loadFromSan(moveString);
    }

    public void resetBoard() {
        moveList = new MoveList();
        moveString = "";
    }

    public String getFen() {
        String fen = moveList.getFen();
        return fen.substring(0,fen.indexOf(" "));
    }
}
