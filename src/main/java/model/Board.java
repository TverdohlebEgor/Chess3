package model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.enums.PieceColorEnum;
import model.pieces.*;
import observer.NotificationHandler;

import java.util.ArrayList;
import java.util.List;

import static model.enums.PieceColorEnum.BLACK;
import static model.enums.PieceColorEnum.WHITE;
import static utils.Channels.UPDATE_VIEW;

@Slf4j
public class Board {
    @Getter
    List<Piece> pieces = new ArrayList<>();
    public Board(){
        addInitialPieces(WHITE);
        addInitialPieces(BLACK);
    }

    private void addInitialPieces(PieceColorEnum color){
        setPiece(new Rook(color, new Position(0,7-(color == WHITE ? 7 : 0))));
        setPiece(new Rook(color, new Position(7,7-(color == WHITE ? 7 : 0))));
        setPiece(new Knight(color, new Position(1,7-(color == WHITE ? 7 : 0))));
        setPiece(new Knight(color, new Position(6,7-(color == WHITE ? 7 : 0))));
        setPiece(new Bishop(color, new Position(2,7-(color == WHITE ? 7 : 0))));
        setPiece(new Bishop(color, new Position(5,7-(color == WHITE ? 7 : 0))));
        setPiece(new King(color,new Position(4,7-(color == WHITE ? 7 : 0))));
        setPiece(new Queen(color, new Position(3,7-(color == WHITE ? 7 : 0))));
        for(int x = 0; x < 8 ; x++){
            setPiece(new Pawn(color, new Position(x,7 - (color == WHITE ? 6 : 1))));
        }
    }

    private void setPiece(Piece piece){
       pieces.add(piece);
       NotificationHandler.send(UPDATE_VIEW,"setPiece",piece.getPosition(),piece);
    }

    public void movePiece(Piece pieceToMove, Position finalPosition){
        Position initialPosition = pieceToMove.getPosition();
        NotificationHandler.send(UPDATE_VIEW,"removePiece",initialPosition);
        pieceToMove.setPosition(finalPosition);
        updateView(initialPosition);
    }

    private void updateView(Position initialPosition){
        NotificationHandler.send(UPDATE_VIEW,"drawBoard");
        for(Piece piece: pieces){
            NotificationHandler.send(UPDATE_VIEW,"setPiece",piece.getPosition(),piece);
        }
    }
}
