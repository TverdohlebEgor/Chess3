package server;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("/game")
public class GameEndpoint {

    private final Map<UUID, Game> ongoingGames = new HashMap<>();

    @GET
    @Path("/start")
    @Produces(MediaType.TEXT_PLAIN)
    public UUID startGame(){
        Game newGame = new Game();
        ongoingGames.put(newGame.getId(),newGame);
        return newGame.getId();
    }

    @POST
    @Path("/{gameId}/stop")
    public Response stopGame(@PathParam("gameId") UUID gameId) {
        if (ongoingGames.containsKey(gameId)) {
            ongoingGames.remove(gameId);
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/all/stop")
    public Response stopAllGames(){
        ongoingGames.clear();
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{gameId}")
    public Response getGame(@PathParam("gameId") UUID gameId){
        if (ongoingGames.containsKey(gameId)) {
            return Response.status(Response.Status.OK).entity(ongoingGames.get(gameId)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
