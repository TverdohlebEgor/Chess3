package piece;

import controll.GameEngine;
import lombok.extern.slf4j.Slf4j;
import observer.NotificationHandler;
import util.CorrectBoardWrapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.FENPrinter.logErrorFEN;

@Slf4j
public class PieceTest {
	private final GameEngine gameEngine;
	private final CorrectBoardWrapper correctBoardWrapper;

	public PieceTest() {
		NotificationHandler.setEnabled(false);
		this.gameEngine = new GameEngine(true);
		this.correctBoardWrapper = new CorrectBoardWrapper();
	}

	public void generalPieceTest(List<String> moves) {
		log.info("####################");
		String lastCorrect = "8/8/8/8/8/8/8/8";
		try {
			gameEngine.resetBoard();
			correctBoardWrapper.resetBoard();
			for (String move : moves) {
				String moveString = "Move:" + move;
				log.info(moveString);
				gameEngine.sendCommand(move);
				try {
					correctBoardWrapper.sendCommand(move);
				} catch (Exception e) {
					log.warn("Correct board consider this move wrong");
				}
				String myFen = gameEngine.boardStatus();
				String stockFen = correctBoardWrapper.getFen();
				log.info("My Engine :"+ myFen);
				log.info("Stockfish :"+stockFen);
				if(!myFen.equals(stockFen)){
					logErrorFEN(lastCorrect, myFen, stockFen,moveString);
				}
				log.info("Moves :"+gameEngine.getMoves().toString());
				assertEquals(myFen, stockFen);
				lastCorrect = myFen;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
