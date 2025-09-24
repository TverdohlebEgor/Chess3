package piece;

import controll.GameEngine;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import model.Move;
import model.enums.MoveType;
import util.StockFishWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.FENPrinter.logErrorFEN;

@Slf4j
@UtilityClass
public class PieceTest {
	private static final GameEngine gameEngine = new GameEngine(true);
	private static StockFishWrapper stockFishWrapper;

    static {
        try {
            stockFishWrapper = new StockFishWrapper();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	public static void correctMovesTest(List<String> moves) {
		log.info("####################");
		String lastCorrect = "8/8/8/8/8/8/8/8";
		try {
			gameEngine.resetBoard();
			stockFishWrapper.resetBoard();
            List<String> smoves = new ArrayList<>();
			for (String move : moves) {
				Move smove = gameEngine.handleMove(move, MoveType.SAN);
                smoves.add(smove.UCImove());
				stockFishWrapper.simulateMoves(smoves);

				String myFen = gameEngine.boardStatus();
				String stockFen = stockFishWrapper.getFen();

				String moveString = "Move:" + move;
				log.info(moveString);
                log.info("My Engine :{}", myFen);
                log.info("Stockfish :{}", stockFen);
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
