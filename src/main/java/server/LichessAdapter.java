package server;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.openapi.quarkus.lichessExplorer_yaml.api.DefaultApi;
import jakarta.inject.Inject;
import org.openapi.quarkus.lichessExplorer_yaml.model.MastersGames200Response;

@ApplicationScoped
@Slf4j
public class LichessAdapter {
    @Inject
    @RestClient
    DefaultApi test;
    public void findGamesFromPosition(String FEN){
        MastersGames200Response res = test.mastersGames(
                FEN,
                null,
                null,
                null,
                null,
                null
        );
        log.info(res.toString());
    }
}
