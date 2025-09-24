package model;

import controll.GameEngine;
import lombok.Data;

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
    }
}
