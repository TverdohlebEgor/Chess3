package controll;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.Board;
import model.Position;
import model.enums.PieceColorEnum;
import model.pieces.*;
import observer.NotificationHandler;
import view.BoardView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.enums.PieceColorEnum.BLACK;
import static model.enums.PieceColorEnum.WHITE;
import static utils.Channels.RETURN_COMMAND;
import static utils.Channels.SEND_COMMAND;
import static utils.Constant.*;
import static utils.Util.*;

@Slf4j
public class GameEngine {
    private final Map<String,String> specialCommands = new HashMap<>();
    private BoardView boardView;
    private Board board;

    @Getter
    private PieceColorEnum turn = WHITE;

    @Getter
    private List<String> moves = new ArrayList<>();

    public GameEngine(){
        boardView = new BoardView();
        board = new Board();
        initSpecialCommands();
        NotificationHandler.subscribe(SEND_COMMAND,this);
    }

    public GameEngine(boolean testMode) {
        board = new Board();
    }

    public String boardStatus(){
        return board.toString();
    }

    public void resetBoard(){
        turn = WHITE;
        moves.clear();
        board.reset();
    }

    public String sendCommand(String inputText){
        inputText = inputText.trim();
        String returnText = specialCommands.getOrDefault(inputText,null);
        if(returnText != null) {
            writeToTerminal(returnText);
        }
        if("rotate".equals(inputText)){
            boardView.rotateAndDraw();
        } else if ("boardStatus".equals(inputText)) {
           writeToTerminal(board.toString());
        } else if("reset".equals(inputText)){
            board.reset();
        }
        return switch (inputText.length()){
            case 2 -> handleTwoCharactersCommand(inputText);
            case 3 -> handleThreeCharactesCommand(inputText);
            default -> "";
        };
    }

    private void initSpecialCommands(){
        specialCommands.put("help"," WELCOME TO THE DRUNK ONE NIGHTER CHESS GAME " +
                "\n reset -> reset the game TODO" +
                "");
        specialCommands.put("turn","It's "+turn.name().toLowerCase()+" turn");
    }

    private String handleTwoCharactersCommand(String command){
       String column = command.substring(0,1);
       String number = command.substring(1,2);
       Position finalPos = positionFromRowCol(column,number);
       List<Piece> movablePieces = new ArrayList<>();
       if(!legalColums.contains(column) || !legalRows.contains(number)){
           illegalMove();
           return "";
       }
      for(Piece piece : board.getPieces()){
          if(piece instanceof Pawn && piece.getColor() == turn && piece.canMove(finalPos,board.getPieces())){
              movablePieces.add(piece);
          }
      }
      if(movablePieces.size() != 1){
          illegalMove();
          return "";
      }
      movablePieces.getFirst().setHasMoved(true);
      changeTurn();
      String initialPos = stringFromPosition(movablePieces.getFirst().getPosition());
      board.movePiece(movablePieces.getFirst(),finalPos);
      moves.add(command);
      return initialPos+stringFromPosition(finalPos);
    }

    private String handleThreeCharactesCommand(String command){
        String pieceName = command.substring(0,1);
        String column = command.substring(1,2);
        String number = command.substring(2,3);
        Position finalPos = positionFromRowCol(column,number);
        List<Piece> movablePieces = new ArrayList<>();
        if(!legalColums.contains(column) || !legalRows.contains(number) || !legalPieces.contains(pieceName)){
            illegalMove();
            return "";
        }
        for(Piece piece : board.getPieces()){
            if(
                pieceIsOfType(piece,pieceName) &&
                piece.getColor() == turn &&
                piece.canMove(finalPos,board.getPieces())
            ){
                movablePieces.add(piece);
            }
        }
        if(movablePieces.size() != 1){
            illegalMove();
            return "";
        }
        movablePieces.getFirst().setHasMoved(true);
        changeTurn();
        String initialPos = stringFromPosition(movablePieces.getFirst().getPosition());
        board.movePiece(movablePieces.getFirst(),finalPos);
        moves.add(command);
        return initialPos+stringFromPosition(finalPos);
    }

    private void illegalMove(){
        writeToTerminal("Illegal move !");
    }

    private void writeToTerminal(String text){
        NotificationHandler.send(RETURN_COMMAND, "returnCommand", text);
    }

    private void changeTurn(){
        turn = (turn == WHITE ? BLACK : WHITE);
    }
}
