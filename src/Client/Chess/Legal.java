package Client.Chess;

import  Client.Chess.Piece;
import  Client.Chess.Chess;

public class Legal {

    Piece piece;
    int newX;
    int newY;
    int turn;

    public Legal(Piece piece, int newX, int newY, turn){
        this.piece=piece;
        this.newX=newX;
        this.newY=newY;
        this.turn=turn;
    }
    public boolean isLegal(){
        if (piece.getType()==PieceType.RED_PAWN){
            if(turn%2==1){              //your turn
                return false;
            }
            if(movement==0){
                if((piece.getOldX() == newX +1 || piece.getOldX() == newX -1)   //pawn kill
                        && newY == piece.getOldY() +1
                        &&board[newY][newX].hasPiece()){

                }

            }
            if(movement>=0){

            }
        }
        if (piece.getType()==PieceType.RED_ROOK_LIGHT){
            if(turn%2==1){
                return false;
            }

            for(int i = piece.; i<= newX; i++){
                board[x1][y1].hasPiece()
            }
        }
        if (piece.getType()==PieceType.RED_ROOK_DARK){
            if(turn%2==1){
                return false;
            }
        }
        if (piece.getType()==PieceType.RED_KING){
            if(turn%2==1){
                return false;
            }
        }
        if (piece.getType()==PieceType.RED_QUEEN){
            if(turn%2==1){
                return false;
            }
        }
        if (piece.getType()==PieceType.RED_BISHOP_LIGHT){
            if(turn%2==1){
                return false;
            }
        }
        if (piece.getType()==PieceType.RED_BISHOP_DARK){
            if(turn%2==1){
                return false;
            }
        }
        if (piece.getType()==PieceType.RED_KNIGHT) {
            if(turn%2==1){
                return false;
            }
        }
        //-----------
        if (piece.getType()==PieceType.WHITE_PAWN){
            if(turn%2==0){
                return false;
            }
            if(movement==0){

            }
            if(movement>=0){

            }
        }
        if (piece.getType()==PieceType.WHITE_ROOK_LIGHT){
            if(turn%2==0){
                return false;
            }
        }
        if (piece.getType()==PieceType.WHITE_ROOK_DARK){
            if(turn%2==0){
                return false;
            }
        }
        if (piece.getType()==PieceType.WHITE_KING){
            if(turn%2==0){
                return false;
            }
        }
        if (piece.getType()==PieceType.WHITE_QUEEN){
            if(turn%2==0){
                return false;
            }
        }
        if (piece.getType()==PieceType.WHITE_BISHOP_LIGHT){
            if(turn%2==0){
                return false;
            }
        }
        if (piece.getType()==PieceType.WHITE_BISHOP_DARK){
            if(turn%2==0){
                return false;
            }
        }
        if (piece.getType()==PieceType.WHITE_KNIGHT){
            if(turn%2==0){
                return false;
            }

    }
}
