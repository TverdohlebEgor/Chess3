package piece;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class PawnTest extends PieceTest {
	@Test
	public void movement() {
		correctMovesTest(List.of("e4", "e5"));

		correctMovesTest(List.of("e4", "e5", "d3", "d6", "d4", "d5"));

		correctMovesTest(List.of("e4", "d5", "exd5"));
	}

	@Test
	public void promotion() {
		correctMovesTest(List.of("e4","d5","exd5","e5","d4","exd4","d6","d3","dxc7","dxc2","Bf4","Bf5","c8=Q","c1=Q"));
		correctMovesTest(List.of("e4","d5","exd5","e5","d4","exd4","d6","d3","dxc7","dxc2","Bf4","Bf5","cxb8=Q","cxb1=Q"));

		correctMovesTest(List.of("e4","d5","exd5","e5","d4","exd4","d6","d3","dxc7","dxc2","Bf4","Bf5","c8=N","c1=N"));
		correctMovesTest(List.of("e4","d5","exd5","e5","d4","exd4","d6","d3","dxc7","dxc2","Bf4","Bf5","cxb8=N","cxb1=N"));

		correctMovesTest(List.of("e4","d5","exd5","e5","d4","exd4","d6","d3","dxc7","dxc2","Bf4","Bf5","c8=R","c1=R"));
		correctMovesTest(List.of("e4","d5","exd5","e5","d4","exd4","d6","d3","dxc7","dxc2","Bf4","Bf5","cxb8=R","cxb1=R"));

		correctMovesTest(List.of("e4","d5","exd5","e5","d4","exd4","d6","d3","dxc7","dxc2","Bf4","Bf5","c8=B","c1=B"));
		correctMovesTest(List.of("e4","d5","exd5","e5","d4","exd4","d6","d3","dxc7","dxc2","Bf4","Bf5","cxb8=B","cxb1=B"));
	}
}
