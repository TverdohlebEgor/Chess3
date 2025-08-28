package utils;

import lombok.experimental.UtilityClass;
import model.Board;
import model.Move;
import model.Position;
import model.enums.PieceColorEnum;
import model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

import static utils.LegalMovesAdjuster.removeDangareousForKingMoves;

@UtilityClass
public class MoveSimulator {

    public static boolean moveLetKingInDanger(Move move, PieceColorEnum kingColor, Board boardCopy){
        boardCopy.move(move);
        for(Piece piece : boardCopy.getPieces().stream().filter(p -> p.getColor() == kingColor.enemy()).toList()){
            for(Position targetedPos : piece.updateLegalMoves(boardCopy).stream().map(Move::finalPosition).toList()){
                if(boardCopy.getKing(kingColor).getPosition().equals(targetedPos)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean moveIsCheckMate(Move move, Board boardCopy){
        //Didn't need to simulate move since the GameEngine already did
        Piece movingPiece = boardCopy.pieceIn(move.finalPosition());

        List<Move> simulatedLegalMoves = new ArrayList<>();
        for(Piece piece : boardCopy.getPieces().stream().filter(p -> p.getColor() == movingPiece.getColor().enemy()).toList()){
            simulatedLegalMoves.addAll(piece.updateLegalMoves(boardCopy));
        }

        removeDangareousForKingMoves(simulatedLegalMoves,boardCopy);

        return simulatedLegalMoves.isEmpty();
    }

}
