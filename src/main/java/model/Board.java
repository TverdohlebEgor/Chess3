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
    private List<Piece> pieces = new ArrayList<>();


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

    public void reset(){
        for(int i = 0; i < 64; i++){
            int x = i/8;
            int y = i%8;
            NotificationHandler.send(UPDATE_VIEW,"removePiece",new Position(x,y));
        }
        pieces.clear();
        addInitialPieces(WHITE);
        addInitialPieces(BLACK);
    }

    private void setPiece(Piece piece){
       pieces.add(piece);
       NotificationHandler.send(UPDATE_VIEW,"setPiece",piece.getPosition(),piece);
    }

    public void addPiece(String pieceName, PieceColorEnum color, Position pos){
        Piece newPiece = null;
        switch (pieceName) {
            case "Q" -> newPiece = new Queen(color,pos);
            case "N" -> newPiece = new Knight(color,pos);
            case "B" -> newPiece = new Bishop(color,pos);
            case "R" -> newPiece = new Rook(color,pos);
        }
        if(newPiece == null){
            throw new IllegalArgumentException("Trying to create new piece but illegal name");
        }
        setPiece(newPiece);
    }

    public void removePiece(Position pos){
        NotificationHandler.send(UPDATE_VIEW,"removePiece",pos);
        for (Piece piece : pieces){
            if(piece.getPosition().equals(pos)){
                pieces.remove(piece);
                break;
            }
        }
    }

    public boolean isOccupied(Position pos){
        for (Piece piece : pieces){
            if(piece.getPosition().equals(pos)){
                return true;
            }
        }
        return false;
    }

    public Piece pieceIn(Position pos){
        for (Piece piece : pieces){
            if(piece.getPosition().equals(pos)){
                return piece;
            }
        }
        return null;
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

    @Override
    public String toString(){
       StringBuilder stringBuilder = new StringBuilder();
       int counter = 0;
       for(int y = 7; y >= 0; --y){
           for(int x = 0; x < 8; ++x){
                boolean pieceFounded = false;
                Position tempPos = new Position(x,y);
                for(Piece piece : pieces){
                    if(piece.getPosition().equals(tempPos)){
                        if(counter > 0){
                            stringBuilder.append(counter);
                            counter = 0;
                        }
                        pieceFounded = true;
                        stringBuilder.append(piece);
                    }
                }
                if(!pieceFounded) {
                    counter += 1;
                }
           }
           if(counter != 0){
               stringBuilder.append(counter);
           }
           stringBuilder.append("/");
           counter = 0;
       }
       stringBuilder.deleteCharAt(stringBuilder.length()-1);
       return stringBuilder.toString();
    }
}
