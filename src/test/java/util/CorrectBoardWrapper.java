package util;


import com.github.bhlangonijr.chesslib.Board;

public class CorrectBoardWrapper {
	private Board board;
	public CorrectBoardWrapper(){
		board = new Board();
	}

	public void resetBoard(){
		board = new Board();
	}

	public void sendCommand(String move){
		board.doMove(move);
	}

	public String getFen(){
		String fen = board.getFen();
		return fen.substring(0,fen.indexOf(" "));
	}

}
