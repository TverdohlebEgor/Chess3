package piece;

import controll.GameEngine;
import lombok.extern.slf4j.Slf4j;
import model.enums.MoveType;
import observer.NotificationHandler;
import util.PressoWrapper;
import util.StockFishWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.FENPrinter.logErrorFEN;

@Slf4j
public class PieceTest {
	private final GameEngine gameEngine;
	private final PressoWrapper pressoWrapper;

	public PieceTest() {
		NotificationHandler.setEnabled(false);
		this.gameEngine = new GameEngine(true);
		pressoWrapper = new PressoWrapper();

	}

	public void correctMovesTest(List<String> moves) {
		log.info("####################");
		String lastCorrect = "8/8/8/8/8/8/8/8";
		try {
			gameEngine.resetBoard();
			pressoWrapper.resetBoard();
			for (String move : moves) {
				gameEngine.handleMove(move, MoveType.SAN);
				pressoWrapper.sendCommand(move);

				String myFen = gameEngine.boardStatus();
				String stockFen = pressoWrapper.getFen();

				String moveString = "Move:" + move;
				log.info(moveString);
				log.info("My Engine :" + myFen);
				log.info("Stockfish :" + stockFen);
				if (!myFen.equals(stockFen)) {
					logErrorFEN(lastCorrect, myFen, stockFen, moveString);
				}
				assertEquals(myFen, stockFen);
				lastCorrect = myFen;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
