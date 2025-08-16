package util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class StockFishWrapper {
	private static BufferedReader reader;
	private static BufferedWriter writer;

	public StockFishWrapper () throws IOException {
		String stockfishPath = Paths.get(System.getProperty("project.root"),"src","test","resources","stockfish").toString();
		log.info(stockfishPath);
		Process process = new ProcessBuilder(stockfishPath).start();
		reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

		sendCommand("uci");
		readUntilReady();
	}

	private void sendCommand(String command) throws IOException {
		writer.write(command + "\n");
		writer.flush();
	}

	private void readUntilReady() throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			if ("uciok".equals(line)) {
				break;
			}
		}
	}

	public void resetBoard() throws IOException {
		sendCommand("position startpos");
	}

	public void simulateMoves(List<String> moves) throws IOException {
		StringBuilder moveString = new StringBuilder("position startpos moves");
		for (String move : moves) {
			moveString.append(" ").append(move);
		}
		sendCommand(moveString.toString());
	}

	public String getFen() throws IOException {
		sendCommand("d"); // The 'd' command shows the board and FEN
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.startsWith("Fen:")) {
				String fen = line.substring("Fen:".length()).trim();
				fen = fen.substring(0,fen.indexOf(" "));
				return fen;
			}
		}
		return "FEN NOT FOUND";
	}
}
