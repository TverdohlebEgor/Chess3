package piece;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class PawnTest extends PieceTest {
	@Test
	public void movement() {
		generalPieceTest(List.of("e4", "e5"));

		generalPieceTest(List.of("e4", "e7", "e1", "e5"));

		generalPieceTest(List.of("e4", "e5", "e2", "e7"));

		generalPieceTest(List.of("e4", "e5", "d3", "d6", "d4", "d5"));

		generalPieceTest(List.of("e4", "e5", "d3", "d6", "d4", "d5"));

		generalPieceTest(List.of("e4", "e5", "d3", "d6", "d4", "d5","a3","Qd8","Kd8","Nd8","a6"));
	}
}
