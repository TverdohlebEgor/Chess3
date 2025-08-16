package piece;

import controll.GameEngine;
import lombok.extern.slf4j.Slf4j;
import observer.NotificationHandler;
import util.StockFishWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.FENPrinter.logErrorFEN;

@Slf4j
public class PieceTest {
	private final GameEngine gameEngine;
	private final StockFishWrapper stockFishWrapper;

	public PieceTest() {
		NotificationHandler.setEnabled(false);
		this.gameEngine = new GameEngine(true);

		try {
			this.stockFishWrapper = new StockFishWrapper();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void generalPieceTest(List<String> moves) {
		log.info("####################");
		String lastCorrect = "8/8/8/8/8/8/8/8";
		try {
			gameEngine.resetBoard();
			stockFishWrapper.resetBoard();
			List<String> stockMoves = new ArrayList<>();
			for (String move : moves) {
				String stockCommand = gameEngine.sendCommand(move);
				if(!stockCommand.isBlank()) {
					stockMoves.add(stockCommand);
					stockFishWrapper.simulateMoves(stockMoves);

					String myFen = gameEngine.boardStatus();
					String stockFen = stockFishWrapper.getFen();

					String moveString = "Move:" + move + " | StockCommand:" + stockCommand;
					log.info(moveString);
					log.info("My Engine :"+ myFen);
					log.info("Stockfish :"+stockFen);
					if(!myFen.equals(stockFen)){
						logErrorFEN(lastCorrect, myFen, stockFen,moveString);
					}
					log.error("Moves :"+gameEngine.getMoves().toString());
					assertEquals(myFen, stockFen);
					lastCorrect = myFen;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
