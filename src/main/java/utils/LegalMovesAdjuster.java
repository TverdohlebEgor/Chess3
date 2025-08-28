package utils;

import lombok.experimental.UtilityClass;
import model.Board;
import model.Move;
import model.Position;
import model.enums.PieceColorEnum;
import model.pieces.Piece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.MoveSimulator.moveIsCheckMate;
import static utils.MoveSimulator.moveLetKingInDanger;

@UtilityClass
public class LegalMovesAdjuster {
   public static void removeDangareousForKingMoves(List<Move> legalMoves, Board board){
       legalMoves.removeIf(move -> moveLetKingInDanger(move, board.pieceIn(move.initialPosition()).getColor(), board.copy()));
   }

    public static void solveConflict(List<Move> legalMoves) {
        List<List<Move>> toSolveMatx = new ArrayList<>();
        Map<Move,Boolean> visited = new HashMap<>();
        for (Move move : legalMoves) {
            if(Boolean.TRUE.equals(visited.getOrDefault(move,false))){
                continue;
            }
            List<Move> thisList = new ArrayList<>();
            thisList.add(move);
            visited.put(move,true);
            for (Move move2 : legalMoves) {
                if (!move.equals(move2) && move.SANmove().equals(move2.SANmove())) {
                    thisList.add(move2);
                    visited.put(move2,true);
                }
            }
            if (thisList.size() > 1) {
                toSolveMatx.add(thisList);
            }
        }

        for (List<Move> toSolve : toSolveMatx) {
            if (toSolve.size() > 2) {
                for (Move move : toSolve) {
                    legalMoves.remove(move);
                    legalMoves.add(
                            new Move(
                                    move.SANmove().charAt(0) + move.UCImove().substring(0, 2) + move.SANmove().substring(2),
                                    move.UCImove(),
                                    null
                            )
                    );
                }
            } else if (toSolve.size() == 2){
                legalMoves.remove(toSolve.getFirst());
                legalMoves.remove(toSolve.getLast());
                String iniPos1 = toSolve.getFirst().UCImove().substring(0,2);
                String iniPos2 = toSolve.getLast().UCImove().substring(0,2);
                if(iniPos1.charAt(0) == iniPos2.charAt(0)){
                    Move first = toSolve.getFirst();
                    legalMoves.add(addMoveWithResolvedConflict(iniPos1.charAt(1),first));
                    legalMoves.add(addMoveWithResolvedConflict(iniPos2.charAt(1),first));
                } else{
                    Move last = toSolve.getLast();
                    legalMoves.add(addMoveWithResolvedConflict(iniPos1.charAt(0),last));
                    legalMoves.add(addMoveWithResolvedConflict(iniPos2.charAt(0),last));
                }
            }
        }
    }

    private static Move addMoveWithResolvedConflict(char divergenceChar, Move move){
        return new Move(
                String.valueOf(move.SANmove().charAt(0)) +
                        divergenceChar +
                        move.SANmove().substring(1),
                move.UCImove(),
                null
        );
    }

    public static void addChecks(List<Move> legalMoves, Board board) {
        PieceColorEnum ourColor = board.pieceIn(legalMoves.getFirst().initialPosition()).getColor();
        for(int x = 0; x < legalMoves.size(); ++x){
            Move move = legalMoves.get(x);

            Position iniPos = move.initialPosition();
            Position finalPos = move.finalPosition();
            Piece movingPiece = board.pieceIn(iniPos);
            movingPiece.setPosition(finalPos);

            List<Piece> allOurPieces = new ArrayList<>(board.getPieces().stream().filter(p -> p.getColor() == ourColor).toList());
            if(move.addPiece() != null){
                allOurPieces.add(move.addPiece());
            }

            for(Piece piece : allOurPieces){
                for(Position targetedPos : piece.updateLegalMoves(board).stream().map(Move::finalPosition).toList()){
                    if(board.getKing(movingPiece.getColor().enemy()).getPosition().equals(targetedPos)){
                        String charToAdd = moveIsCheckMate(move,board.copy()) ? "#" : "+";
                        legalMoves.remove(x);
                        legalMoves.add(x,new Move(
                                move.SANmove()+charToAdd,
                                move.UCImove(),
                                move.addPiece()
                        ));
                    }
                }
            }

            movingPiece.setPosition(iniPos);
        }
    }
}
