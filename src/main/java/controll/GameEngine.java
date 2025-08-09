package controll;

import model.Board;
import observer.NotificationHandler;
import view.BoardView;

import java.util.HashMap;
import java.util.Map;

import static utils.Channels.RETURN_COMMAND;
import static utils.Channels.SEND_COMMAND;

public class GameEngine {
    private final Map<String,String> specialCommands = new HashMap<>();

    public GameEngine(){
        BoardView boardView = new BoardView();
        Board board = new Board();
        initSpecialCommands();
        NotificationHandler.subscribe(SEND_COMMAND,this);
    }

    private void sendCommand(String inputText){
        inputText = inputText.trim();
        String returnText = specialCommands.getOrDefault(inputText,null);
        if(returnText != null) {
            NotificationHandler.send(RETURN_COMMAND, "returnCommand", returnText);
        }

    }

    private void initSpecialCommands(){
        specialCommands.put("help"," WELCOME TO THE DRUNK ONE NIGHTER CHESS GAME " +
                "\n rotate -> rotate the chessboard TODO " +
                "\n turn -> which turn is it TODO " +
                "\n reset -> reset the game TODO" +
                "");
    }
}
