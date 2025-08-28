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

    @Test
    public void checks(){
        correctMovesTest(List.of("e4","d5","exd5","e6","dxe6","a5","Qe2","b5","exf7+"));
        correctMovesTest(List.of("d4","e5","Qd3","a6","Qe3","d6","dxe5","b6","exd6+"));
        correctMovesTest(List.of("e4", "d5", "exd5", "Qd6", "a3", "Qxa3", "Rxa3", "Be6", "d6", "a6", "dxc7", "Bd5", "c8=Q+"));
    }
}
