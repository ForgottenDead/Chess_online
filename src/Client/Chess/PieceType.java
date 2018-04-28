package Client.Chess;

public enum PieceType {
    RED(1), WHITE(-1),
    RED_PAWN(2), RED_ROOK(3), RED_KING(4),
    RED_QUEEN(5), RED_BISHOP(6), RED_KNIGHT(7),
    WHITE_PAWN(-2), WHITE_ROOK(-3), WHITE_KING(-4),
    WHITE_QUEEN(-5), WHITE_BISHOP(-6), WHITE_KNIGHT(-7);

    final int moveDir;

    PieceType(int moveDir) {
        this.moveDir = moveDir;
    }
}