package model;

import lombok.extern.slf4j.Slf4j;
import model.enums.PieceColorEnum;
import model.pieces.*;
import observer.NotificationHandler;

import java.util.HashMap;
import java.util.Map;

import static model.enums.PieceColorEnum.BLACK;
import static model.enums.PieceColorEnum.WHITE;
import static utils.Channels.UPDATE_VIEW;

@Slf4j
public class Board {
    Map<Position, Piece> pieces = new HashMap<>();
    public Board(){
        addInitialPieces(WHITE);
        addInitialPieces(BLACK);
    }

    private void addInitialPieces(PieceColorEnum color){
        setPiece(new Position(0,7-(color == WHITE ? 7 : 0)), new Rook(color));
        setPiece(new Position(7,7-(color == WHITE ? 7 : 0)), new Rook(color));
        setPiece(new Position(1,7-(color == WHITE ? 7 : 0)), new Knight(color));
        setPiece(new Position(6,7-(color == WHITE ? 7 : 0)), new Knight(color));
        setPiece(new Position(2,7-(color == WHITE ? 7 : 0)), new Bishop(color));
        setPiece(new Position(5,7-(color == WHITE ? 7 : 0)), new Bishop(color));
        setPiece(new Position(3,7-(color == WHITE ? 7 : 0)), new King(color));
        setPiece(new Position(4,7-(color == WHITE ? 7 : 0)), new Queen(color));
        for(int x = 0; x < 8 ; x++){
            setPiece(new Position(x,7 - (color == WHITE ? 6 : 1)), new Pawn(color));
        }
    }

    private void setPiece(Position pos, Piece piece){
       pieces.put(pos,piece);
       NotificationHandler.send(UPDATE_VIEW,"setPiece",pos,piece);
    }

    public void movePiece(Position initialPosition, Position finalPosition){
        Piece pieceToMove = pieces.getOrDefault(initialPosition,null);
        if(pieceToMove != null){
            pieces.put(finalPosition,pieceToMove);
            pieces.remove(initialPosition);
        }
        updateView();
    }

    private void updateView(){
        NotificationHandler.send(UPDATE_VIEW,"drawBackground");
        for(Position pos : pieces.keySet()){
            NotificationHandler.send(UPDATE_VIEW,"setPiece",pos, pieces.get(pos));
        }
    }
}
