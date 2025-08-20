package piece;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class KingTest extends PieceTest {
	@Test
	public void movement() {
		generalPieceTest(List.of("e4","e5","Bd3","Bd6","Nf3","Nf6","O-O","O-O"));
	}
}
