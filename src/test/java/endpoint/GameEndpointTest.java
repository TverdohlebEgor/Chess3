package endpoint;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
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

}
