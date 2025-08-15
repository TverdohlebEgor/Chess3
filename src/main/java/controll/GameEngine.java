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

@Slf4j
public class GameEngine {
    private final Map<String,String> specialCommands = new HashMap<>();
    private BoardView boardView;
    private Board board;

    @Getter
    private PieceColorEnum turn = WHITE;

    public GameEngine(){
        boardView = new BoardView();
        board = new Board();
        initSpecialCommands();
        NotificationHandler.subscribe(SEND_COMMAND,this);
    }

    private void sendCommand(String inputText){
        inputText = inputText.trim();
        String returnText = specialCommands.getOrDefault(inputText,null);
        if(returnText != null) {
            writeToTerminal(returnText);
        }
        if("rotate".equals(inputText)){
            boardView.rotateAndDraw();
            return;
        }
        switch (inputText.length()){
            case 2 -> handleTwoCharactersCommand(inputText);
            case 3 -> handleThreeCharactesCommand(inputText);
        }
    }

    private void initSpecialCommands(){
        specialCommands.put("help"," WELCOME TO THE DRUNK ONE NIGHTER CHESS GAME " +
                "\n reset -> reset the game TODO" +
                "");
        specialCommands.put("turn","It's "+turn.name().toLowerCase()+" turn");
    }

    private void handleTwoCharactersCommand(String command){
       String column = command.substring(0,1);
       String number = command.substring(1,2);
       Position finalPos = positionFromRowCol(column,number);
       List<Piece> movablePieces = new ArrayList<>();
       if(!legalColums.contains(column) || !legalRows.contains(number)){
           illegalMove();
           return;
       }
      for(Piece piece : board.getPieces()){
          if(piece.getColor() == turn && piece.canMove(finalPos,board.getPieces())){
              movablePieces.add(piece);
          }
      }
      if(movablePieces.size() != 1){
          illegalMove();
          return;
      }
      movablePieces.getFirst().setHasMoved(true);
      changeTurn();
      board.movePiece(movablePieces.getFirst(),finalPos);
    }

    private void handleThreeCharactesCommand(String command){
        String pieceName = command.substring(0,1);
        String column = command.substring(1,2);
        String number = command.substring(2,3);
        Position finalPos = positionFromRowCol(column,number);
        List<Piece> movablePieces = new ArrayList<>();
        if(!legalColums.contains(column) || !legalRows.contains(number) || !legalPieces.contains(pieceName)){
            illegalMove();
            return;
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
            return;
        }
        movablePieces.getFirst().setHasMoved(true);
        changeTurn();
        board.movePiece(movablePieces.getFirst(),finalPos);
    }

    private void illegalMove(){
        writeToTerminal("Illegal move !");
    }

    private void writeToTerminal(String text){
        NotificationHandler.send(RETURN_COMMAND, "returnCommand", text);
    }

    private Position positionFromRowCol(String column, String row){
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

    private boolean pieceIsOfType(Piece piece, String typeName){
        return switch (typeName){
            case "K" -> (piece instanceof King);
            case "B" -> (piece instanceof Bishop);
            case "N" -> (piece instanceof Knight);
            case "Q" -> (piece instanceof Queen);
            case "R" -> (piece instanceof Rook);
            default -> false;
        };
    }

    private void changeTurn(){
        turn = (turn == WHITE ? BLACK : WHITE);
    }
}
