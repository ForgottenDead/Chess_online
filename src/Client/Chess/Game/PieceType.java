package Client.Chess.Game;

public enum PieceType { //insted of move direction give them a move ID
    RED(1), WHITE(-1),
    RED_PAWN(1), RED_ROOK_LIGHT(2), RED_ROOK_DARK(2), RED_KING(3),
    RED_QUEEN(4), RED_BISHOP_LIGHT(5), RED_BISHOP_DARK(6), RED_KNIGHT(7),
    WHITE_PAWN(-1), WHITE_ROOK_LIGHT(2), WHITE_ROOK_DARK(2), WHITE_KING(3),
    WHITE_QUEEN(4), WHITE_BISHOP_LIGHT(5), WHITE_BISHOP_DARK(6), WHITE_KNIGHT(7);

    final int moveDir;

    PieceType(int moveDir) {
        this.moveDir = moveDir;
    }
}