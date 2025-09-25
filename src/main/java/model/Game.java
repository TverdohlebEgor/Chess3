package model;

import controll.GameEngine;
import lombok.Data;
import model.enums.MoveType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Game {
    private UUID id;
    private List<Move> moves = new ArrayList<>();
    private List<String> positions = new ArrayList<>();
    private GameEngine engine;

    public Game(){
        this.id = UUID.randomUUID();
        engine = new GameEngine();
        positions.add("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }

    public String UCImove(String move){
        Move newMove = engine.handleMove(move, MoveType.UCI);
        String resultingFEN = engine.boardStatus();
        if(!positions.getLast().equals(resultingFEN)){
            moves.add(newMove);
            positions.add(resultingFEN);
        }
        return resultingFEN;
    }
}
