package piece;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class RookTest extends PieceTest {
	@Test
	public void movement() {
		generalPieceTest(List.of("a4", "a5","h4","h5","Ra3","Ra6","Rd3","Rd6"));
		generalPieceTest(List.of("a4", "a5","h4","h5","Ra3","Ra6","Rb4","Ra4","Rb3"));
		generalPieceTest(List.of("a4","a5","Ra3","Ra6","Rg3","Re6","Rg5","Re4","Rd5","Rg4","Rd3","Rb4","Rd5","Rh4","Rg5","Rb4","Rg4","Rb5","Rb4","Re5","Rb3","Rg5","Re3","Rd5","Re4","Rc5"));
	}
}
