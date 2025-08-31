package model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.enums.PieceColorEnum;
import model.pieces.*;
import observer.NotificationHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static model.enums.PieceColorEnum.BLACK;
import static model.enums.PieceColorEnum.WHITE;
import static utils.Channels.UPDATE_VIEW;

@Slf4j
public class Board {
	@Getter
	private List<Piece> pieces = new ArrayList<>();

    @Getter
    private boolean isCheck = false;

    private boolean isCopy = false;

    public Board(boolean isCopy){
        this.isCopy = isCopy;
        addInitialPieces(WHITE);
        addInitialPieces(BLACK);
    }

	public Board() {
        this.isCopy = false;
		addInitialPieces(WHITE);
		addInitialPieces(BLACK);
	}

    public Board(List<Piece> pieces, boolean isCheck){
        this.isCopy = true;
        this.pieces = pieces;
        this.isCheck = isCheck;
    }

	private void addInitialPieces(PieceColorEnum color) {
        int row = 7 - (color == WHITE ? 7 : 0);
		setPiece(new Rook(color, new Position(0, row)));
		setPiece(new Rook(color, new Position(7, row)));
		setPiece(new Knight(color, new Position(1, row)));
		setPiece(new Knight(color, new Position(6, row)));
		setPiece(new Bishop(color, new Position(2, row)));
		setPiece(new Bishop(color, new Position(5, row)));
		setPiece(new King(color, new Position(4, row)));
		setPiece(new Queen(color, new Position(3, row)));
		for (int x = 0; x < 8; x++) {
			setPiece(new Pawn(color, new Position(x, 7 - (color == WHITE ? 6 : 1))));
		}
	}

	public void reset() {
		for (int i = 0; i < 64; i++) {
			int x = i / 8;
			int y = i % 8;
            sendNotification(UPDATE_VIEW, "removePiece", new Position(x, y));
		}
		pieces.clear();
		addInitialPieces(WHITE);
		addInitialPieces(BLACK);
	}

	private void setPiece(Piece piece) {
		pieces.add(piece);
        sendNotification(UPDATE_VIEW, "setPiece", piece.getPosition(), piece);
	}

	public void removePiece(Position pos) {
        sendNotification(UPDATE_VIEW, "removePiece", pos);
		for (Piece piece : pieces) {
			if (piece.getPosition().equals(pos)) {
				pieces.remove(piece);
				break;
			}
		}
	}

	public boolean isOccupied(Position pos) {
		for (Piece piece : pieces) {
			if (piece.getPosition().equals(pos)) {
				return true;
			}
		}
		return false;
	}

    public Piece pieceIn(String posName){
        return pieceIn(Position.fromString(posName));
    }

	public Piece pieceIn(Position pos) {
		for (Piece piece : pieces) {
			if (piece.getPosition().equals(pos)) {
				return piece;
			}
		}
		return null;
	}

	public King getKing(PieceColorEnum kingColor) {
		for (Piece piece : pieces) {
			if (piece.getColor() == kingColor && piece instanceof King king) {
				return king;
			}
		}
		return null;
	}


	public void move(Move move) {
		if (isOccupied(move.finalPosition())) {
			removePiece(move.finalPosition());
		}
		if (move.addPiece() != null) {
			removePiece(move.initialPosition());
			setPiece(move.addPiece());
		} else {
			handlingCastling(move);
            handleChecks(move);
            sendNotification(UPDATE_VIEW, "removePiece", move.initialPosition());
			Piece pieceToMove = pieceIn(move.initialPosition());
			pieceToMove.setPosition(move.finalPosition());
			pieceToMove.setHasMoved(true);
		}
        updateView();
	}

	private void handlingCastling(Move move) {
		Rook rook;
        switch (move.UCImove()) {
            case "e1c1" -> handleRookMoveInCastling("a1","d1");
            case "e1g1" -> handleRookMoveInCastling("h1","f1");
            case "e8c8" -> handleRookMoveInCastling("a8","d8");
            case "e8g8" -> handleRookMoveInCastling("h8","f8");
        }
	}

    private void handleRookMoveInCastling(String iniPos, String fPos){
        Optional<Rook> rook = Optional.ofNullable((Rook) pieceIn(iniPos));
        if(rook.isEmpty()) {
            return;
        }
        sendNotification(UPDATE_VIEW, "removePiece", rook.get().getPosition());
        rook.get().setPositionFromString(fPos);
        rook.get().setHasMoved(true);
    }

	private void updateView() {
        sendNotification(UPDATE_VIEW, "drawBoard");
		for (Piece piece : pieces) {
            sendNotification(UPDATE_VIEW, "setPiece", piece.getPosition(), piece);
		}
	}

    private void handleChecks(Move move){
        this.isCheck = move.SANmove().endsWith("+");
    }

    public Board copy(){
        List<Piece> piecesCopy = new ArrayList<>();
        for(Piece piece : pieces){
            piecesCopy.add(piece.copy());
        }
        return new Board(piecesCopy,isCheck);
    }

    private void sendNotification(String channelName, String functionName, Object... args){
        if(!isCopy) NotificationHandler.send(channelName,functionName,args);
    }

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		int counter = 0;
		for (int y = 7; y >= 0; --y) {
			for (int x = 0; x < 8; ++x) {
				boolean pieceFounded = false;
				Position tempPos = new Position(x, y);
				for (Piece piece : pieces) {
					if (piece.getPosition().equals(tempPos)) {
						if (counter > 0) {
							stringBuilder.append(counter);
							counter = 0;
						}
						pieceFounded = true;
						stringBuilder.append(piece);
					}
				}
				if (!pieceFounded) {
					counter += 1;
				}
			}
			if (counter != 0) {
				stringBuilder.append(counter);
			}
			stringBuilder.append("/");
			counter = 0;
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		return stringBuilder.toString();
	}
}
