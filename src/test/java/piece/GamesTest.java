package piece;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static piece.PieceTest.correctMovesTest;

@Slf4j
public class GamesTest {
    @ParameterizedTest
    @MethodSource("fileProvider")
    void testFileContent(List<String> arguments) {
        arguments = arguments.stream().filter(String::isBlank).toList();
        correctMovesTest(arguments);
    }

    private static Stream<Arguments> fileProvider() throws IOException {
        List<Arguments> args;
        try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/games.pgn"))) {
            args = reader.lines()
                    .map(line -> Arrays.asList(line.split(" ")))
                    .filter(list -> list.size() > 1)
                    .map(Arguments::of)
                    .toList();
        }
        return args.stream();
    }
}
