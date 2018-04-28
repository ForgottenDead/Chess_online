package Client.Chess;

public enum PieceType {
    RED(1), WHITE(-1),
    RED_PAWN(2), RED_ROOK_LIGHT(3), RED_ROOK_DARK(4), RED_KING(5),
    RED_QUEEN(6), RED_BISHOP_LIGHT(7), RED_BISHOP_DARK(8), RED_KNIGHT(9),
    WHITE_PAWN(-2), WHITE_ROOK_LIGHT(-3), WHITE_ROOK_DARK(-4), WHITE_KING(-5),
    WHITE_QUEEN(-6), WHITE_BISHOP_LIGHT(-7), WHITE_BISHOP_DARK(-8), WHITE_KNIGHT(-9);

    final int moveDir;

    PieceType(int moveDir) {
        this.moveDir = moveDir;
    }
}