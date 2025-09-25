package endpoint;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class GameEndpointTest {

    @BeforeEach
    public void clearGames() throws Exception {
        given()
                .when().post("game/all/stop")
                .then()
                .statusCode(200);
    }

    private UUID createGame(){
        Response response = given()
                .when()
                .get("game/start")
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response();
        String newGameIdString = response.asString();
        return UUID.fromString(newGameIdString);
    }

    @Test
    public void creation() {
        UUID newGameId = createGame();
        assertNotNull(newGameId, "The extracted UUID should not be null.");
        System.out.println("Created a new game with ID: " + newGameId);
    }

    @Test
    public void existingGame() {
        UUID newGameId = createGame();
        given()
                .when().get("game/"+newGameId)
                .then()
                .statusCode(200);
    }

    @Test
    public void deletingGame(){
        UUID newGameId = createGame();
        given()
                .when().post("game/"+newGameId+"/stop")
                .then()
                .statusCode(200);
        given()
                .when().get("game/"+newGameId)
                .then()
                .statusCode(404);
    }

    public String move(UUID gameId, String move){
        return given()
                .when().get("game/"+gameId+"/move/"+move)
                .then()
                .statusCode(200)
                .body(notNullValue())
                .extract()
                .response().asString();
    }

    @Test
    public void uciMove(){
        UUID newGameId = createGame();
        String Fen1 = move(newGameId,"e2e4");
        String Fen2 = move(newGameId,"e2e4");
        String Fen3 = move(newGameId,"e7e5");
        assertThat(Fen1,is(equalTo(Fen2)));
        assertThat(Fen2,is(not(equalTo(Fen3))));
        assertThat(Fen3,is(equalTo("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR")));
    }

}
