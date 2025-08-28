package model.enums;

public enum PieceColorEnum {
    WHITE,
    BLACK;

    public PieceColorEnum enemy() {
        return this == WHITE ? BLACK : WHITE;
    }
}
