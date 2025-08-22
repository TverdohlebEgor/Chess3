package piece;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class KingTest extends PieceTest {
	@Test
	public void movement() {
		correctMovesTest(List.of("e3","e6","Ke2","Ke7"));
	}

	@Test
	public void castling() {
		correctMovesTest(List.of("e4","e5","Nf3","Nc6","Bb5","Qf6","O-O","d6","a3","Bg4","a4","O-O-O"));

		correctMovesTest(List.of("d4","e5","Qd3","Bc5","Be3","Nf6","Nc3","O-O","O-O-O"));
	}
}
