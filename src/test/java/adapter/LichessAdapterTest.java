package adapter;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import server.LichessAdapter;

@QuarkusTest
public class LichessAdapterTest {
    @Inject
    LichessAdapter lichessAdapter;

    @Test
    public void test(){
       lichessAdapter.findGamesFromPosition("r1bqk2r/4bppp/p1np1n2/1p1Np1B1/4P3/N1P5/PP3PPP/R2QKB1R b KQkq - 0 10");
    }
}
